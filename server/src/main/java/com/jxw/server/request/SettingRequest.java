package com.jxw.server.request;


import org.jetbrains.annotations.NotNull;


public class SettingRequest {
    @NotNull
    private String userId;

    @NotNull
    private String trainingMethod;

    @NotNull
    private String theme;

    public SettingRequest(@NotNull String userId, @NotNull String trainingMethod, @NotNull String theme) {
        this.userId = userId;
        this.trainingMethod = trainingMethod;
        this.theme = theme;
    }

    @NotNull
    public String getUserId() {
        return userId;
    }

    @NotNull
    public String getTrainingMethod() {
        return trainingMethod;
    }

    @NotNull
    public String getTheme() {
        return theme;
    }

    public void setUserId(@NotNull String userId) {
        this.userId = userId;
    }

    public void setTrainingMethod(@NotNull String trainingMethod) {
        this.trainingMethod = trainingMethod;
    }

    public void setTheme(@NotNull String theme) {
        this.theme = theme;
    }
}
