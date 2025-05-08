package com.tekton.challenge.controller;

import com.tekton.challenge.service.RequestLogService;
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
    public ResponseEntity<?> getRequestHistory(@RequestParam("page") int page,  @RequestParam("size") int size){
        return ResponseEntity.ok(requestLogService.getRequestHistory(page, size));
    }
}
