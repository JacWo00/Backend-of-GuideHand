package com.jxw.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.jxw.server.dto.LatestTrainingSummary;
import com.jxw.server.entity.UserTrainingRecords;
import com.jxw.server.mapper.UserTrainingRecordsMapper;
import com.jxw.server.service.IUserTrainingRecordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2025-02-25
 */
@Service
public class UserTrainingRecordsServiceImpl extends MppServiceImpl<UserTrainingRecordsMapper, UserTrainingRecords> implements IUserTrainingRecordsService {

    @Autowired
    private UserTrainingRecordsMapper userTrainingRecordsMapper;


    @Override
    public boolean save(UserTrainingRecords userTrainingRecords) {
        return userTrainingRecordsMapper.insert(userTrainingRecords) > 0;
    }

    // 获取最近一次训练记录
    public LatestTrainingSummary getLatestTraining(String userId) {
        return this.baseMapper.selectLastTraining(userId);
    }

    @Override
    public List<UserTrainingRecords> findByUserIdAndTrainingDateBetween(String userId, LocalDate startDate, LocalDate endDate) {
        QueryWrapper<UserTrainingRecords> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId)
                .ge("training_date",startDate)
                .le("training_date",endDate);
        return userTrainingRecordsMapper.selectList(queryWrapper);
    }

    // 获取最近7天训练汇总（按天分组）
    public List<LatestTrainingSummary> getLast7DaysSummary(String userId) {
        return this.baseMapper.selectLast7DaysSummary(userId);
    }

    @Override
    public UserTrainingRecords findByUserIdAndRecordId(String userId, String recordId) {
        QueryWrapper<UserTrainingRecords> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId)
                .eq("record_id",recordId);
        return userTrainingRecordsMapper.selectOne(queryWrapper);
    }
}
