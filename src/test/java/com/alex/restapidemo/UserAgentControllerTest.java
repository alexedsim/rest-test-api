package com.alex.restapidemo;
import com.alex.restapidemo.controller.UserAgentController;
import com.alex.restapidemo.exception.UserAgentCreationException;
import com.alex.restapidemo.model.UserAgent;
import com.alex.restapidemo.service.UserAgentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserAgentControllerTest {
    @Mock
    private UserAgentService userAgentService;

    @InjectMocks
    private UserAgentController userAgentController;

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
        userAgentController.createUserAgent("user agent string");

        // Verify that the createUserAgent method in the UserAgentService class was called once with the correct argument
        verify(userAgentService, times(1)).createUserAgent("user agent string");

    }

    @Test
    void testCreateUserAgent_Exception() {
        // If the createUserAgent method in the UserAgentService class throws an exception, the createUserAgent method in the UserAgentController class should throw a UserAgentCreationException

        doThrow(new RuntimeException()).when(userAgentService).createUserAgent(anyString());

        assertThrows(UserAgentCreationException.class, () -> userAgentController.createUserAgent("user agent string"));
    }

    @Test
    void testGetLastTenUserAgents() {
        // Create a list of UserAgent objects to use in the test
        UserAgent userAgent1 = new UserAgent("hash1", "user agent string 1");
        UserAgent userAgent2 = new UserAgent("hash2", "user agent string 2");
        UserAgent userAgent3 = new UserAgent("hash3", "user agent string 3");
        List<UserAgent> userAgentList = Arrays.asList(userAgent1, userAgent2, userAgent3);

        // Set up the mock object to return the list of UserAgent objects when the getLastTenUserAgents method is called
        when(userAgentService.getLastTenUserAgents()).thenReturn(userAgentList);

        // Call the getLastTenUserAgents method in the UserAgentController class and verify that it returns the correct ResponseEntity object
        ResponseEntity<List<UserAgent>> responseEntity =  userAgentController.getLastTenUserAgents();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userAgentList, responseEntity.getBody());
    }

    /* Other possible test cases
    @Test
    void testGetUserAgentByHash() {
        // Create a UserAgent object to use in the test
        UserAgent userAgent = new UserAgent("hash1", "user agent string");

        // Set up the mock object to return the UserAgent object when the getUserAgentByHash method is called with the correct argument
        when(userAgentService.getUserAgentByHash("hash1")).thenReturn(Optional.of(userAgent));

        // Call the getUserAgentByHash method in the UserAgentController class with the correct argument and verify that it returns the correct ResponseEntity object
        ResponseEntity<UserAgent> responseEntity =(ResponseEntity<UserAgent>) userAgentController.getUserAgentByHash("hash1");//this endpoint does not exist or it is not implemented yet,
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userAgent, responseEntity.getBody());
    }

    @Test
    void testGetUserAgentByHash_NotFound() {
        // If the getUserAgentByHash method in the UserAgentService class returns an empty Optional object, the getUserAgentByHash method in the UserAgentController class should throw a UserAgentNotFoundException
        String hash = "hash";
        when(userAgentService.getUserAgentByHash(hash)).thenReturn(Optional.empty());

        assertThrows(UserAgentNotFoundException.class, () -> {
            userAgentController.getUserAgentByHash(hash);
        });
    }

    @Test
    void testGetUserAgentByHash_Exception() {
        // If the getUserAgentByHash method in the UserAgentService class throws an exception, the getUserAgentByHash method in the UserAgentController class should throw that exception
        String hash = "hash";
        when(userAgentService.getUserAgentByHash(hash)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> {
            userAgentController.getUserAgentByHash(hash);
        });
    }
    */
}
