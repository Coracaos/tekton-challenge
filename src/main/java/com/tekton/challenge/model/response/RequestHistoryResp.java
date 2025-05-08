package com.tekton.challenge.model.response;

import com.tekton.challenge.model.dto.PaginationDto;
import com.tekton.challenge.model.dto.RequestLogDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "paginated list of request logs")
public class RequestHistoryResp {

    @Schema(description = "list of request log")
    private List<RequestLogDto> values;

    @Schema(description = "pagination information")
    private PaginationDto pagination;

}
