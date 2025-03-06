package com.jxw.server.request;

import lombok.Data;

@Data
public class SingleShootDetectionResponse {
    private String code;
    private String message;
    private Integer id;


    public SingleShootDetectionResponse(String code, String message, Integer id) {
        this.code = code;
        this.message = message;
        this.id = id;
    }
}
