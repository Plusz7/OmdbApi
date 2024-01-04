package com.omdbApi.omdb.repository;

import com.omdbApi.omdb.model.MovieDb;
import com.omdbApi.omdb.util.TestObjects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
public class MovieRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void saveMovie() {
        MovieDb savedMovie = movieRepository.save(TestObjects.movieDto.toMovieDb());

        assertNotNull(savedMovie);
    }

    @Test
    public void whenFindByFavorite_thenReturnFavoriteMovies() {
        // given
        MovieDb movie1 = TestObjects.movieDto.toMovieDb();
        movie1.setFavorite(true);
        MovieDb movie2 = new MovieDb("t2", "desc", "genre", "director", "poster", false);
        entityManager.persist(movie1);
        entityManager.persist(movie2);
        entityManager.flush();

        // when
        List<MovieDb> foundMovies = movieRepository.findByFavorite(true);

        // then
        assertThat(foundMovies).isNotEmpty();
        assertThat(foundMovies.size()).isEqualTo(1);
        assertThat(foundMovies.get(0).isFavourite()).isEqualTo(true);
    }
}
