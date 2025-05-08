package com.tekton.challenge.model.response;

import com.tekton.challenge.model.dto.PaginationDto;
import com.tekton.challenge.model.dto.RequestLogDto;
import lombok.Data;

import java.util.List;

@Data
public class RequestHistoryResp {

    private List<RequestLogDto> values;

    private PaginationDto pagination;

}
