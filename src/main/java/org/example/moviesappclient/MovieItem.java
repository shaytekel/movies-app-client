package org.example.moviesappclient;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import org.example.moviesappclient.model.Movie;

public class MovieItem {
    private final SimpleStringProperty id, name, director, description;

    public MovieItem(String id, String name, String director, String description) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.director = new SimpleStringProperty(director);
        this.description = new SimpleStringProperty(description);
    }

    public static MovieItem fromMovie(Movie movie) {
        return new MovieItem(movie.getId(), movie.getName(), movie.getDirector(), movie.getDescription());
    }

    // Getters for properties
    public String getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getDirector() { return director.get(); }

    public String getDescription() { return description.get(); }

    // Property getters
    public SimpleStringProperty idProperty() {
        return id;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty directorProperty() {
        return director;
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }
}
