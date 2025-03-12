package com.jxw.server.entity;

public class ShootingAnalysis {
    private String analysis;
    private String suggestions;
    private String weaknessPoints;

    public ShootingAnalysis(String analysis, String suggestions, String weaknessPoints) {
        this.analysis = analysis;
        this.suggestions = suggestions;
        this.weaknessPoints = weaknessPoints;
    }

    public ShootingAnalysis(String input) {
        this.analysis = extractContent(input, "分析：");
        this.suggestions = extractContent(input, "建议：");
        this.weaknessPoints = extractContent(input, "缺点：");
    }

    private String extractContent(String input, String key) {
        int startIndex = input.indexOf(key);
        if (startIndex == -1) {
            return ""; // 如果没有找到关键词，返回空字符串
        }
        startIndex += key.length();
        int endIndex = input.length();

        // 查找下一个关键词的索引，确保获取的是当前部分的内容
        for (String nextKey : new String[]{"分析：", "建议：", "缺点："}) {
            if (!nextKey.equals(key)) {
                int nextIndex = input.indexOf(nextKey, startIndex);
                if (nextIndex != -1 && nextIndex < endIndex) {
                    endIndex = nextIndex;
                }
            }
        }
        return input.substring(startIndex, endIndex);
    }

    public ShootingAnalysis(){}

    public String getAnalysis() {
        return analysis;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public String getWeaknessPoints() {
        return weaknessPoints;
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
