package com.alex.restapidemo.service;

import com.alex.restapidemo.exception.UserAgentNotFoundException;
import com.alex.restapidemo.model.UserAgent;
import com.alex.restapidemo.repository.UserAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAgentService {

    @Autowired
    private UserAgentRepository userAgentRepository;

    public List<UserAgent> getAllUserAgents() {
        return userAgentRepository.findAll();
    }

    public UserAgent getUserAgentById(Long id) throws UserAgentNotFoundException {
        return userAgentRepository.findById(id)
                .orElseThrow(() -> new UserAgentNotFoundException("Could not find UserAgent with ID:"+id));
    }

    public UserAgent createUserAgent(UserAgent userAgent) {
        return userAgentRepository.save(userAgent);
    }

    public UserAgent updateUserAgent(Long id, UserAgent userAgentDetails) throws UserAgentNotFoundException {
        UserAgent userAgent = getUserAgentById(id);

        userAgent.setBrowser(userAgentDetails.getBrowser());
        userAgent.setOs(userAgentDetails.getOs());
        userAgent.setDeviceType(userAgentDetails.getDeviceType());

        return userAgentRepository.save(userAgent);
    }

    public void deleteUserAgent(Long id) throws UserAgentNotFoundException {
        UserAgent userAgent = getUserAgentById(id);
        userAgentRepository.delete(userAgent);
    }

}