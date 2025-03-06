package com.jxw.server.service.impl;

import com.jxw.server.entity.LLMInput;
import com.jxw.server.entity.UserInfo;
import com.jxw.server.service.ILLMService;
import com.jxw.server.util.llm.HttpSSE;
import com.jxw.server.util.llm.MyWebSocket;
import com.jxw.server.util.llm.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LLMService implements ILLMService {
    @Autowired
    UserInfoServiceImpl userInfoService;
    @Autowired
    MyWebSocket myWebSocket;
    @Autowired
    HttpSSE httpSSE;

    @Override
    public String analysis(LLMInput llmInput, String userId) {
        UserInfo userInfo = userInfoService.getById(userId);
        String sessionId = userInfo.getSessionId();
        if(sessionId==null){
            sessionId = Session.getSessionId();
            userInfo.setSessionId(sessionId);
            userInfoService.updateById(userInfo);
        }
        String requestId = Session.getRequestId();

        String result = myWebSocket.webSocketInvoke(llmInput.toFormattedString(),sessionId, requestId);
        return result;
    }

    @Override
    public String sumUpAnalysis(ArrayList<String> analysisResultList, String userId) {
        String input=String.join("/n",analysisResultList);
        String sessionId = userInfoService.getById(userId).getSessionId();
        String requestId = Session.getRequestId();
        String result = myWebSocket.webSocketInvoke(input,sessionId, requestId);
        return result;
    }
}
