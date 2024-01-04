package com.omdbApi.omdb.service;

import com.omdbApi.omdb.repository.MovieRepository;
import com.omdbApi.omdb.repository.OmdbRepository;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private OmdbRepository omdbRepository;
    private MovieService movieService;

}
