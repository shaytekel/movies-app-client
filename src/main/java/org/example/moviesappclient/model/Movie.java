package org.example.moviesappclient.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serial;
import java.io.Serializable;

public class Movie implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes

    private String id;
    private String name;
    private String director;
    private String description;
    @SerializedName("year")
    private int year;
    @SerializedName("stock")
    private int stock;
    private double price;

    public Movie(String id, String name, String director, String description, int year, int stock, double price) {
        this.id = id;
        this.name = name;
        this.director = director;
        this.description = description;
        this.year = year;
        this.stock = stock;
        this.price = price;
    }

    public Movie() {}

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int decreaseStock(int decStock) {
        this.stock -= decStock;
        return this.stock;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", director='" + director + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", stock=" + stock +
                ", price=" + price +
                '}';
    }
}
