package com.tekton.challenge.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.tekton.challenge.config.CacheConfig;
import com.tekton.challenge.exception.ExternalServiceException;
import com.tekton.challenge.model.response.CalculatedValueResp;
import com.tekton.challenge.webclient.PercentageWebClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CalculateService.class, CacheConfig.class})
class CalculateServiceSpringTest {

    @MockBean
    private PercentageWebClient percentageWebClient;

    @MockBean
    @Qualifier("percentageCache")
    private Cache<String, BigDecimal> percentageCache;

    @MockBean
    @Qualifier("percentageBackupCache")
    private Cache<String, BigDecimal> percentageBackupCache;

    @Autowired
    private CalculateService calculateService;

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
    }

    @Test
    void shouldThrowException_whenWebClientAndBackupFail() {
        when(percentageWebClient.getPercentage()).thenThrow(new RuntimeException("service down"));
        when(percentageBackupCache.getIfPresent(PERCENTAGE_KEY)).thenReturn(null);

        assertThrows(ExternalServiceException.class, () ->
                calculateService.calculateValue(BigDecimal.ONE, BigDecimal.ONE));
    }
}
