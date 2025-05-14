package com.tekton.challenge.controller;

import com.tekton.challenge.model.response.RequestHistoryResp;
import com.tekton.challenge.service.RequestLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RequestLogController.class)
public class RequestLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestLogService requestLogService;

    @Test
    void givenRequestValid_whenGetRequestHistory_thenOk() throws Exception {
        // Arrange
        when(requestLogService.getRequestHistory(anyInt(), anyInt()))
                .thenReturn(new RequestHistoryResp());

        // Act & Assert
        mockMvc.perform(get("/api/metadata/requests")
                .param("page","1")
                .param("size","10"))
                .andExpect(status().isOk());

    }

    @Test
    void givenRequestInvalid_whenGetRequestHistory_thenBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/metadata/requests")
                        .param("page","0")
                        .param("size","10"))
                .andExpect(status().isBadRequest());

    }
}
