package com.omdbApi.omdb.service;

import com.omdbApi.omdb.exception.OmdbMovieNotFoundException;
import com.omdbApi.omdb.model.MovieDb;
import com.omdbApi.omdb.model.dto.MovieDto;
import com.omdbApi.omdb.repository.MovieRepository;
import com.omdbApi.omdb.repository.OmdbRepository;
import com.omdbApi.omdb.util.TestObjects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private OmdbRepository omdbRepository;
    private MovieService movieService;

    private static final String TITLE = "title";
    private static final String API_KEY = "apikey";
    @BeforeEach
    public void setup() {
        movieService = new MovieService(omdbRepository, movieRepository);
    }

    @Test
    public void successfullyFindMovie() throws OmdbMovieNotFoundException {

        MovieDto movieDto = TestObjects.movieDto;

        when(omdbRepository.getMovieDto(movieDto.getTitle(), API_KEY)).thenReturn(movieDto);

        MovieDb movie = movieService.getMovie(movieDto.getTitle(), API_KEY);

        assertNotNull(movie);
        verify(omdbRepository, times(1)).getMovieDto(any(), any());
    }

    @Test
    public void failFindMovie() {
        MovieDto movieDto = TestObjects.movieDto;

        when(omdbRepository.getMovieDto(movieDto.getTitle(), API_KEY)).thenReturn(null);

        assertThrows(OmdbMovieNotFoundException.class, () -> {
            movieService.getMovie(movieDto.getTitle(), API_KEY);
        });

        verify(omdbRepository, times(1)).getMovieDto(movieDto.getTitle(), API_KEY);
    }

    @Test
    public void emptyTitleFindMovie() {
        String title = "";

        assertThrows(IllegalArgumentException.class, () -> {
            movieService.getMovie(title, API_KEY);
        });
    }

    @Test
    public void nullTitleFindMovie() {
         assertThrows(IllegalArgumentException.class, () -> {
            movieService.getMovie(null, API_KEY);
        });
    }

    @Test
    public void whenMovieFetchedFirstTime_thenItShouldBeCached() throws OmdbMovieNotFoundException {
        String title = TITLE;
        String apiKey = API_KEY;
        MovieDto movieDto = TestObjects.movieDto;

        when(omdbRepository.getMovieDto(title, apiKey)).thenReturn(movieDto);

        MovieDb firstRetrieval = movieService.getMovie(title, apiKey);
        MovieDb secondRetrieval = movieService.getMovie(title, apiKey);

        assertSame(firstRetrieval, secondRetrieval, "The movie should be fetched from cache on second call");
        verify(omdbRepository, times(1)).getMovieDto(title, apiKey);
    }

    @Test
    public void whenMovieFetchedSecondTime_thenItShouldBeRetrievedFromCache() throws OmdbMovieNotFoundException {
        String title = TITLE;
        String apiKey = API_KEY;
        MovieDto movieDto = TestObjects.movieDto;

        when(omdbRepository.getMovieDto(title, apiKey)).thenReturn(movieDto);

        movieService.getMovie(title, apiKey); // First call - should cache the movie
        movieService.getMovie(title, apiKey); // Second call - should fetch from cache

        verify(omdbRepository, times(1)).getMovieDto(title, apiKey);
    }

    @Test
    public void whenCacheLimitReached_thenCacheShouldBeCleared() throws OmdbMovieNotFoundException {
        String apiKey = API_KEY;
        for (int i = 1; i <= 6; i++) {
            String title = "Movie " + i;
            MovieDto movieDto = TestObjects.movieDto;// Setup a valid movie DTO
            movieDto.setTitle(title);
            when(omdbRepository.getMovieDto(title, apiKey)).thenReturn(movieDto);

            movieService.getMovie(title, apiKey);
        }

        // Now, the first movie should not be in cache
        String firstMovieTitle = "Movie 1";
        movieService.getMovie(firstMovieTitle, apiKey); // This should be a cache miss

        verify(omdbRepository, times(2)).getMovieDto(firstMovieTitle, apiKey);
    }

    @Test
    public void whenValidTitleAndFavorite_thenMovieShouldBeSaved() throws OmdbMovieNotFoundException {
        String title = TITLE;
        String apiKey = API_KEY;
        String favourite = "true";
        MovieDto movieDto = TestObjects.movieDto;
        MovieDb movieDb = movieDto.toMovieDb();
        movieDb.setFavorite(true);

        when(omdbRepository.getMovieDto(title, apiKey)).thenReturn(movieDto);
        when(movieRepository.save(any(MovieDb.class))).thenReturn(movieDb);

        MovieDb savedMovie = movieService.saveMovie(title, favourite, apiKey);

        assertTrue(savedMovie.isFavourite());
        verify(movieRepository, times(1)).save(any(MovieDb.class));
    }

    @Test
    public void whenInvalidFavorite_thenThrowIllegalArgumentException() {
        String title = TITLE;
        String apiKey = API_KEY;
        String favourite = "invalid";

        when(omdbRepository.getMovieDto(title, apiKey)).thenReturn(TestObjects.movieDto);

        assertThrows(IllegalArgumentException.class, () -> {
            movieService.saveMovie(title, favourite, apiKey);
        });
    }

    @Test
    public void getFavoritesShouldReturnFavoriteMovies() {
        List<MovieDb> mockFavorites = new ArrayList<>();
        mockFavorites.add(TestObjects.movieDto.toMovieDb());
        when(movieRepository.findByFavorite(true)).thenReturn(mockFavorites);

        List<MovieDb> favorites = movieService.getFavorites();

        assertEquals(mockFavorites, favorites, "The returned favorites should match the mock favorites");
        verify(movieRepository, times(1)).findByFavorite(true);
    }
}
