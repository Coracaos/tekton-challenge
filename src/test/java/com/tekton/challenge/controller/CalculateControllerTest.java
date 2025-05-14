package com.tekton.challenge.controller;

import com.tekton.challenge.model.response.CalculatedValueResp;
import com.tekton.challenge.service.CalculateService;
import com.tekton.challenge.service.RequestLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculateController.class)
public class CalculateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculateService calculateService;

    @MockBean
    private RequestLogService requestLogService;

    @Test
    void givenRequestValid_whenGetCalculatedValue_thenOk() throws Exception {
        // Arrange
        when(calculateService.calculateValue(any(BigDecimal.class), any(BigDecimal.class)))
                .thenReturn(new CalculatedValueResp(BigDecimal.ONE));

        // Act & Assert
        mockMvc.perform(get("/api/calculate")
                        .param("num1", "20.10")
                        .param("num2", "21.30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value( 1.0));

    }

    @Test
    void givenRequestInvalid_whenGetCalculatedValue_thenBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/calculate")
                        .param("num1", "-10.10")
                        .param("num2", "21.30"))
                .andExpect(status().isBadRequest());

    }

}
