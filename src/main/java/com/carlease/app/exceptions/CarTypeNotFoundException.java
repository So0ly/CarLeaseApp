package com.carlease.app.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND)
public class CarTypeNotFoundException extends RuntimeException {
    public CarTypeNotFoundException(String message) {
        super(message);
    }
}
