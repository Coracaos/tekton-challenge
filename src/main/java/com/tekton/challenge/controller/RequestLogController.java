package com.tekton.challenge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tekton.challenge.service.RequestLogService;
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

    @GetMapping(value = "/requests")
    public ResponseEntity<?> getRequestHistory(
            @NotNull @Min(1) @RequestParam("page") Integer page,
            @Min(1) @Max(50) @RequestParam("size") Integer size) throws JsonProcessingException {
        return ResponseEntity.ok(requestLogService.getRequestHistory(page, size));
    }
}
