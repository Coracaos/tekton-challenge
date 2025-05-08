package com.tekton.challenge.controller;

import com.tekton.challenge.model.response.CalculatedValueResp;
import com.tekton.challenge.service.CalculateService;
import com.tekton.challenge.validation.DecimalScale;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
           @NotNull @DecimalMin(value = "0.0", inclusive = true) @DecimalMax("1000000.00") @DecimalScale(maxScale = 2) @RequestParam("num1") BigDecimal num1,
           @NotNull @DecimalMin(value = "0.0", inclusive = true) @DecimalMax("1000000.00") @DecimalScale(maxScale = 2) @RequestParam("num2") BigDecimal num2
   ){
      return ResponseEntity.ok(calculateService.calculateValue(num1, num2));
   }

}
