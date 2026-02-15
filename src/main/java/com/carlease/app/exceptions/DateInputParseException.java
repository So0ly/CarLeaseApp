package com.carlease.app.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
public class DateInputParseException extends RuntimeException {
    public DateInputParseException(String message) {
        super(message);
    }
}
