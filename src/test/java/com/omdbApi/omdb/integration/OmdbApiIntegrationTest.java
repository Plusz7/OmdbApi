package com.omdbApi.omdb.integration;

import com.omdbApi.omdb.config.TestConfig;
import com.omdbApi.omdb.model.MovieDb;
import com.omdbApi.omdb.repository.MovieRepository;
import com.omdbApi.omdb.repository.OmdbRepository;
import com.omdbApi.omdb.service.MovieService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OmdbApiIntegrationTest {

    @Autowired
    private MockWebServer mockWebServer;

    @Autowired
    @Qualifier("testing")
    private WebClient webClient;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private OmdbRepository omdbRepository;

    private static final String SAMPLE_RESPONSE = "{\n" +
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

    @Test
    public void findAndSaveMovieTest() throws Exception {

        mockWebServer.enqueue(new MockResponse()
                .setBody(SAMPLE_RESPONSE)
                .addHeader("Content-Type", "application/json")
                .setResponseCode(200)
        );

        mockMvc.perform(post("/v1/movies")
                        .param("title", "Inception")
                        .param("apiKey", "apiKey")
                        .param("favourite", "true"))
                .andExpect(status().isOk());

        MovieDb savedMovie = movieRepository.findByTitle("Inception");
        assertNotNull(savedMovie);
        assertTrue(savedMovie.isFavourite());
    }
}
