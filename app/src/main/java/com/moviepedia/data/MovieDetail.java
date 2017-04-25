package com.moviepedia.data;


/**
 * POJO class which represents the movie detail model
 */
public class MovieDetail {

    public int movieId;
    public String title;
    public String overview;
    public String image;

    public MovieDetail(int id, String title, String overview, String image) {
        this.movieId = id;
        this.title = title;
        this.overview = overview;
        this.image = image;
    }
}
