package com.tekton.challenge.config;


import com.google.common.testing.FakeTicker;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CacheConfigTest {

    @Test
    void shouldKeepLastCacheValueInBackup() throws InterruptedException {
        var percentageKey = "percentage";
        var cacheConfig = new CacheConfig();
        cacheConfig.setExpirationTime(Duration.ofMinutes(30));
        var percentageBackupCache = cacheConfig.percentageBackupCache();
        var ticker = new FakeTicker();
        var percentageCache = cacheConfig.percentageCache(percentageBackupCache, ticker::read);
        percentageCache.put(percentageKey, BigDecimal.ONE);
        ticker.advance(Duration.ofMinutes(40));
        Thread.sleep(Duration.ofSeconds(1));
        assertNull(percentageCache.getIfPresent(percentageKey));
        assertEquals(BigDecimal.ONE, percentageBackupCache.getIfPresent(percentageKey));
    }
}
