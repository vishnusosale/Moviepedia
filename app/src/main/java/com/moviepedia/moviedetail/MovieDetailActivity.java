package com.moviepedia.moviedetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.moviepedia.R;


/**
 * Adds {@link MovieDetailFragment} to the FrameLayout
 */
public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        if (savedInstanceState == null) {

            Bundle bundle = new Bundle();
            bundle.putInt("id", getIntent().getIntExtra("id", 0));
            bundle.putString("title", getIntent().getStringExtra("title"));

            MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
            movieDetailFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().add(R.id.movie_detail_frame_layout,
                    movieDetailFragment).commit();
        }
    }
}
