package com.tekton.challenge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tekton.challenge.model.response.ApiErrorResp;
import com.tekton.challenge.model.response.RequestHistoryResp;
import com.tekton.challenge.service.RequestLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/metadata")
@RequiredArgsConstructor
public class RequestLogController {

    private final RequestLogService requestLogService;

    @Operation(summary = "returns information about the requests made to the API")
    @GetMapping(value = "/requests")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "request information retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "invalid input parameters",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResp.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResp.class)
                    )
            )
    })
    public ResponseEntity<RequestHistoryResp> getRequestHistory(
            @Parameter(
                    description = "page number (starts at 1)",
                    example = "1",
                    required = true,
                    schema = @Schema(type = "number", format = "integer", minimum = "1")
            )
            @NotNull @Min(1) @RequestParam("page") Integer page,
            @Parameter(
                    description = "Number of items per page (min: 1, max: 50)",
                    example = "10",
                    required = true,
                    schema = @Schema(type = "number", format = "integer", minimum = "1", maximum = "50")
            )
            @Min(1) @Max(50) @RequestParam("size") Integer size) throws JsonProcessingException {
        return ResponseEntity.ok(requestLogService.getRequestHistory(page, size));
    }
}
