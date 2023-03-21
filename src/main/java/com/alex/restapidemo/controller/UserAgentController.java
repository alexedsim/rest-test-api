package com.alex.restapidemo.controller;

import com.alex.restapidemo.exception.UserAgentCreationException;
import com.alex.restapidemo.exception.UserAgentNotFoundException;
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
    @Autowired
    private final UserAgentService userAgentService;

    public UserAgentController(UserAgentService userAgentService) {
        this.userAgentService = userAgentService;
    }


    @PostMapping
    public ResponseEntity<Void> createUserAgent(@RequestHeader(value = "User-Agent") String userAgentString) {
        try {
            userAgentService.createUserAgent(userAgentString);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            throw new UserAgentCreationException("Failed to create user agent");
        }
    }



    @GetMapping
    public ResponseEntity<List<UserAgent>> getLastTenUserAgents() {
        List<UserAgent> lastTenUserAgents = userAgentService.getLastTenUserAgents();
        if (lastTenUserAgents.isEmpty()) {
            throw new UserAgentNotFoundException("No user agents found");
        }
        return ResponseEntity.ok(lastTenUserAgents);
    }

    /*
    public Object getUserAgentByHash(String hash1) {
    }

     */
}