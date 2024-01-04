package com.omdbApi.omdb.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.omdbApi.omdb.model.MovieDb;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDto {
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Plot")
    private String description;
    @JsonProperty("Genre")
    private String genre;
    @JsonProperty("Director")
    private String director;
    @JsonProperty("Poster")
    private String poster;

    // Default constructor
    public MovieDto() {
    }

    public MovieDto(String title, String description, String genre, String director, String poster) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.director = director;
        this.poster = poster;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public MovieDb toMovieDb() {
        return new MovieDb(
                getTitle(),
                getDescription(),
                getGenre(),
                getDirector(),
                getPoster(),
                false
        );
    }
}

