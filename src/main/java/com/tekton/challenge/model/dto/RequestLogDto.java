package com.tekton.challenge.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Data
public class RequestLogDto {

    private Long id;
    private String endpoint;
    private Map<String, List<String>> queryParams;
    private Integer responseCode;
    private Boolean isOk;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime registrationDate;
}
