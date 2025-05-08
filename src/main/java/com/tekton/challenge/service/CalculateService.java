package com.tekton.challenge.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.tekton.challenge.config.CacheConfig;
import com.tekton.challenge.exception.ExternalServiceException;
import com.tekton.challenge.model.response.CalculatedValueResp;
import com.tekton.challenge.webclient.PercentageWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CalculateService {

    private final PercentageWebClient percentageWebClient;
    private final Cache<String, BigDecimal> percentageCache;
    private final Cache<String, BigDecimal> percentageBackupCache;

    public CalculatedValueResp calculateValue(BigDecimal num1, BigDecimal num2){
        var percentage = getPercentage();
        var result = num1.add(num2).multiply(percentage.add(BigDecimal.valueOf(1)));
        return new CalculatedValueResp(result);
    }

    private BigDecimal getPercentage(){
        var percentageKey = "percentage";

        BigDecimal percentage = percentageCache.getIfPresent(percentageKey);
        if(percentage != null){
            return percentage;
        }

        try{
            percentage = percentageWebClient.getPercentage();
        }catch (Exception ex){
            percentage = percentageBackupCache.getIfPresent(percentageKey);
            if(percentage != null){
                percentageCache.put(percentageKey, percentage);
                return percentage;
            }
            throw new ExternalServiceException("error to get percentage from external api");
        }

        percentageCache.put(percentageKey, percentage);
        return percentage;
    }

}
