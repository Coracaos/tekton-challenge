package com.tekton.challenge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tekton.challenge.model.dto.PaginationDto;
import com.tekton.challenge.model.dto.RequestLogDto;
import com.tekton.challenge.model.entity.RequestLog;
import com.tekton.challenge.model.response.RequestHistoryResp;
import com.tekton.challenge.repository.RequestLogRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RequestLogService {

    private final ObjectMapper objectMapper;
    private final RequestLogRepo requestLogRepo;

    public RequestHistoryResp getRequestHistory(Integer page, Integer size) throws JsonProcessingException {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("registrationDate").descending());
        Page<RequestLog> requestLogPage = requestLogRepo.findAll(pageable);

        var requestHistoryResp = new RequestHistoryResp();

        var requestLogsDto = new ArrayList<RequestLogDto>();
        for(var requestLog : requestLogPage.getContent()){
            var requestLogDto = new RequestLogDto();
            requestLogDto.setId(requestLog.getId());
            requestLogDto.setEndpoint(requestLog.getEndpoint());
            requestLogDto.setQueryParams(objectMapper.readValue(
                    requestLog.getQueryParams(),
                    new TypeReference<Map<String, List<String>>>() {}
            ));
            requestLogDto.setResponseCode(requestLog.getResponseCode());
            requestLogDto.setRegistrationDate(requestLog.getRegistrationDate());
            requestLogsDto.add(requestLogDto);
        }
        requestHistoryResp.setValues(requestLogsDto);

        var paginationDto = new PaginationDto(
                requestLogPage.getTotalElements(),
                requestLogPage.getTotalPages()
        );
        requestHistoryResp.setPagination(paginationDto);

        return requestHistoryResp;
    }

    @Async
    public void saveRequestLog(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        var path = request.getRequestURI();
        var queryParams = objectMapper.writeValueAsString(request.getParameterMap());
        var responseCode = response.getStatus();

        var requestHistory = new RequestLog();
        requestHistory.setEndpoint(path);
        requestHistory.setQueryParams(queryParams);
        requestHistory.setResponseCode(responseCode);
        requestHistory.setRegistrationDate(OffsetDateTime.now());
        requestLogRepo.save(requestHistory);
    }
}
