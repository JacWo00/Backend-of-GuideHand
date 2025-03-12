package com.jxw.server.service;

import com.jxw.server.entity.ShootingAngles;

import java.util.ArrayList;
import java.util.List;

public interface ILLMService {
    String analysis(ShootingAngles shootingAngles, String userId);

    String analysis(String input, String userId);

    String sumUpAnalysis(List<String> analysisResultList, String userId);
}
