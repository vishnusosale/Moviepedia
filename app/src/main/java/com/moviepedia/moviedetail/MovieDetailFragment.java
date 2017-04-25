package com.moviepedia.moviedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.moviepedia.R;
import com.moviepedia.data.MovieDetail;
import com.squareup.picasso.Picasso;

/**
 * {@link Fragment} Shows the details of the movie selected
 */
public class MovieDetailFragment extends Fragment implements MovieDetailContract.MovieDetailView {

    private ProgressBar progressBar;
    private ImageView imageView;
    private TextView title;
    private TextView overView;
    private MovieDetailPresenter movieDetailPresenter;
    private View movieDetailView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        imageView = (ImageView) rootView.findViewById(R.id.movie_detail_image_view);
        title = (TextView) rootView.findViewById(R.id.movie_detail_title);
        overView = (TextView) rootView.findViewById(R.id.movie_detail_overview);
        movieDetailView = rootView.findViewById(R.id.movie_detail_view);

        movieDetailPresenter = new MovieDetailPresenter(this, getContext());
        movieDetailPresenter.getMovieDetail(getArguments().getInt("id"));

        return rootView;
    }


    /**
     * Show progress
     */
    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Make progress gone from the view
     */
    @Override
    public void dismissProgress() {
        progressBar.setVisibility(View.GONE);
    }

    /**
     * Show error messages to the user
     *
     * @param errorMessage Human readable error message
     */
    @Override
    public void showError(CharSequence errorMessage) {
        if (errorMessage != null) {
            final Snackbar snackbar = Snackbar.make(movieDetailView, errorMessage,
                    Snackbar.LENGTH_LONG);
            snackbar.setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    movieDetailPresenter.getMovieDetail(getArguments().getInt("id"));
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        }
    }

    /**
     * Show movie details
     *
     * @param movieDetail {@link MovieDetail} object to show relevant movie details
     */
    @Override
    public void showMovieDetail(MovieDetail movieDetail) {

        imageView.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        overView.setVisibility(View.VISIBLE);


        Picasso.with(getContext().getApplicationContext()).load("https://image.tmdb" +
                ".org/t/p/w500" + movieDetail.image).into(imageView);

        title.setText(movieDetail.title);
        overView.setText(movieDetail.overview);
    }
}
