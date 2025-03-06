package com.jxw.server.service;

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
public interface IUserTrainingRecordsService extends IService<UserTrainingRecords> {

    List<UserTrainingRecords> findByUserIdAndTrainingDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    boolean save(UserTrainingRecords userTrainingRecords);
}
