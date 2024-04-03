package org.example.moviesappclient.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.example.moviesappclient.model.Movie;
import org.example.moviesappclient.model.MovieClient;
import org.example.moviesappclient.MovieItem;

import java.io.IOException;
import java.util.List;

public class MainController {
    private final MovieClient movieClient;
    public Label searchResultLabel, deleteStatusLabel;

    @FXML
    private TableView<MovieItem> searchResultsTable;

    @FXML
    private TableColumn<MovieItem, String> idColumn;

    @FXML
    private TableColumn<MovieItem, String> nameColumn;

    @FXML
    private TextField idField, nameField, directorField, descriptionField, yearField, stockField, priceField, quantityField;

    @FXML
    private Pane functionalityPane;

    public MainController() {
        this.movieClient = new MovieClient();
    }

    public MainController(MovieClient movieClient) {
        this.movieClient = movieClient;
    }

    @FXML
    public void addMovie() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String director = directorField.getText().trim();
        String description = descriptionField.getText().trim();
        String yearText = yearField.getText().trim();
        String stockText = stockField.getText().trim();
        String priceText = priceField.getText().trim();

        // Check if any of the fields are empty
        if (id.isEmpty() || name.isEmpty() || director.isEmpty() || description.isEmpty() || yearText.isEmpty() || stockText.isEmpty() || priceText.isEmpty()) {
            // Display error popup
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("All fields are required.");
            alert.showAndWait();
            return;
        }

        try {
            int year = Integer.parseInt(yearText);
            int stock = Integer.parseInt(stockText);
            double price = Double.parseDouble(priceText);

            // Create the movie object
            Movie movie = new Movie(id, name, director, description, year, stock, price);

            // Add the movie
            if(movieClient.addMovie(movie)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Movie added successfully!");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Something went wrong");
                alert.showAndWait();
            }

        } catch (NumberFormatException e) {
            // Display error popup if year, stock, or price is not a valid number
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid numbers for Year, Stock, and Price.");
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void buyMovie() throws IOException {
        String id = idField.getText().trim();
        boolean success = movieClient.buyMovie(id);
        if (success) {
            // Display success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Movie purchased successfully.");
            alert.showAndWait();
        } else {
            // Display error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to purchase movie.");
            alert.showAndWait();
        }

    }

    // Method to deleting a movie by ID
    @FXML
    public void deleteMovie() throws IOException {
        String movieId = idField.getText();
        try {
            movieClient.deleteMovie(movieId);
            deleteStatusLabel.setText("Deletion successful");
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log any exceptions
            deleteStatusLabel.setText("Failed to delete movie");
        }
    }

    // Method to search for a movie by ID
    @FXML
    public void getMovieById() throws IOException {
        // Optionally, handle response or update UI
        Movie movie = movieClient.getMovieById(idField.getText());
        if (movie != null) {
            // Display the movie details in the application (e.g., populate fields)
            nameField.setText(movie.getName());
            directorField.setText(movie.getDirector());
            descriptionField.setText(movie.getDescription());
            yearField.setText(Integer.toString(movie.getYear()));
            stockField.setText(Integer.toString(movie.getStock()));
            priceField.setText(Double.toString(movie.getPrice()));

        } else {
            // Display an error message if the movie was not found
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Movie not found.");
            alert.showAndWait();
        }
    }

    // Method to search for movies by name
    @FXML
    public void searchMoviesByName() throws IOException {
        String name = nameField.getText();
        List<Movie> searchResults = movieClient.searchMoviesByName(name);
        System.out.println(searchResults);
        if(searchResults != null) {
            populateSearchResults(searchResults);
        } else {
            // Display an error message if the movie was not found
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Results not found.");
            alert.showAndWait();
        }
    }

    private void populateSearchResults(List<Movie> results) {
        ObservableList<MovieItem> observableList = FXCollections.observableArrayList();
        results.forEach(movie -> observableList.add(MovieItem.fromMovie(movie)));
        searchResultsTable.setItems(observableList);
    }


    @FXML
    public void openAddMovie() {
        loadScreenIntoPane("/org/example/moviesappclient/add_movie.fxml");
    }

    @FXML
    public void openDeleteMovie() {
        loadScreenIntoPane("/org/example/moviesappclient/delete_movie.fxml");
    }

    @FXML
    public void openBuyMovie() {
        loadScreenIntoPane("/org/example/moviesappclient/buy_movie.fxml");
    }

    @FXML
    public void openSearchByID() {
        loadScreenIntoPane("/org/example/moviesappclient/search_by_id.fxml");
    }

    @FXML
    public void openSearchByName() {
        loadScreenIntoPane("/org/example/moviesappclient/search_by_name.fxml");
    }

    private void loadScreenIntoPane(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Pane root = fxmlLoader.load();
            functionalityPane.getChildren().setAll(root);
        } catch (IOException e) {
//            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to load screen: " + fxmlFile);
            alert.showAndWait();
        }
    }
}