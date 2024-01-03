package com.omdbApi.omdb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movies")
public class MovieDb {
    @Id
    private long id;
    private String title;
    private String description;
    private String genre;
    private String director;
    private String poster;
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

    public boolean isFavorite() {
        return favorite;
    }
}
