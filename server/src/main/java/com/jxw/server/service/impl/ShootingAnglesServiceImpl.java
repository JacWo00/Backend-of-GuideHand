package com.jxw.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jxw.server.entity.ShootingAngles;
import com.jxw.server.mapper.ShootingAnglesMapper;
import com.jxw.server.service.IShootingAnglesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShootingAnglesServiceImpl implements IShootingAnglesService {
    @Autowired
    ShootingAnglesMapper shootingAnglesMapper;

    @Override
    public Boolean save(ShootingAngles shootingAngles) {
        try {
            int result = shootingAnglesMapper.insert(shootingAngles);
            return result > 0; // 插入成功返回 true，否则返回 false
        } catch (Exception e) {
            throw new RuntimeException("保存投篮角度数据失败", e); // 抛出异常，便于上层处理
        }
    }

    @Override
    public ShootingAngles getShootingAngles(String userId, String recordId) {
        try {
            // 构建查询条件
            QueryWrapper<ShootingAngles> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId)
                    .eq("record_id", recordId);

            // 查询数据
            return shootingAnglesMapper.selectOne(queryWrapper);
        } catch (Exception e) {
            throw new RuntimeException("查询投篮角度数据失败", e); // 抛出异常，便于上层处理
        }
    }
}
