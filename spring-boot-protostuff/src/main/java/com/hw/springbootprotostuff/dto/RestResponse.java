package com.hw.springbootprotostuff.dto;

/**
 * @author bruce on 2017/8/23.
 */
public class RestResponse{

    private String code;
    private String message;

    public RestResponse() {}

    public RestResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RestResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
