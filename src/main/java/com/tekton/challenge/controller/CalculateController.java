package com.tekton.challenge.controller;

import com.tekton.challenge.model.response.CalculatedValueResp;
import com.tekton.challenge.service.CalculateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CalculateController {

   private final CalculateService calculateService;

   @GetMapping(value = "/calculate")
   public ResponseEntity<CalculatedValueResp> getCalculatedValue(
           @RequestParam("num1") BigDecimal num1,
           @RequestParam("num2") BigDecimal num2
   ){
      return ResponseEntity.ok(calculateService.calculateValue(num1, num2));
   }

}
