package com.omdbApi.omdb.config;

import com.omdbApi.omdb.repository.OmdbRepository;
import okhttp3.mockwebserver.MockWebServer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.logging.Level;
import java.util.logging.Logger;

@TestConfiguration
public class TestConfig {
    @Bean
    public MockWebServer mockWebServer() {
        Logger logger = Logger.getLogger(MockWebServer.class.getName());
        logger.setLevel(Level.INFO);
        return new MockWebServer();
    }

    @Bean(name = "testing")
    @Profile("test")
    public WebClient webClient(MockWebServer mockWebServer) {
        return WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();
    }

    @Bean
    @Profile("test")
    public OmdbRepository omdbRepository(@Qualifier("testing") WebClient webClient) {
        return new OmdbRepository(webClient);
    }
}
