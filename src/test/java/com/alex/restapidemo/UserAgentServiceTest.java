package com.alex.restapidemo;
import com.alex.restapidemo.model.UserAgent;
import com.alex.restapidemo.repository.UserAgentRepository;
import com.alex.restapidemo.service.UserAgentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserAgentServiceTest {
    @Mock
    private UserAgentRepository userAgentRepository;

    private UserAgentService userAgentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userAgentService = new UserAgentService(userAgentRepository);
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


/*
@Test
void testCreateUserAgent_UpdateExistingUserAgent() {
    String userAgentString = "user agent string";
    String userAgentHash = "user agent hash";
    UserAgent existingUserAgent = new UserAgent(userAgentHash, userAgentString);
    existingUserAgent.setTimestamp(null);
    UserAgent newUserAgent = new UserAgent(userAgentHash, userAgentString);

    when(userAgentRepository.findByUserAgentHash(userAgentHash)).thenReturn(Optional.of(existingUserAgent));
    when(userAgentRepository.save(existingUserAgent)).thenReturn(existingUserAgent);

    userAgentService.createUserAgent(userAgentString);

    verify(userAgentRepository, times(1)).save(existingUserAgent);
    verify(existingUserAgent, times(1)).setTimestamp(any());
}

*/



    @Test
    void testGetLastTenUserAgents() {
        List<UserAgent> userAgentList = List.of(new UserAgent("hash1", "user agent string 1"), new UserAgent("hash2", "user agent string 2"));

        when(userAgentRepository.findTop10ByOrderByTimestampDesc()).thenReturn(userAgentList);

        List<UserAgent> result = userAgentService.getLastTenUserAgents();

        verify(userAgentRepository, times(1)).findTop10ByOrderByTimestampDesc();
        assertEquals(userAgentList, result);
    }
}
