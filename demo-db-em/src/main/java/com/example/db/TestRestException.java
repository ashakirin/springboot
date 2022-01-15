package com.example.db;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "test reason")
public class TestRestException extends RuntimeException {

    public TestRestException(String message) {
        super(message);
    }
}
