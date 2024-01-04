package com.omdbApi.omdb.repository;


import com.omdbApi.omdb.config.TestConfig;
import com.omdbApi.omdb.model.dto.MovieDto;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")
public class OmdbRepositoryTest {

    @Autowired
    private MockWebServer mockWebServer;

    @Autowired
    @Qualifier("testing")
    private WebClient webClient;

    private OmdbRepository omdbRepository;

    @BeforeEach
    void setUp() {
        omdbRepository = new OmdbRepository(webClient);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void whenGetMovieDto_thenReturnsMovie() {
        String sampleResponse = "{\n" +
                "    \"Title\": \"Inception\",\n" +
                "    \"Year\": \"2010\",\n" +
                "    \"Rated\": \"PG-13\",\n" +
                "    \"Released\": \"16 Jul 2010\",\n" +
                "    \"Runtime\": \"148 min\",\n" +
                "    \"Genre\": \"Action, Adventure, Sci-Fi\",\n" +
                "    \"Director\": \"Christopher Nolan\",\n" +
                "    \"Writer\": \"Christopher Nolan\",\n" +
                "    \"Actors\": \"Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page\",\n" +
                "    \"Plot\": \"A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.\",\n" +
                "    \"Language\": \"English, Japanese, French\",\n" +
                "    \"Country\": \"United Kingdom, United States\",\n" +
                "    \"Awards\": \"Won 4 Oscars. 157 wins & 220 nominations total\",\n" +
                "    \"Poster\": \"https://example.com/inception_poster.jpg\"\n" +
                "}";

        mockWebServer.enqueue(new MockResponse()
                .setBody(sampleResponse)
                .addHeader("Content-Type", "application/json")
                .setResponseCode(200));

        MovieDto result = omdbRepository.getMovieDto("Inception", "apiKey");

        assertNotNull(result);
        assertEquals("Inception", result.getTitle());
    }
}