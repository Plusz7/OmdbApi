package com.omdbApi.omdb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "movies")
public class MovieDb {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "title", unique = true, nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "genre", nullable = false)
    private String genre;
    @Column(name = "director", nullable = false)
    private String director;
    @Column(name = "poster", nullable = false)
    private String poster;
    @Column(name = "favorite", nullable = false)
    private boolean favorite;

    public MovieDb() {
    }

    public MovieDb(String title, String description, String genre, String director, String poster, boolean favorite) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.director = director;
        this.poster = poster;
        this.favorite = favorite;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getPoster() {
        return poster;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isFavourite() {
        return favorite;
    }
}
