package com.omdbApi.omdb.util;

import com.omdbApi.omdb.model.dto.MovieDto;

public class TestObjects {

    public static MovieDto movieDto = new MovieDto(
            "title",
            "plot",
            "genre",
            "director",
            "poster"
    );

    public static final String GET_MOVIE_RESPONSE_BODY = "{\n" +
            "    \"id\": 0,\n" +
            "    \"title\": \"The Fifth Element\",\n" +
            "    \"description\": \"In the colorful future, a cab driver unwittingly becomes the central figure in the search for a legendary cosmic weapon to keep Evil and Mr. Zorg at bay.\",\n" +
            "    \"genre\": \"Action, Adventure, Sci-Fi\",\n" +
            "    \"director\": \"Luc Besson\",\n" +
            "    \"poster\": \"https://m.media-amazon.com/images/M/MV5BZWFjYmZmZGQtYzg4YS00ZGE5LTgwYzAtZmQwZjQ2NDliMGVmXkEyXkFqcGdeQXVyNTUyMzE4Mzg@._V1_SX300.jpg\",\n" +
            "    \"favorite\": false\n" +
            "}";
}
