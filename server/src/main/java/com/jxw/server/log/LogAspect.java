package com.jxw.server.log;
import com.google.gson.*;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.ibatis.type.SimpleTypeRegistry.isSimpleType;


@Aspect
@Component
@Order(1)
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    private static final int MAX_LOG_LENGTH = 512;  // 日志截断长度
    private static final int MAX_PARAM_LENGTH = 200; // 参数日志最大长度
    @Autowired(required = false)
    private HttpServletRequest request;

    @Around("@annotation(com.jxw.server.log.Log)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String requestId = UUID.randomUUID().toString().substring(0, 8);
        MDC.put("requestId", requestId);
        long startTime = System.currentTimeMillis();

        try {
            logRequest(joinPoint, requestId);
            return joinPoint.proceed();
        } catch (Throwable ex) {
            logError(ex, requestId);
            throw ex;
        } finally {
            logDuration(startTime, requestId);
            MDC.clear();
        }
    }

    private void logRequest(ProceedingJoinPoint joinPoint, String requestId) {
        if (!logger.isInfoEnabled()) return;

        String[] paramsDesc = Arrays.stream(joinPoint.getArgs())
                .map(this::describeParameter)
                .toArray(String[]::new);

//        String logMessage = String.format(
//                "[%s] 请求 - URI: %s | 方法: %s | 参数: %s",
//                requestId,
//                getRequestUri(),
//                joinPoint.getSignature().toShortString(),
//                truncate(String.join(", ", paramsDesc))
//        );

        String[] logEntries = {
                "URI     : " + getRequestUri(),
                "Method  : " + joinPoint.getSignature().toShortString(),
                "Client  : " + getClientInfo(),
                "Params  : " + truncate(String.join(", ", paramsDesc))
        };

        logger.info("[{}] API START\n{}",
                requestId, String.join("\n", logEntries));

    }

    private String describeParameter(Object param) {
        if (param == null) return "null";

        // 处理文件类型参数
        if (param instanceof MultipartFile) {
            MultipartFile file = (MultipartFile) param;
            return String.format("File[name=%s, size=%sKB]",
                    file.getOriginalFilename(),
                    file.getSize() / 1024);
        }

        // 处理文件数组/列表
        if (param instanceof MultipartFile[]) {
            return Arrays.stream((MultipartFile[]) param)
                    .map(f -> "File[name=" + f.getOriginalFilename() + "]")
                    .collect(Collectors.joining(", ", "[", "]"));
        }

        // 其他类型处理
        if (isSimpleType(param.getClass())) {
            return param.toString();
        }

        return param.getClass().getSimpleName() + "@" + Integer.toHexString(param.hashCode());
    }

    private boolean isSimpleType(Class<?> clazz) {
        return clazz.isPrimitive() ||
                clazz == String.class ||
                Number.class.isAssignableFrom(clazz) ||
                clazz == Boolean.class;
    }

    private String getRequestUri() {
        return request != null ?
                request.getMethod() + " " + request.getRequestURI() : "N/A";
    }

    private String getClientInfo() {
        if (request == null) return "N/A";
        return String.format("%s (%s)",
                request.getRemoteAddr(),
                request.getHeader("User-Agent"));
    }

    private String safeParams(Object[] args) {
        try {
            String json = new Gson().toJson(args);
            return truncate(json.replaceAll("\"password\":\"[^\"]+\"", "\"password\":\"***\""));
        } catch (Exception e) {
            return "[Params Serialization Failed]";
        }
    }

    private void logDuration(long startTime, String requestId) {
        long cost = System.currentTimeMillis() - startTime;
        logger.info("[{}] API END - {}ms", requestId, cost);

        if (cost > 1000) {
            logger.warn("[{}] SLOW_REQUEST: {}ms", requestId, cost);
        }
    }

    private void logError(Throwable ex, String requestId) {
        logger.error("[{}] API ERROR: {} - {}",
                requestId, ex.getClass().getSimpleName(), ex.getMessage());
    }

    private String truncate(String content) {
        if (content == null) return "";
        return content.length() > MAX_PARAM_LENGTH
                ? content.substring(0, MAX_PARAM_LENGTH) + "..."
                : content;
    }
}