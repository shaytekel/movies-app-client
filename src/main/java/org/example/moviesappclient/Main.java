package org.example.moviesappclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main_screen.fxml"));

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 320, 240);
            stage.setMinHeight(500);
            stage.setMinWidth(800);
            stage.setTitle("Movie Store");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}