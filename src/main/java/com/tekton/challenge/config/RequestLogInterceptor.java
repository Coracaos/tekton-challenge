package com.tekton.challenge.config;

import com.tekton.challenge.service.RequestLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;


@Service
@RequiredArgsConstructor
public class RequestLogInterceptor implements HandlerInterceptor {

    private final RequestLogService requestLogService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        var path = request.getRequestURI();
        var queryParamsMap = request.getParameterMap();
        var responseCode = response.getStatus();
        requestLogService.saveRequestLog(path, queryParamsMap, responseCode);
    }
}
