package com.microservice.bookstore.response;

public class StatusResponse {
    private String message;

    private Object result;

    public StatusResponse(String message, Object result) {
        this.message = message;
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
