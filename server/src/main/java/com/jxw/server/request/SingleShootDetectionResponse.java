package com.jxw.server.request;


public class SingleShootDetectionResponse {
    private String code;
    private String message;
    private Integer id;


    public SingleShootDetectionResponse(String code, String message, Integer id) {
        this.code = code;
        this.message = message;
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Integer getId() {
        return id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
