package com.moviepedia.data;

/**
 * POJO class which represents movie model
 */
public class Movie {

    public String movieImage;
    public String movieTitle;
    public int id;
    public String dateReleased;
    public double popularity;

    public Movie(int id, String movieImage, String movieTitle, String dateReleased,
                 double popularity) {
        this.id = id;
        this.movieImage = movieImage;
        this.movieTitle = movieTitle;
        this.dateReleased = dateReleased;
        this.popularity = popularity;
    }
}
