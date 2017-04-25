package com.moviepedia.movielist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.moviepedia.R;

/**
 * {@link Fragment} view that shows a list of movies
 */
public class MovieListFragment extends Fragment {

    private ListView movieListView;
    private ProgressBar progressBar;
    private View movieListLayout;

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

        return rootView;
    }
}
