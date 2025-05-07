package com.tekton.challenge.webclient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PercentageWebClient {

    private final WebClient percentageWebClientBase;

    public BigDecimal getPercentage(){
        return percentageWebClientBase
                .get()
                .uri("/api/mock/percentage")
                .retrieve()
                .bodyToMono(BigDecimal.class)
                .block();
    }
}
