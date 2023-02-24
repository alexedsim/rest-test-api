package com.alex.restapidemo.controller;

import com.alex.restapidemo.model.UserAgent;
import com.alex.restapidemo.service.UserAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/useragents")
public class UserAgentController {

    private final UserAgentService userAgentService;

    @Autowired
    public UserAgentController(UserAgentService userAgentService) {
        this.userAgentService = userAgentService;
    }

    @PostMapping
    public ResponseEntity<Void> createUserAgent(@RequestHeader(value = "User-Agent") String userAgentString) {
        userAgentService.createUserAgent(userAgentString);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<UserAgent>> getLastTenUserAgents() {
        List<UserAgent> lastTenUserAgents = userAgentService.getLastTenUserAgents();
        return ResponseEntity.ok(lastTenUserAgents);
    }
}