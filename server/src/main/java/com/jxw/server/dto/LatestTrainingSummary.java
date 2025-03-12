package com.jxw.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;


public class LatestTrainingSummary {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate trainingDate;
    private Integer attempts;
    private Integer hits;
    private String shootingType;
    private String suggestions;

    public LatestTrainingSummary(LocalDate trainingDate, Integer attempts, Integer hits, String shootingType, String suggestions) {
        this.trainingDate = trainingDate;
        this.attempts = attempts;
        this.hits = hits;
        this.shootingType = shootingType;
        this.suggestions = suggestions;
    }

    public LocalDate getTrainingDate() {
        return trainingDate;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public Integer getHits() {
        return hits;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setTrainingDate(LocalDate trainingDate) {
        this.trainingDate = trainingDate;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public String getShootingType() {
        return shootingType;
    }

    public void setShootingType(String shootingType) {
        this.shootingType = shootingType;
    }
}