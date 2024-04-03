module org.example.moviesappclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    exports org.example.moviesappclient;
    exports org.example.moviesappclient.model;
    exports org.example.moviesappclient.controller;
    opens org.example.moviesappclient to com.google.gson, javafx.fxml;
    opens org.example.moviesappclient.controller to com.google.gson, javafx.fxml;
    opens org.example.moviesappclient.model to com.google.gson, javafx.fxml;
}