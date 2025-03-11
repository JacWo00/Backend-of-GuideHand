package com.jxw.server.service;

import com.jxw.server.entity.LLMInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

public interface ILLMService {
    public String analysis(LLMInput llmInput, String userId);

    String analysis(String input, String userId);

    public String sumUpAnalysis(ArrayList<String> analysisResultList, String userId);
}
