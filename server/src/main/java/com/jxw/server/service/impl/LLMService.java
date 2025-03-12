package com.jxw.server.service.impl;

import com.jxw.server.entity.ShootingAngles;
import com.jxw.server.entity.UserInfo;
import com.jxw.server.service.ILLMService;
import com.jxw.server.util.llm.HttpSSE;
import com.jxw.server.util.llm.MyWebSocket;
import com.jxw.server.util.llm.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class LLMService implements ILLMService {
    @Autowired
    UserInfoServiceImpl userInfoService;
    @Autowired
    MyWebSocket myWebSocket;
    @Autowired
    HttpSSE httpSSE;

    @Override
    public String analysis(ShootingAngles shootingAngles, String userId) {
        return invokeWebSocketAnalysis(shootingAngles.toFormattedString(), userId);
    }

    @Override
    public String analysis(String input, String userId) {
        return invokeWebSocketAnalysis(input, userId);
    }

    @Override
    public String sumUpAnalysis(List<String> analysisResultList, String userId) {
        // 修复换行符拼写错误，使用正确的\n
        String input = String.join("\n", analysisResultList);
        return invokeWebSocketAnalysis(input, userId);
    }

    /**
     * 统一WebSocket调用方法
     */
    private String invokeWebSocketAnalysis(String input, String userId) {
        final String sessionId = getOrCreateSessionId(userId);
        final String requestId = Session.getRequestId();
        return myWebSocket.webSocketInvoke(input, sessionId, requestId);
    }

    /**
     * 获取或创建Session ID（包含用户信息更新）
     */
    private String getOrCreateSessionId(String userId) {
        UserInfo userInfo = getUserInfo(userId);
        String sessionId = userInfo.getSessionId();

        if (sessionId.isEmpty()) {
            sessionId = Session.getSessionId();
            userInfo.setSessionId(sessionId);
            userInfoService.updateById(userInfo);
        }
        return sessionId;
    }

    /**
     * 带空校验的用户信息获取
     */
    private UserInfo getUserInfo(String userId) {
        Objects.requireNonNull(userId, "User ID cannot be null");
        UserInfo userInfo = userInfoService.getById(userId);
        if (userInfo == null) {
            throw new RuntimeException("User not found for ID: " + userId);
        }
        return userInfo;
    }
}
