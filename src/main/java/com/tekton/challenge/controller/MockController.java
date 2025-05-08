package com.tekton.challenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Validated
@RestController
@RequestMapping("/api/mock")
public class MockController {

   @Operation(summary = "returns a percentage in decimal format")
   @GetMapping(value = "/percentage")
   public ResponseEntity<BigDecimal> getPercentage(){
       return ResponseEntity.ok(new BigDecimal("0.12"));
   }
}
