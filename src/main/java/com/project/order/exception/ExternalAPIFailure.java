package com.project.order.exception;

public class ExternalAPIFailure extends RuntimeException{
    private static final long serialVersionUID = 7275874979835346217L;

    private String message;

    public ExternalAPIFailure() {

    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ExternalAPIFailure(Exception e) {
        this.message=e.getMessage();
    }
}
