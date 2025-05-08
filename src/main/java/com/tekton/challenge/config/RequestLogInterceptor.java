package com.tekton.challenge.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tekton.challenge.model.entity.RequestLog;
import com.tekton.challenge.repository.RequestLogRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class RequestLogInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;
    private final RequestLogRepo requestHistoryRepo;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        var path = request.getRequestURI();
        var queryParams = objectMapper.writeValueAsString(request.getParameterMap());
        var responseCode = response.getStatus();

        var requestHistory = new RequestLog();
        requestHistory.setEndpoint(path);
        requestHistory.setQueryParams(queryParams);
        requestHistory.setResponseCode(responseCode);
        requestHistory.setRegistrationDate(OffsetDateTime.now());
        requestHistoryRepo.save(requestHistory);
    }
}
