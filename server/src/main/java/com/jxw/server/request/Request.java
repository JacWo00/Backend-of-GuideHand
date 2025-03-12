package com.jxw.server.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.annotation.Nonnull;
import lombok.Data;

import java.time.LocalDate;

public class Request {
    private Long userID;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String userId;
    private String trainingMethod;
    private String theme;

    // 需要有 getter 和 setter，Spring Boot 反序列化 JSON 需要它们
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTrainingMethod() { return trainingMethod; }
    public void setTrainingMethod(String trainingMethod) { this.trainingMethod = trainingMethod; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    // 无参构造
    public Request() {
    }

    // 有参构造
    public Request(Long userID, LocalDate startDate, LocalDate endDate) {
        this.userID = userID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters
    public Long getUserID() {
        return userID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    // Setters
    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    // toString 方法
    @Override
    public String toString() {
        return "Request{" +
                "userID=" + userID +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
