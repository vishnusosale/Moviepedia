package com.moviepedia.movielist;


import com.moviepedia.data.Movie;

import java.util.List;

/**
 * Contract class between the view and the presenter
 */
class MovieListContract {

    interface MovieListPresenter {

        /**
         * Get the list of movies from the remote servers
         *
         * @param page Indicate pagination
         */
        void getMovieList(int page);
    }

    interface MovieListView {

        /**
         * Make progress invisible
         */
        void dismissProgress();

        /**
         * Show a progress
         */
        void showProgress();

        /**
         * Show error messages to the user
         *
         * @param errorMessage Human readable error message
         */
        void showError(CharSequence errorMessage);

        /**
         * Show a list of movies
         *
         * @param movieList A list (preferably ArrayList) of {@link Movie}
         */
        void showMovieList(List<Movie> movieList);
    }
}
