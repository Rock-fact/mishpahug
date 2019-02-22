package com.kor.foodmanager.data.model;

public class MessageDto {
    private int code;
    private String message;

    public MessageDto() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
