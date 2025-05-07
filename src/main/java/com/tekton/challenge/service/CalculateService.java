package com.tekton.challenge.service;

import com.tekton.challenge.model.response.CalculatedValueResp;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculateService {

    public CalculatedValueResp calculateValue(BigDecimal num1, BigDecimal num2){
        return new CalculatedValueResp(num1.add(num2));
    }
}
