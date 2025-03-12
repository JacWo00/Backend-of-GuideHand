package com.jxw.server.dto;

public class BeforeTrainingRecentSummary {
    private LatestTrainingSummary latestTrainingSummary;
    private RecentDaysTrainingSummary recentDaysTrainingSummary;

    public BeforeTrainingRecentSummary(LatestTrainingSummary latestTrainingSummary, RecentDaysTrainingSummary recentDaysTrainingSummary) {
        this.latestTrainingSummary = latestTrainingSummary;
        this.recentDaysTrainingSummary = recentDaysTrainingSummary;
    }

    public LatestTrainingSummary getLatestTrainingSummary() {
        return latestTrainingSummary;
    }

    public RecentDaysTrainingSummary getRecentDaysTrainingSummary() {
        return recentDaysTrainingSummary;
    }

    public void setLatestTrainingSummary(LatestTrainingSummary latestTrainingSummary) {
        this.latestTrainingSummary = latestTrainingSummary;
    }

    public void setRecentDaysTrainingSummary(RecentDaysTrainingSummary recentDaysTrainingSummary) {
        this.recentDaysTrainingSummary = recentDaysTrainingSummary;
    }
}
