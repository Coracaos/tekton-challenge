package com.tekton.challenge.service;

import com.tekton.challenge.model.response.CalculatedValueResp;
import com.tekton.challenge.webclient.PercentageWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CalculateService {

    private final PercentageWebClient percentageWebClient;

    public CalculatedValueResp calculateValue(BigDecimal num1, BigDecimal num2){
        var percentage = percentageWebClient.getPercentage();
        var result = num1.add(num2).multiply(percentage.add(BigDecimal.valueOf(1)));
        return new CalculatedValueResp(result);
    }

}
