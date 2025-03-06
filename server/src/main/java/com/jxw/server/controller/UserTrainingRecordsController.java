package com.jxw.server.controller;


import com.jxw.server.entity.UserTrainingRecords;
import com.jxw.server.request.Request;
import com.jxw.server.service.IUserTrainingRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2025-02-25
 */
@RestController
@RequestMapping("/user-training-records")
public class UserTrainingRecordsController {
    @Autowired
    IUserTrainingRecordsService userTrainingRecordsService;

    @GetMapping("/recent-seven-days")
    public List<UserTrainingRecords> findByUserIdAndTrainingDateBetween(@RequestBody Request request){
        Long userID= request.getUserID();
        LocalDate startDate=request.getStartDate();
        LocalDate endDate=request.getEndDate();
        return userTrainingRecordsService.findByUserIdAndTrainingDateBetween(userID,startDate,endDate);
    }

    @PostMapping("/add-records")
    public String addRecords(@RequestBody List<UserTrainingRecords> userTrainingRecordsList){
        try {
            for(UserTrainingRecords userTrainingRecords:userTrainingRecordsList){
                userTrainingRecordsService.save(userTrainingRecords);
            }
            return "success";
        } catch (Exception e) {
            return "error"+e.getMessage();
        }
    }
}
