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
    void testCreateUserAgent_existingUserTwo() {
        String userAgentString = "user agent string";
        String userAgentHash = "offKEqA3fEn0vvnQjwUI7ho0TgQqseFZulJqcO+pdnk=";

        UserAgent existingUserAgent = new UserAgent(userAgentHash, userAgentString);
        existingUserAgent.setTimestamp(Timestamp.valueOf(LocalDateTime.now().minusHours(1))); // Set an old timestamp to simulate an existing user agent

        when(userAgentRepository.findByUserAgentHash(userAgentHash)).thenReturn(Optional.of(existingUserAgent));

        // Create a UserAgent object with a known timestamp
        Timestamp knownTimestamp = Timestamp.valueOf(LocalDateTime.of(2022, 1, 1, 0, 0));
        UserAgent expectedUserAgent = new UserAgent(userAgentHash, userAgentString);
        expectedUserAgent.setTimestamp(knownTimestamp);

        ArgumentCaptor<UserAgent> argument = ArgumentCaptor.forClass(UserAgent.class);
        userAgentService.createUserAgent(userAgentString);


        verify(userAgentRepository).save(argument.capture());
        UserAgent savedUserAgent = argument.getValue();

        assertEquals(expectedUserAgent.getUserAgentString(), savedUserAgent.getUserAgentString());
        assertTrue(savedUserAgent.getTimestamp().after(knownTimestamp));
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
