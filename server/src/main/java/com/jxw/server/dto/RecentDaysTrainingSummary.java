package com.jxw.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class RecentDaysTrainingSummary {
    private Integer trainingCount;
    private Integer attempts;
    private Integer hits;
    private String suggestions;

    public RecentDaysTrainingSummary(Integer trainingCount, Integer attempts, Integer hits, String suggestions) {
        this.trainingCount = trainingCount;
        this.attempts = attempts;
        this.hits = hits;
        this.suggestions = suggestions;
    }

    public Integer getTrainingCount() {
        return trainingCount;
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

    public void setTrainingCount(Integer trainingCount) {
        this.trainingCount = trainingCount;
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
}
