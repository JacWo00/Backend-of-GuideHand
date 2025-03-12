package com.jxw.server.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.jxw.server.dto.LatestTrainingSummary;
import com.jxw.server.entity.UserTrainingRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2025-02-25
 */
public interface UserTrainingRecordsMapper extends MppBaseMapper<UserTrainingRecords> {
    @Select("SELECT training_date, " +
            "       attempts, " +
            "       hits, " +
            "       shooting_type AS shootingType, "+
            "       ai_suggestions AS suggestions " +
            "FROM user_training_records " +
            "WHERE user_id = #{userId} " +
            "  AND training_date >= CURRENT_DATE - INTERVAL '7' DAY " +
            "ORDER BY training_date DESC")
    List<LatestTrainingSummary> selectLast7DaysSummary(@Param("userId") String userId);

    @Select("""
    SELECT
        training_date,
        attempts,
        hits,
        shooting_type AS shootingType,
        ai_suggestions
    FROM user_training_records
    WHERE user_id = #{userId}
    ORDER BY training_date DESC
    LIMIT 1
""")
    LatestTrainingSummary selectLastTraining(@Param("userId") String userId);
}
