package com.alex.restapidemo.controller;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/headers/mirror")
public class HeadersController {

    @GetMapping()
    public ResponseEntity<Map<String, String>> getHeaders(HttpHeaders headers) {
        return ResponseEntity.ok(headers.toSingleValueMap());
    }
}