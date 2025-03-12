package com.jxw.server.dto;

import com.jxw.server.entity.ShootingAnalysis;
import com.jxw.server.entity.ShootingAngles;

public class DetailedAnalysis {
    private ShootingAngles shootingAngles;
    private String analysis;
    private String suggestions;
    private String weaknessPoints;

    public DetailedAnalysis(ShootingAngles shootingAngles, String analysis, String suggestions, String weaknessPoints) {
        this.shootingAngles = shootingAngles;
        this.analysis = analysis;
        this.suggestions = suggestions;
        this.weaknessPoints = weaknessPoints;
    }

    public ShootingAngles getShootingAngles() {
        return shootingAngles;
    }

    public String getAnalysis() {
        return analysis;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public String getWeaknessPoints() {
        return weaknessPoints;
    }

    public void setShootingAngles(ShootingAngles shootingAngles) {
        this.shootingAngles = shootingAngles;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public void setWeaknessPoints(String weaknessPoints) {
        this.weaknessPoints = weaknessPoints;
    }
}
