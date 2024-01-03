package com.omdbApi.omdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebclientConfig {

    private final static String OMDB_API_LINK = "http://www.omdbapi.com";

    @Bean
    public WebClient.Builder wenClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(OMDB_API_LINK)
                .build();
    }
}
