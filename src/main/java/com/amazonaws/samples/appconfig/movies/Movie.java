package com.amazonaws.samples.appconfig.movies;

public class Movie {

    private final long id;
    private final String movieName;


    public Movie(Long id, String movieName) {
        this.id = id;
        this.movieName = movieName;
    }

    public long getId() {
        return this.id;
    }

    public String getMovieName() {
        return this.movieName;
    }
}