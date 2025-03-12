package com.jxw.server.request;

public class LlmConsultRequest {
    private String userId;
    private String recordId;
    private String inquiry;
    private Boolean context;

    public LlmConsultRequest(String userId, String recordId, String inquiry, Boolean context) {
        this.userId = userId;
        this.recordId = recordId;
        this.inquiry = inquiry;
        this.context = context;
    }

    public String getUserId() {
        return userId;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getInquiry() {
        return inquiry;
    }

    public Boolean getContext() {
        return context;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public void setInquiry(String inquiry) {
        this.inquiry = inquiry;
    }

    public void setContext(Boolean context) {
        this.context = context;
    }
}
