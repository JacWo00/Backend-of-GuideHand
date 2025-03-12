package com.jxw.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.UUID;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import io.swagger.annotations.ApiModel;
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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_training_records")
@ApiModel(value="UserTrainingRecords对象", description="")
public class UserTrainingRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    @MppMultiId
    @TableField("record_id")
    private String recordId;

    @MppMultiId
    @TableField(value = "user_id") // 复合主键字段2
    private String userId;

    @TableField("training_date")
    private LocalDate trainingDate;

    @TableField("shooting_type")
    private String shootingType; // three point shoot/middle shoot/free shoot

    @TableField("training_method")
    private String trainingMethod; // single or multiple

    @TableField("attempts")
    private Integer attempts;

    @TableField("hits")
    private Integer hits;

    @TableField("ai_analysis")
    private String aiAnalysis;

    @TableField("ai_suggestions")
    private String aiSuggestions;

    @TableField("weakness_points")
    private String weaknessPoints;

    public UserTrainingRecords(String userId, LocalDate trainingDate, String shootingType, String trainingMethod, Integer attempts, Integer hits, String aiAnalysis, String aiSuggestions, String weaknessPoints) {
        this.recordId = UUID.randomUUID().toString();
        this.userId = userId;
        this.trainingDate = trainingDate;

        this.shootingType = shootingType;
        this.trainingMethod = trainingMethod;
        this.attempts = attempts;
        this.hits = hits;
        this.aiAnalysis = aiAnalysis;
        this.aiSuggestions = aiSuggestions;
        this.weaknessPoints = weaknessPoints;
    }

    public UserTrainingRecords() {
    }

    public String getRecordId() {
        return recordId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDate getTrainingDate() {
        return trainingDate;
    }

    public String getShootingType() {
        return shootingType;
    }

    public String getTrainingMethod() {
        return trainingMethod;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public Integer getHits() {
        return hits;
    }

    public String getAiAnalysis() {
        return aiAnalysis;
    }

    public String getAiSuggestions() {
        return aiSuggestions;
    }

    public String getWeaknessPoints() {
        return weaknessPoints;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTrainingDate(LocalDate trainingDate) {
        this.trainingDate = trainingDate;
    }

    public void setShootingType(String shootingType) {
        this.shootingType = shootingType;
    }

    public void setTrainingMethod(String trainingMethod) {
        this.trainingMethod = trainingMethod;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public void setAiAnalysis(String aiAnalysis) {
        this.aiAnalysis = aiAnalysis;
    }

    public void setAiSuggestions(String aiSuggestions) {
        this.aiSuggestions = aiSuggestions;
    }

    public void setWeaknessPoints(String weaknessPoints) {
        this.weaknessPoints = weaknessPoints;
    }
}
