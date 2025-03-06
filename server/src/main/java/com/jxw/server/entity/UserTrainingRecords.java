package com.jxw.server.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2025-02-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_training_records")
@ApiModel(value="UserTrainingRecords对象", description="")
public class UserTrainingRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "record_id", type = IdType.AUTO)
    private Integer recordId;

    @TableField("user_id")
    private Long userId;

    @TableField("training_date")
    private LocalDate trainingDate;

    @TableField("training_time")
    private BigDecimal trainingTime;

    @TableField("three_point_attempts")
    private Integer threePointAttempts;

    @TableField("three_point_hits")
    private Integer threePointHits;

    @TableField("mid_range_attempts")
    private Integer midRangeAttempts;

    @TableField("mid_range_hits")
    private Integer midRangeHits;

    @TableField("free_throw_attempts")
    private Integer freeThrowAttempts;

    @TableField("free_throw_hits")
    private Integer freeThrowHits;

    @TableField("ai_analysis")
    private String aiAnalysis;

    @TableField("ai_suggestions")
    private String aiSuggestions;

    @TableField("weakness_points")
    private String weaknessPoints;


}
