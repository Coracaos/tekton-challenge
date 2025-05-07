package com.tekton.challenge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/mock")
public class MockController {

   @GetMapping(value = "/percentage")
   public ResponseEntity<?> getPercentage(){
       return ResponseEntity.ok(0.12);
   }
}
