package com.eventapp.prototype.service.exception;

public class UserAlreadyExistsException extends RuntimeException {

    private String rejectedValue;

    public UserAlreadyExistsException(final String message) {
        super(message);
    }

    public UserAlreadyExistsException(final String message, final String value) {
        this(message);
        rejectedValue = value;
    }

    public String getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(String rejectedValue) {
        this.rejectedValue = rejectedValue;
    }
}
