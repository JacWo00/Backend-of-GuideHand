package com.jxw.server.controller;

import com.jxw.server.dto.DetailedAnalysis;
import com.jxw.server.entity.ShootingAngles;
import com.jxw.server.entity.UserTrainingRecords;
import com.jxw.server.log.Log;
import com.jxw.server.response.ApiResponse;
import com.jxw.server.security.AuthToken;
import com.jxw.server.service.IShootingAnglesService;
import com.jxw.server.service.IUserTrainingRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/detailedAnalysis")
public class DetailedAnalysisController {
    @Autowired
    IShootingAnglesService shootingAnglesService;

    @Autowired
    IUserTrainingRecordsService userTrainingRecordsService;

     @Log
     @AuthToken
     @GetMapping("/getByRecordId")
    public ResponseEntity<ApiResponse> getByRecordId(
            @RequestParam("userId") String userId,
            @RequestParam("recordId") String recordId){
         try{
             ShootingAngles shootingAngles = shootingAnglesService.getShootingAngles(userId, recordId);
             UserTrainingRecords userTrainingRecord = userTrainingRecordsService.findByUserIdAndRecordId(userId, recordId);
             DetailedAnalysis detailedAnalysis = new DetailedAnalysis(shootingAngles, userTrainingRecord.getAiAnalysis(), userTrainingRecord.getAiSuggestions(), userTrainingRecord.getWeaknessPoints());
             return ResponseEntity.ok(new ApiResponse("200","success",detailedAnalysis));
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
         }
     }
}
