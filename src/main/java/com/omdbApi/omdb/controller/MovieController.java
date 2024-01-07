package com.omdbApi.omdb.controller;

import com.omdbApi.omdb.exception.MovieRequestArgumentException;
import com.omdbApi.omdb.exception.OmdbMovieNotFoundException;
import com.omdbApi.omdb.model.MovieDb;
import com.omdbApi.omdb.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    ) throws OmdbMovieNotFoundException, MovieRequestArgumentException {
        MovieDb movie = service.getMovie(title, apiKey);
        return ResponseEntity.ok().body(movie);
    }

    @PostMapping
    public ResponseEntity<MovieDb> saveMovie(
            @RequestParam String title,
            @RequestParam String apiKey,
            @RequestParam String favourite
    ) throws OmdbMovieNotFoundException, MovieRequestArgumentException {
        MovieDb movie = service.saveMovie(title, favourite, apiKey);
        return ResponseEntity.ok().body(movie);
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<MovieDb>> getFavorites() {
        return ResponseEntity.ok().body(service.getFavorites());
    }
}
