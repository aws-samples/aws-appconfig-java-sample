package com.amazonaws.samples.appconfig.movies;

public class Movie {

    private long id;
    private final String movieName;



    public Movie(Long id, String movieName) {
        this.id = id;
        this.movieName = movieName;
    }

    public long getId() {
        return this.id;
    }

    public long setId(int movieId){
        return this.id = movieId;
    }
    public String getMovieName() {
        return this.movieName;
    }
}