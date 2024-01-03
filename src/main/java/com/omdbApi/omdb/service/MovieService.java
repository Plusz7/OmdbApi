package com.omdbApi.omdb.service;

import com.omdbApi.omdb.exception.OmdbMovieNotFoundException;
import com.omdbApi.omdb.model.MovieDb;
import com.omdbApi.omdb.model.dto.MovieDto;
import com.omdbApi.omdb.repository.MovieRepository;
import com.omdbApi.omdb.repository.OmdbRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MovieService {

    private static final Logger LOG = LoggerFactory.getLogger(MovieService.class);
    private HashMap<String, MovieDb> cachedMovies = new HashMap<>();
    private final OmdbRepository omdbRepository;
    private final MovieRepository movieRepository;

    public MovieService(OmdbRepository omdbRepository, MovieRepository movieRepository) {
        this.omdbRepository = omdbRepository;
        this.movieRepository = movieRepository;
    }

    public MovieDb getMovie(String title, String apiKey) throws OmdbMovieNotFoundException {

        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title must not be null or empty");
        }

        if(cachedMovies.containsKey(title)) {
            return cachedMovies.get(title);
        }

        if(cachedMovies.size() >= 5) {
            cachedMovies.clear();
        }

        MovieDto movieDto = omdbRepository.getMovieDto(title, apiKey);

        if(validateMovieDto(movieDto)) {
            MovieDb movieDb = movieDto.toMovieDb();
            cachedMovies.put(movieDto.getTitle(), movieDto.toMovieDb());
            return movieDb;
        } else {
            throw new OmdbMovieNotFoundException("Movie not found. Title: " + title);
        }
    }

    public MovieDb saveMovie(String title, String favourite, String apiKey) {

        MovieDb movieDb = cachedMovies.get(title);

        return movieRepository.save(movieDb);
    }

    private boolean validateMovieDto(MovieDto movieDto) {
        return movieDto != null &&
                movieDto.getTitle() != null &&
                movieDto.getDescription() != null &&
                movieDto.getGenre() != null &&
                movieDto.getDirector() != null &&
                movieDto.getPoster() != null;
    }

    private boolean validateMovieDb(MovieDb movieDb) {
        return movieDb != null &&
                movieDb.getTitle() != null &&
                movieDb.getDescription() != null &&
                movieDb.getGenre() != null &&
                movieDb.getDirector() != null &&
                movieDb.getPoster() != null;
    }
}
