package com.alex.restapidemo;
import com.alex.restapidemo.controller.HeadersController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HeadersController.class)
public class HeadersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetHeaders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/headers/mirror")
                        .header("header1", "value1")
                        .header("header2", "value2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.header1").value("value1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.header2").value("value2"));
    }
}
