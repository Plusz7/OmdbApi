package com.omdbApi.omdb.repository;

import com.omdbApi.omdb.model.dto.MovieDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

@Repository
public class OmdbRepository {

    private static final Logger LOG = LoggerFactory.getLogger(OmdbRepository.class);
    private final WebClient webClient;

    public OmdbRepository(WebClient webClient) {
        this.webClient = webClient;
    }

    public MovieDto getMovieDto(String title, String apiKey) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apikey", apiKey)
                        .queryParam("t", title)
                        .queryParam("plot", "short")
                        .build()
                )
                .retrieve()
                .bodyToMono(MovieDto.class)
                .doOnError(error -> {
                    LOG.error("Error during deserialization: " + error.getMessage());
                })
                .block();
    }
}
