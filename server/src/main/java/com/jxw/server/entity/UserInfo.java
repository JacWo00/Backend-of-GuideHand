package com.jxw.server.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
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
@TableName("users")
@ApiModel(value="UserInfo对象", description="")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private String userId;

    @TableField("username")
    private String username;

    @TableField("gender")
    private String gender;

    @TableField("height")
    private Double height;

    @TableField("weight")
    private Double weight;

    @TableField("location")
    private String location;

    @TableField("skill_level")
    private String skillLevel;

    @TableField("age")
    private Integer age;

    @TableField("session_id")
    private String sessionId;

    // 无参构造
    public UserInfo() {
    }

    public UserInfo(String userId, String username, String gender, Double height, Double weight, String location, String skillLevel, Integer age, String sessionId) {
        this.userId = userId;
        this.username = username;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.location = location;
        this.skillLevel = skillLevel;
        this.age = age;
        this.sessionId = sessionId;
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getGender() {
        return gender;
    }

    public Double getHeight() {
        return height;
    }

    public Double getWeight() {
        return weight;
    }

    public String getLocation() {
        return location;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public Integer getAge() {
        return age;
    }

    public String getSessionId() {
        return sessionId;
    }

    // Setters
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
