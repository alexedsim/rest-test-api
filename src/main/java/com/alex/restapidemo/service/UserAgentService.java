package com.alex.restapidemo.service;

import com.alex.restapidemo.exception.UserAgentNotFoundException;
import com.alex.restapidemo.model.UserAgent;
import com.alex.restapidemo.repository.UserAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserAgentService {
    private final UserAgentRepository userAgentRepository;

    @Autowired
    public UserAgentService(UserAgentRepository userAgentRepository) {
        this.userAgentRepository = userAgentRepository;
    }


    @Transactional
    public void createUserAgent(String userAgentString) {
        String userAgentHash = hashUserAgent(userAgentString);
        Optional<UserAgent> existingUserAgent = userAgentRepository.findById(userAgentHash);
        if (existingUserAgent.isPresent()) {
            UserAgent userAgentToUpdate = existingUserAgent.get();
            userAgentToUpdate.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
            userAgentRepository.save(userAgentToUpdate);
        } else {
            UserAgent newUserAgent = new UserAgent();
            newUserAgent.setHash(userAgentHash);
            newUserAgent.setUserAgentString(userAgentString);
            newUserAgent.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
            userAgentRepository.save(newUserAgent);
        }
    }

    public List<UserAgent> getLastTenUserAgents() {
        return userAgentRepository.findTop10ByOrderByTimestampDesc();
    }

    private String hashUserAgent(String userAgentString) {
        // Use SHA-256 hashing algorithm to create a hash of the user agent string
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(userAgentString.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to create user agent hash: " + e.getMessage(), e);
        }
    }

}