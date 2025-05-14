package com.tekton.challenge.controller;

import com.tekton.challenge.service.RequestLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MockController.class)
public class MockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestLogService requestLogService;

    @Test
    void givenRequestValid_whenGetPercentage_thenOk() throws Exception {
        mockMvc.perform(get("/api/mock/percentage"))
                .andExpect(status().isOk())
                .andExpect(content().string("0.12"));
    }

}