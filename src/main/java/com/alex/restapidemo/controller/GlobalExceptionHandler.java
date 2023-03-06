package com.alex.restapidemo.controller;
import com.alex.restapidemo.exception.UserAgentCreationException;
import com.alex.restapidemo.exception.UserAgentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UserAgentNotFoundException.class)
    public ResponseEntity<String> handleUserAgentNotFoundException(UserAgentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserAgentCreationException.class)
    public ResponseEntity<String> handleUserAgentCreationException(UserAgentCreationException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
