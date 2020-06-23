package com.juborajsarker.mcctechnicaltest.model;

public class Thumbnail {

    String thumbnailUrl;
    String movieId;
    String movieTitle;

    public Thumbnail(String thumbnailUrl, String movieId, String movieTitle) {
        this.thumbnailUrl = thumbnailUrl;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
    }

    public Thumbnail() {
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
}
