package com.jxw.server.response.controller;

import com.jxw.server.log.Log;
import com.jxw.server.request.LlmConsultRequest;
import com.jxw.server.response.ApiResponse;
import com.jxw.server.security.AuthToken;
import com.jxw.server.service.ILLMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/llm")
public class LLMController {
    @Autowired
    ILLMService llmService;

    @Log
    @AuthToken
    @PostMapping("/consult")
    public ResponseEntity<ApiResponse> consult(@RequestBody LlmConsultRequest request){
        String userId= request.getUserId();
        // String recordId = request.getRecordId();
        String inquiry = request.getInquiry();
        Boolean context = request.getContext();

        StringBuilder sb=new StringBuilder();
        if(context){
            sb.append("请你结合上一次的训练数据和分析结果，回答以下问题：\n");
            sb.append(inquiry);
        }
        else{
            sb.append("请你不要结合之前的对话，回答以下问题：\n");
            sb.append(inquiry);
        }
        try {
            String answer = llmService.analysis(sb.toString(), userId);
            return ResponseEntity.ok(new ApiResponse("200","success",answer));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }


}
