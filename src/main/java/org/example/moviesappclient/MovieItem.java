package org.example.moviesappclient;

import javafx.beans.property.SimpleStringProperty;
import org.example.moviesappclient.model.Movie;

public class MovieItem {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;

    public MovieItem(String id, String name) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    public static MovieItem fromMovie(Movie movie) {
        return new MovieItem(movie.getId(), movie.getName());
    }

    // Getters for properties
    public String getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }
}
