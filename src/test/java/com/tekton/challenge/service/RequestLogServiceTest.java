package com.tekton.challenge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tekton.challenge.model.entity.RequestLog;
import com.tekton.challenge.model.response.RequestHistoryResp;
import com.tekton.challenge.repository.RequestLogRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestLogServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private RequestLogRepo requestLogRepo;

    @InjectMocks
    private RequestLogService requestLogService;


    @Test
    public void testGetRequestHistory() throws JsonProcessingException, JsonProcessingException {
        var requestLog1 = new RequestLog();
        requestLog1.setId(1L);
        requestLog1.setEndpoint("/api/test");
        requestLog1.setQueryParams("{\"param1\": [\"value1\"]}");
        requestLog1.setResponseCode(200);
        requestLog1.setIsOK(true);
        requestLog1.setRegistrationDate(OffsetDateTime.now());

        var page = Mockito.mock(Page.class);
        when(page.getContent()).thenReturn(List.of(requestLog1));
        when(page.getTotalElements()).thenReturn(1L);
        when(page.getTotalPages()).thenReturn(1);

        when(requestLogRepo.findAll(any(Pageable.class))).thenReturn(page);
        when(objectMapper.readValue(anyString(), any(TypeReference.class))).thenReturn(new HashMap<String, List<String>>());

        RequestHistoryResp response = requestLogService.getRequestHistory(1, 10);

        assertNotNull(response);
        assertEquals(1, response.getValues().size());
        assertEquals(1L, response.getPagination().totalElements());
        assertEquals(1, response.getPagination().totalPages());
    }

}
