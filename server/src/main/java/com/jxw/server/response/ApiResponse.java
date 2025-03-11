package com.jxw.server.response;



public class ApiResponse {
    private String code;
    private String message;
    private Object data;

    public ApiResponse(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ApiResponse success() {
        return new ApiResponse("SUCCESS", "Operation succeeded", null);
    }

    public static ApiResponse error(String message) {
        return new ApiResponse("FAIL", message, null);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
