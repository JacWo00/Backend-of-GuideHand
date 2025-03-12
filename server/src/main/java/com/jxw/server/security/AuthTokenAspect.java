package com.jxw.server.security;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
public class AuthTokenAspect {

    @Autowired
    private HttpServletRequest request; // 注入 HttpServletRequest 获取请求头信息

    @Around("@annotation(com.jxw.server.security.AuthToken)")
    public Object validateToken(ProceedingJoinPoint joinPoint) throws Throwable {
        // 从请求头中获取 Token
        String token = request.getHeader("Authorization");

        if (token == null || !isValidToken(token)) {
            throw new RuntimeException("Invalid or missing token");
        }

        // 继续执行原方法
        return joinPoint.proceed();
    }

    private boolean isValidToken(String token) {
        try{
            JwtUtil.parseClaim(token);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
