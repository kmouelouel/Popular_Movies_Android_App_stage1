package com.example.android.popularmoviesstage1.models;

import java.io.Serializable;

public class Movie implements Serializable {
    private int id;
    private String title; // original_title
    private String poster; // poster_path
    private String backdropImage; // backdrop_path
    private String overview;
    private int rating; // vote_average
    private String date; // release_date

    public Movie() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setBackdrop(String backdrop) {
        this.backdropImage = backdrop;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getBackdrop() {
        return backdropImage;
    }

    public String getOverview() {
        return overview;
    }

    public int getRating() {
        return rating;
    }

    public String getDate() {
        return date;
    }

    public Movie(int id, String title, String poster, String backdropImage, String overview, int rating, String date) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.backdropImage = backdropImage;
        this.overview = overview;
        this.rating = rating;
        this.date = date;

    }

}
