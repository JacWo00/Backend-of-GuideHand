package com.jxw.server.controller;

import com.jxw.server.dto.BeforeTrainingRecentSummary;
import com.jxw.server.dto.LatestTrainingSummary;
import com.jxw.server.dto.RecentDaysTrainingSummary;
import com.jxw.server.log.Log;
import com.jxw.server.response.ApiResponse;
import com.jxw.server.security.AuthToken;
import com.jxw.server.service.IUserTrainingRecordsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("/history")
public class HistoryRecordsController {
    @Autowired
    IUserTrainingRecordsService userTrainingRecordsService;

    private static final Logger logger = LoggerFactory.getLogger(DetectionController.class);

    private final Executor asyncTaskExecutor;
    private final Executor ioExecutor;

    @Log
    @AuthToken
    @GetMapping("/beforeTraining")
    public CompletableFuture<ApiResponse> getBeforeTrainingAsync(@RequestParam("userId") String userId) {
        // @RequestBody IdOnlyRequest request
        //String userId = request.getUserId();

        // 并行获取数据
        CompletableFuture<LatestTrainingSummary> latestFuture =
                CompletableFuture.supplyAsync(() ->
                        userTrainingRecordsService.getLatestTraining(userId), ioExecutor);

        CompletableFuture<List<LatestTrainingSummary>> weekFuture =
                CompletableFuture.supplyAsync(() ->
                        userTrainingRecordsService.getLast7DaysSummary(userId), ioExecutor);

        return latestFuture.thenCombineAsync(weekFuture, (latest, weekData) -> {
            RecentDaysTrainingSummary recent = processWeekData(weekData);
            return new ApiResponse("200", "Success", new BeforeTrainingRecentSummary(latest, recent));
        }, asyncTaskExecutor);
    }

    private RecentDaysTrainingSummary processWeekData(List<LatestTrainingSummary> weekData) {
        int hits=0;
        int attempts=0;
        StringBuffer sb=new StringBuffer();
        for(LatestTrainingSummary summary:weekData){
            hits+=summary.getHits();
            attempts+=summary.getAttempts();
            sb.append(summary.getSuggestions());
            sb.append("\n");
        }

        return new RecentDaysTrainingSummary(weekData.size(), attempts, hits, sb.toString());
    }


    // 构造函数注入（推荐）
    @Autowired
    public HistoryRecordsController(
            @Qualifier("asyncTaskExecutor") Executor asyncTaskExecutor,
            @Qualifier("ioExecutor") Executor ioExecutor
    ) {
        this.asyncTaskExecutor = asyncTaskExecutor;
        this.ioExecutor = ioExecutor;
    }
}
