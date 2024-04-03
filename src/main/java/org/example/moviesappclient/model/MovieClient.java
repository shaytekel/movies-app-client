package org.example.moviesappclient.model;

import com.google.gson.*;
import javafx.scene.control.Alert;
import org.example.moviesappclient.model.Movie;
import org.example.moviesappclient.model.Request;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieClient {

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(Integer.class, (JsonDeserializer<Integer>) (json, typeOfT, context) -> {
        if(json.isJsonPrimitive()) {
            JsonPrimitive primitive = json.getAsJsonPrimitive();
            if(primitive.isNumber()) {
                return primitive.getAsNumber().intValue();
            }
        }
        throw new JsonParseException("Failed to deserialize integer value: " + json);
    }).create();

    public boolean addMovie(Movie movie) throws IOException {
        Request request = new Request("addMovie", createRequestMap("movie", movie));
        Response response = sendRequestToServer(request);
        // Process the response if needed
        return response.getMessage().equals("Movie added successfully");
    }

    public Movie getMovieById(String idValue) throws IOException {
        Request request = new Request("getMovieById", createRequestMap("id", idValue));
        Response response = sendRequestToServer(request);

        if (response != null && response.getData() != null) {
            JsonObject movieJson = gson.fromJson(gson.toJson(response.getData()), JsonObject.class);

            // Deserialize the movie object using Gson
            return gson.fromJson(movieJson, Movie.class);
        } else {
            // Handle case where response or movie data is null
            return null;
        }
    }

    public boolean buyMovie(String id) throws IOException {
        Request request = new Request("buyMovie", createRequestMap("id", id));
        Object response = sendRequestToServer(request);
        if (response instanceof Boolean) {
            return (boolean) response;
        } else {
            // Handle unexpected response types
            return false;
        }
    }

    public void deleteMovie(String id) throws IOException {
        Request request = new Request("deleteMovie", createRequestMap("id", id));
        sendRequestToServer(request);
        // Process the response if needed
    }

    public List<Movie> searchMoviesByName(String name) throws IOException {
        Request request = new Request("searchMoviesByName", createRequestMap("name", name));
        Response response = sendRequestToServer(request);
        if (response instanceof List) {
            return (List<Movie>) response;
        } else {
            // Handle unexpected response types
            return null;
        }
    }

    private Map<String, Object> createRequestMap(String name, Object value) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put(name, value);
        return reqMap;
    }

    private Response sendRequestToServer(Request request) throws IOException {
        try {
            Socket socket = new Socket("localhost", 8000);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send the request object as JSON string to the server
            String jsonRequest = gson.toJson(request);
            System.out.println("Sending JSON request to server: " + jsonRequest);
            writer.println(jsonRequest); // Append a newline character
            writer.flush(); // Flush the output stream to ensure data is sent

            // Read the JSON response from the server
            String jsonResponse = reader.readLine();

            // Deserialize the JSON response into a Response object
            Response response = gson.fromJson(jsonResponse, Response.class);

            // Close the streams and socket
            reader.close();
            writer.close();
            socket.close();

            return response;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
