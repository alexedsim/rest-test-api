package com.alex.restapidemo;
import com.alex.restapidemo.model.UserAgent;
import com.alex.restapidemo.repository.UserAgentRepository;
import com.alex.restapidemo.service.UserAgentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserAgentServiceTest {
    @Mock
    private UserAgentRepository userAgentRepository;
    @InjectMocks
    private UserAgentService userAgentService;

    @BeforeEach
    void setUp() {
        try {
            MockitoAnnotations.openMocks(this);
        } catch (MockitoException e) {
            fail("Failed to initialize Mockito annotations: " + e.getMessage());
        }
    }

    @Test
    void testCreateUserAgent() {
        String userAgentString = "user agent string";
        String userAgentHash = "offKEqA3fEn0vvnQjwUI7ho0TgQqseFZulJqcO+pdnk=";

        when(userAgentRepository.findByUserAgentHash(userAgentHash)).thenReturn(Optional.empty());
        doAnswer(invocation -> {
            UserAgent savedUserAgent = invocation.getArgument(0);
            assertEquals(userAgentString, savedUserAgent.getUserAgentString());
            return savedUserAgent;
        }).when(userAgentRepository).save(any(UserAgent.class));

        userAgentService.createUserAgent(userAgentString);

        verify(userAgentRepository, times(1)).findByUserAgentHash(userAgentHash);
        verify(userAgentRepository, times(1)).save(any(UserAgent.class));
    }


    @Test
    void testCreateUserAgent_existingUser() {
        String userAgentString = "user agent string";
        String userAgentHash = "offKEqA3fEn0vvnQjwUI7ho0TgQqseFZulJqcO+pdnk=";

        /*we will use this timestamp to set our existing user,
        * after the service call our existing user will have a different timestamp
        * because of the update*/
        Timestamp firstTimestamp = Timestamp.valueOf(LocalDateTime.of(1970, 1, 1, 0, 0));

        UserAgent existingUserAgent = new UserAgent(userAgentHash, userAgentString);
        existingUserAgent.setTimestamp(firstTimestamp); // Set an old timestamp to simulate an existing user agent

        when(userAgentRepository.findByUserAgentHash(userAgentHash)).thenReturn(Optional.of(existingUserAgent));


        ArgumentCaptor<UserAgent> argument = ArgumentCaptor.forClass(UserAgent.class);
        userAgentService.createUserAgent(userAgentString);


        verify(userAgentRepository).save(argument.capture());
        UserAgent savedUserAgent = argument.getValue();

        assertEquals(savedUserAgent.getUserAgentString(), userAgentString);
        assertTrue(savedUserAgent.getTimestamp().after(firstTimestamp));
    }



    @Test
    void testGetLastTenUserAgents() {
        List<UserAgent> userAgentList = List.of(new UserAgent("hash1", "user agent string 1"), new UserAgent("hash2", "user agent string 2"));

        when(userAgentRepository.findTop10ByOrderByTimestampDesc()).thenReturn(userAgentList);

        List<UserAgent> result = userAgentService.getLastTenUserAgents();

        verify(userAgentRepository, times(1)).findTop10ByOrderByTimestampDesc();
        assertEquals(userAgentList, result);
    }
}
