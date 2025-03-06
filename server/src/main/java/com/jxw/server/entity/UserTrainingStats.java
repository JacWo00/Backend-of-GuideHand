package com.jxw.server.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("user_training_stats")
@ApiModel(value="UserTrainingStats对象", description="")
public class UserTrainingStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    @TableField("training_count")
    private Integer trainingCount;

    @TableField("total_training_time")
    private BigDecimal totalTrainingTime;

    @TableField("three_point_attempts")
    private Integer threePointAttempts;

    @TableField("three_point_hits")
    private Integer threePointHits;

    @TableField("mid_range_attempts")
    private Integer midRangeAttempts;

    @TableField("mid_range_hits")
    private Integer midRangeHits;


}
