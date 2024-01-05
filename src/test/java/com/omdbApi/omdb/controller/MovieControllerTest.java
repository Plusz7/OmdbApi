package com.omdbApi.omdb.controller;


import com.omdbApi.omdb.model.MovieDb;
import com.omdbApi.omdb.service.MovieService;
import com.omdbApi.omdb.util.TestObjects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {

    @Mock
    private MovieService movieService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MovieController movieController = new MovieController(movieService);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    public void whenGetMovie_thenReturnMovie() throws Exception {
        String title = "title";
        String apiKey = "someApiKey";
        MovieDb movie = TestObjects.movieDto.toMovieDb();

        when(movieService.getMovie(title, apiKey)).thenReturn(movie);

        mockMvc.perform(get("/v1/movies")
                        .param("title", title)
                        .param("apiKey", apiKey))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(movieService, times(1)).getMovie(title, apiKey);
    }

    @Test
    public void whenSaveMovie_thenReturnSavedMovie() throws Exception {
        String title = "title";
        String apiKey = "someApiKey";
        MovieDb movie = TestObjects.movieDto.toMovieDb();

        when(movieService.saveMovie(title, "true", apiKey)).thenReturn(movie);

        mockMvc.perform(post("/v1/movies")
                        .param("title", title)
                        .param("apiKey", apiKey)
                        .param("favourite", "true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(movieService, times(1)).saveMovie(title, "true", apiKey);
    }

    @Test
    public void whenGetFavorites_thenReturnListOfFavorites() throws Exception {
        List<MovieDb> favorites = new ArrayList<>();
        favorites.add(new MovieDb());

        when(movieService.getFavorites()).thenReturn(favorites);

        mockMvc.perform(get("/v1/movies/favorites"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(movieService, times(1)).getFavorites();
    }

}
