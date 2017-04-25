package com.moviepedia.moviedetail;


import com.moviepedia.data.MovieDetail;

/**
 * Contract class between the view and presenter
 */
class MovieDetailContract {

    interface MovieDetailView {

        /**
         * Show progress
         */
        void showProgress();

        /**
         * Make progress gone from the view
         */
        void dismissProgress();

        /**
         * Show error messages to the user
         *
         * @param errorMessage Human readable error message
         */
        void showError(CharSequence errorMessage);

        /**
         * Show movie details
         *
         * @param movieDetail {@link MovieDetail} object to show relevant movie details
         */
        void showMovieDetail(MovieDetail movieDetail);
    }

    interface MovieDetailPresenter {

        /**
         * Get movie details from remote servers
         *
         * @param id id of the movie to be queried
         */
        void getMovieDetail(int id);
    }
}
