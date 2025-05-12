package com.tekton.challenge.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.tekton.challenge.exception.ExternalServiceException;
import com.tekton.challenge.model.response.CalculatedValueResp;
import com.tekton.challenge.webclient.PercentageWebClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculateServiceTest {

    @Mock
    private PercentageWebClient percentageWebClient;

    @Mock
    private Cache<String, BigDecimal> percentageCache;

    @Mock
    private Cache<String, BigDecimal> percentageBackupCache;

    private CalculateService calculateService;

    @BeforeEach
    void setup(){
        calculateService = new CalculateService(percentageWebClient, percentageCache, percentageBackupCache);
    }

    private static final String PERCENTAGE_KEY = "percentage";

    @Test
    void shouldReturnCalculatedValue_whenPercentageFromCache() {
        BigDecimal num1 = new BigDecimal("100.00");
        BigDecimal num2 = new BigDecimal("200.00");
        BigDecimal percentage = new BigDecimal("0.10");

        when(percentageCache.getIfPresent(PERCENTAGE_KEY)).thenReturn(percentage);

        CalculatedValueResp result = calculateService.calculateValue(num1, num2);

        assertEquals(new BigDecimal("330.00"), result.value());
    }

    @Test
    void shouldReturnCalculatedValue_whenPercentageFromWebClient() {
        BigDecimal num1 = new BigDecimal("50");
        BigDecimal num2 = new BigDecimal("50");
        BigDecimal percentage = new BigDecimal("0.50");

        when(percentageCache.getIfPresent(PERCENTAGE_KEY)).thenReturn(null);
        when(percentageWebClient.getPercentage()).thenReturn(percentage);

        CalculatedValueResp result = calculateService.calculateValue(num1, num2);

        assertEquals(new BigDecimal("150.00"), result.value());
        verify(percentageCache).put("percentage", percentage);
    }

    @Test
    void shouldReturnValue_whenWebClientFailsAndBackupSucceeds() {
        BigDecimal num1 = new BigDecimal("10");
        BigDecimal num2 = new BigDecimal("20");
        BigDecimal backupPercentage = new BigDecimal("0.10");

        when(percentageCache.getIfPresent(PERCENTAGE_KEY)).thenReturn(null);
        when(percentageWebClient.getPercentage()).thenThrow(new RuntimeException("Service down"));
        when(percentageBackupCache.getIfPresent(PERCENTAGE_KEY)).thenReturn(backupPercentage);

        CalculatedValueResp result = calculateService.calculateValue(num1, num2);

        assertEquals(new BigDecimal("33.00"), result.value());
        verify(percentageCache).put("percentage", backupPercentage);
    }

    @Test
    void shouldThrowException_whenWebClientAndBackupFail() {
        when(percentageWebClient.getPercentage()).thenThrow(new RuntimeException("service down"));
        when(percentageBackupCache.getIfPresent(PERCENTAGE_KEY)).thenReturn(null);

        assertThrows(ExternalServiceException.class, () ->
                calculateService.calculateValue(BigDecimal.ONE, BigDecimal.ONE));
    }
}
