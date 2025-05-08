package com.tekton.challenge.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Data
@Schema(description = "request log information")
public class RequestLogDto {

    @Schema(description = "unique ID of the request log", example = "1023")
    private Long id;

    @Schema(description = "endpoint that was called", example = "/api/calculate")
    private String endpoint;

    @Schema(
            description = "query parameters sent with the request",
            example = "{\"num1\": [\"10.11\"], \"num2\": [\"10.22\"]}"
    )
    private Map<String, List<String>> queryParams;

    @Schema(description = "http response code returned", example = "200")
    private Integer responseCode;

    @Schema(description = "the request was successful", example = "true")
    private Boolean isOk;

    @Schema(
            description = "Timestamp when the request was registered",
            type = "string",
            format = "date-time",
            example = "2025-05-08T14:32:45.123Z"
    )
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime registrationDate;
}
