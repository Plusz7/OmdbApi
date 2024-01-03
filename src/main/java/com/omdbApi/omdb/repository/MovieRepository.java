package com.omdbApi.omdb.repository;

import com.omdbApi.omdb.model.MovieDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieDb, Long> {
}
