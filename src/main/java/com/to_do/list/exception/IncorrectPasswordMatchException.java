package com.to_do.list.exception;

public class IncorrectPasswordMatchException extends RuntimeException {
    public IncorrectPasswordMatchException(String message) {
        super(message);
    }
}
