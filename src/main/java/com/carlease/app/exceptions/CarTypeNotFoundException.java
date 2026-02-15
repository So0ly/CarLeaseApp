package com.carlease.app.exceptions;

public class CarTypeNotFoundException extends RuntimeException {
    public CarTypeNotFoundException(String message) {
        super(message);
    }
}
