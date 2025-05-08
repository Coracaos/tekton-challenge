package com.tekton.challenge.service;

import com.tekton.challenge.model.dto.PaginationDto;
import com.tekton.challenge.model.dto.RequestLogDto;
import com.tekton.challenge.model.entity.RequestLog;
import com.tekton.challenge.model.response.RequestHistoryResp;
import com.tekton.challenge.repository.RequestLogRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class RequestLogService {

    private final RequestLogRepo requestLogRepo;

    public RequestHistoryResp getRequestHistory(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("registrationDate").descending());
        Page<RequestLog> requestLogPage = requestLogRepo.findAll(pageable);

        var requestHistoryResp = new RequestHistoryResp();

        var requestLogsDto = new ArrayList<RequestLogDto>();
        for(var requestLog : requestLogPage.getContent()){
            var requestLogDto = new RequestLogDto();
            requestLogDto.setId(requestLog.getId());
            requestLogDto.setEndpoint(requestLog.getEndpoint());
            requestLogDto.setQueryParams(requestLog.getQueryParams());
            requestLogDto.setResponseCode(requestLog.getResponseCode());
            requestLogDto.setRegistrationDate(requestLog.getRegistrationDate());
            requestLogsDto.add(requestLogDto);
        }
        requestHistoryResp.setValues(requestLogsDto);

        var paginationDto = new PaginationDto(
                requestLogPage.getNumberOfElements(),
                requestLogPage.getTotalPages()
        );
        requestHistoryResp.setPagination(paginationDto);

        return requestHistoryResp;
    }
}
