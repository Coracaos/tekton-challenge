package com.tekton.challenge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tekton.challenge.model.entity.RequestLog;
import com.tekton.challenge.model.response.RequestHistoryResp;
import com.tekton.challenge.repository.RequestLogRepo;
import com.tekton.challenge.support.JsonTestUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestLogServiceTest {

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

    @Mock
    private RequestLogRepo requestLogRepo;

    @InjectMocks
    private RequestLogService requestLogService;

    @Test
     void givenValidPageableRequestLog_whenGetRequestHistory_thenReturnsExpectedResult() throws IOException {
        // Arrange
        var requestLogs = JsonTestUtils.readJsonFromFile(
                "data/service/RequestLogService/RequestLogs.json",
                new TypeReference<List<RequestLog>>() {
                }
        );

        Page<RequestLog> requestLogsPage = new PageImpl<>(
                requestLogs,
                PageRequest.of(0, 10),
                1
        );

        when(requestLogRepo.findAll(any(Pageable.class))).thenReturn(requestLogsPage);

        var expectedOutput = JsonTestUtils.readJsonFromFile(
                "data/service/RequestLogService/RequestHistoryResp.json",
                RequestHistoryResp.class
        );

        // Act
        var requestHistoryResp = requestLogService.getRequestHistory(1, 10);

        // Assert
        assertEquals(expectedOutput, requestHistoryResp);

    }

    @Test
    void giveEmptyPageableRequestLog_whenGetRequestHistory_thenReturnsExcectedResult() throws IOException {
        // Arrange
        Page<RequestLog> requestLogsEmptyPage = new PageImpl<>(
                Collections.emptyList(),
                PageRequest.of(0, 10),
                0
        );
        when(requestLogRepo.findAll(any(Pageable.class))).thenReturn(requestLogsEmptyPage);

        // Act
        var requestHistoryResp = requestLogService.getRequestHistory(1, 10);

        // Assert
        assertEquals(0, requestHistoryResp.getValues().size());
        assertEquals(0, requestHistoryResp.getPagination().totalElements());
        assertEquals(0, requestHistoryResp.getPagination().totalPages());
    }

    @Test
    void givenValidParams_whenSaveRequestLog_thenSaveRequestLog() throws IOException{

        // Arrange
        var path = "/api/log";
        var queryParams = Map.of("num1", new String[]{"10.23"});
        var responseCode = 200;
        var captorRequestLog = ArgumentCaptor.forClass(RequestLog.class);

        // Act
        requestLogService.saveRequestLog(path, queryParams, responseCode);

        // Assert
        verify(requestLogRepo).save(captorRequestLog.capture());
        var capturedRequestLog = captorRequestLog.getValue();
        assertEquals(path, capturedRequestLog.getEndpoint());
        assertEquals(objectMapper.writeValueAsString(queryParams), capturedRequestLog.getQueryParams());
        assertEquals(responseCode, capturedRequestLog.getResponseCode());
        assertNotNull(capturedRequestLog.getRegistrationDate());

    }

}
