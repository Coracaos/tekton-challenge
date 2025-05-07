package com.tekton.challenge.webclient.config;

import io.netty.channel.ChannelOption;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class PercentageWebClientConfig {

    @Value("${api.percentage.base-url}")
    private String baseUrl;

    @Value("${api.percentage.connection-timeout}")
    private int connectionTimeout;

    @Value("${api.percentage.response-timeout}")
    private int responseTimeout;

    @Bean
    public WebClient percentageWebClientBase() {

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
                .responseTimeout(Duration.ofMillis(responseTimeout));

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(baseUrl)
                .build();
    }

}
