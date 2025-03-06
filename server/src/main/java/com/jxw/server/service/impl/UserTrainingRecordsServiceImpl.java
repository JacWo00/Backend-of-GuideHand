package com.jxw.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jxw.server.entity.UserTrainingRecords;
import com.jxw.server.mapper.UserTrainingRecordsMapper;
import com.jxw.server.service.IUserTrainingRecordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
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
public class UserTrainingRecordsServiceImpl extends ServiceImpl<UserTrainingRecordsMapper, UserTrainingRecords> implements IUserTrainingRecordsService {

    @Autowired
    private UserTrainingRecordsMapper userTrainingRecordsMapper;
    @Override
    public List<UserTrainingRecords> findByUserIdAndTrainingDateBetween(Long userId, LocalDate startDate, LocalDate endDate) {

        QueryWrapper<UserTrainingRecords> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId)
                .ge("training_date",startDate)
                .le("training_date",endDate);
        return userTrainingRecordsMapper.selectList(queryWrapper);
    }

    @Override
    public boolean save(UserTrainingRecords userTrainingRecords) {
        return userTrainingRecordsMapper.insert(userTrainingRecords) > 0;
    }
}
