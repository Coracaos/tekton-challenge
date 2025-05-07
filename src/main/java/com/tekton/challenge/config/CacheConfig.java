package com.tekton.challenge.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Value("${cache.percentage.expiration-time}")
    private Duration expirationTime;

    @Bean
    public Cache<String, BigDecimal> percentageCache(){
        return Caffeine.newBuilder()
                .expireAfterWrite(expirationTime.toMillis(), TimeUnit.MILLISECONDS)
                .removalListener((String key, BigDecimal value, RemovalCause cause) -> {
                    percentageBackupCache().put(key, value);
                })
                .maximumSize(1)
                .build();
    }

    @Bean
    public Cache<String, BigDecimal> percentageBackupCache(){
        return Caffeine.newBuilder()
                .maximumSize(1)
                .build();
    }

}
