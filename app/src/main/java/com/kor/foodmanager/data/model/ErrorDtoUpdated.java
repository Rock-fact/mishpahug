package com.kor.foodmanager.data.model;

public class ErrorDtoUpdated {
    private int code;
    private MessageDtoUpdatedText message;

    public ErrorDtoUpdated(int code, MessageDtoUpdatedText message) {
        this.code = code;
        this.message = message;
    }

    public ErrorDtoUpdated() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public MessageDtoUpdatedText getMessage() {
        return message;
    }

    public void setMessage(MessageDtoUpdatedText message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "code=" + code + ", message=" + message + '}';
    }
}
