package com.omdbApi.omdb.controller;

import com.omdbApi.omdb.exception.OmdbMovieNotFoundException;
import com.omdbApi.omdb.model.MovieDb;
import com.omdbApi.omdb.model.dto.MovieDto;
import com.omdbApi.omdb.service.MovieService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/movies")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<MovieDb> getMovie(
            @RequestParam String title,
            @RequestParam String apiKey
    ) throws OmdbMovieNotFoundException {
        MovieDb movie = service.getMovie(title, apiKey);
        return ResponseEntity.ok().body(movie);
    }
}
