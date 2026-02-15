package com.carlease.app.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
public class IncorrectTimePeriodException extends RuntimeException {
    public IncorrectTimePeriodException(String message) {
        super(message);
    }
}
