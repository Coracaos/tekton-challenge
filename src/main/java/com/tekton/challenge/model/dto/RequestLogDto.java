package com.tekton.challenge.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class RequestLogDto {

    private Long id;
    private String endpoint;
    private String queryParams;
    private Integer responseCode;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime registrationDate;
}
