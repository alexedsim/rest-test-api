package com.alex.restapidemo.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserAgentNotFoundException extends RuntimeException {

    public UserAgentNotFoundException(String message) {
        super(message);
    }
}