package com.jxw.server.request;

public class IdOnlyRequest {
    private String userId;

    public IdOnlyRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
