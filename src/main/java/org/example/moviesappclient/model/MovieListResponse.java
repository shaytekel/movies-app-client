package org.example.moviesappclient.model;

import java.util.List;

public class MovieListResponse extends Response {
    private final List<Movie> movieList;

    public MovieListResponse(String message, List<Movie> movieList) {
        super(message, null); // Set data to null as it's not used in this subclass
        this.movieList = movieList;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }
}
