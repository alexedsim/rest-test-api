package com.alex.restapidemo.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserAgentCreationException extends RuntimeException{


    public UserAgentCreationException(String message) {
        super(message);
    }
}
