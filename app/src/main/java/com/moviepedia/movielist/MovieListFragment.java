package com.moviepedia.movielist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.moviepedia.R;
import com.moviepedia.data.Movie;
import com.moviepedia.moviedetail.MovieDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * {@link Fragment} view that shows a list of movies
 */
public class MovieListFragment extends Fragment implements MovieListContract.MovieListView {

    private List<Movie> movies;
    private ListView movieListView;
    private ProgressBar progressBar;
    private View movieListLayout;
    private MovieListPresenter movieListPresenter;
    private MovieAdapter moviesArrayAdapter;

    public MovieListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Sets the fragment to receive calls options menu from the Activity.
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);

        movieListView = (ListView) rootView.findViewById(R.id.movie_list_view);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        movieListLayout = rootView.findViewById(R.id.movie_list_layout);

        movieListPresenter = new MovieListPresenter(this, this.getContext());
        movieListPresenter.getMovieList(1);

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_most_popular:
                item.setChecked(true);
                // Sort the ArrayList of movies according to increasing popularity
                Collections.sort(movies, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie o1, Movie o2) {
                        return Double.compare(o2.popularity, o1.popularity);
                    }
                });
                // Notify the adapter to refresh because the data in the ArrayList has changed
                moviesArrayAdapter.notifyDataSetChanged();
                // Move the ListView item to starting position
                movieListView.setSelection(0);
                break;
            case R.id.menu_most_recent:
                item.setChecked(true);
                // Sort the ArrayList of movies according to decreasing date (Latest first)
                Collections.sort(movies, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie o1, Movie o2) {
                        return o2.dateReleased.compareTo(o1.dateReleased);
                    }
                });
                // Notify the adapter to refresh because the data in the ArrayList has changed
                moviesArrayAdapter.notifyDataSetChanged();
                // Move the ListView item to starting position
                movieListView.setSelection(0);
                break;
        }

        return true;
    }

    /**
     * Show error messages to the user
     *
     * @param errorMessage Human readable error message
     */
    @Override
    public void showError(CharSequence errorMessage) {
        // Show a SnackBar to notify the user about errors
        if (errorMessage != null) {
            final Snackbar snackbar = Snackbar.make(movieListLayout, errorMessage,
                    Snackbar.LENGTH_LONG);
            snackbar.setAction(R.string.retry, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Try to get movie list again
                    movieListPresenter.getMovieList(1);
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        }
    }

    /**
     * Make progress invisible
     */
    @Override
    public void dismissProgress() {
        progressBar.setVisibility(View.GONE);
    }

    /**
     * Show a progress
     */
    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Show a list of movies
     *
     * @param movieList A list (preferably ArrayList) of {@link Movie}
     */
    @Override
    public void showMovieList(final List<Movie> movieList) {

        movies = movieList;
        // Init the adapter and set it to the ListView
        moviesArrayAdapter = new MovieAdapter(getContext(), movieList);
        movieListView.setAdapter(moviesArrayAdapter);
        movieListView.setVisibility(View.VISIBLE);

        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra("id", movieList.get(position).id);
                intent.putExtra("title", movieList.get(position).movieTitle);
                startActivity(intent);
            }
        });
    }

    /**
     * The Custom {@link ArrayAdapter} to show a list of movies
     */
    private class MovieAdapter extends ArrayAdapter<Movie> {

        MovieAdapter(@NonNull Context context, @NonNull List<Movie> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            ViewHolder viewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_list_item,
                        parent, false);
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id
                        .movie_item_image_view);
                viewHolder.textView = (TextView) convertView.findViewById(R.id
                        .movie_item_text_view);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Movie movie = getItem(position);
            if (movie != null) {
                viewHolder.textView.setText(movie.movieTitle);

                Picasso.with(getContext()).load("https://image.tmdb.org/t/p/w500" + movie
                        .movieImage).into(viewHolder.imageView);
            }

            return convertView;
        }

        private class ViewHolder {
            ImageView imageView;
            TextView textView;
        }
    }
}
