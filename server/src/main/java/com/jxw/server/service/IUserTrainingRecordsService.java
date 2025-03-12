package com.jxw.server.service;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.jxw.server.dto.LatestTrainingSummary;
import com.jxw.server.entity.UserTrainingRecords;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2025-02-25
 */
public interface IUserTrainingRecordsService extends IMppService<UserTrainingRecords> {
    List<UserTrainingRecords> findByUserIdAndTrainingDateBetween(String userId, LocalDate startDate, LocalDate endDate);

    boolean save(UserTrainingRecords userTrainingRecords);

    LatestTrainingSummary getLatestTraining(String userId);

    List<LatestTrainingSummary> getLast7DaysSummary(String userId);

    UserTrainingRecords findByUserIdAndRecordId(String userId,String recordId);
}
