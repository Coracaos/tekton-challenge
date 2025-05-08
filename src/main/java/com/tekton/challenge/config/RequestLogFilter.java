package com.tekton.challenge.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tekton.challenge.model.entity.RequestLog;
import com.tekton.challenge.repository.RequestLogRepo;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class RequestLogFilter implements Filter {

    private final ObjectMapper objectMapper;
    private final RequestLogRepo requestHistoryRepo;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        var path = httpRequest.getRequestURI();
        var queryParams = objectMapper.writeValueAsString(httpRequest.getParameterMap());
        var responseCode = httpResponse.getStatus();

        var requestHistory = new RequestLog();
        requestHistory.setEndpoint(path);
        requestHistory.setQueryParams(queryParams);
        requestHistory.setResponseCode(responseCode);
        requestHistory.setRegistrationDate(OffsetDateTime.now());
        requestHistoryRepo.save(requestHistory);

        filterChain.doFilter(servletRequest, servletResponse);

    }
}
