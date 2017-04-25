package com.moviepedia.moviedetail;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.moviepedia.data.MovieDetail;
import com.moviepedia.util.ApiConstants;
import com.moviepedia.util.VolleyNetwork;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Presenter which handles the business logic behind showing the movie list
 */
class MovieDetailPresenter implements MovieDetailContract.MovieDetailPresenter {

    private final String TAG = getClass().getSimpleName();
    private final MovieDetailContract.MovieDetailView movieDetailView;
    private final Context context;

    MovieDetailPresenter(MovieDetailContract.MovieDetailView movieDetailView, Context context) {
        this.movieDetailView = movieDetailView;
        this.context = context;
    }

    /**
     * Get movie details from remote servers
     *
     * @param id id of the movie to be queried
     */
    @Override
    public void getMovieDetail(int id) {

        movieDetailView.showProgress();

        final String MOVIE_DETAIL_URI = "https://api.themoviedb" +
                ".org/3/movie/" + String.valueOf(id) +
                "?api_key=687c09fb8d67fee2d49eea108d71400a&language=en-US";

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(MOVIE_DETAIL_URI, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // Request success, process data and update UI
                movieDetailView.dismissProgress();

                try {
                    movieDetailView.showMovieDetail(new MovieDetail(
                            response.getInt("id"),
                            response.getString("original_title"),
                            response.getString("overview"),
                            response.getString("poster_path")));

                } catch (JSONException e) {
                    movieDetailView.dismissProgress();
                    Log.d(TAG, e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Request error
                movieDetailView.dismissProgress();
                if (error instanceof NoConnectionError) {
                    movieDetailView.showError("No Network Connectivity");
                } else if (error instanceof AuthFailureError) {
                    movieDetailView.showError("Invalid credentials");
                } else if (error instanceof NetworkError) {
                    movieDetailView.showError("Please check your network settings");
                } else if (error instanceof ServerError) {
                    movieDetailView.showError("Problem with the server");
                } else if (error instanceof TimeoutError) {
                    movieDetailView.showError("Server timed out. Try again");
                } else if (error instanceof ParseError) {
                    movieDetailView.showError("Request could not be parsed");
                }
                Log.d(TAG, error.toString());
            }
        });

        // Set retry policy for the request
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                ApiConstants.getTimeOutInMs(),
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the network request to the queue
        VolleyNetwork.getInstance(context.getApplicationContext())
                .addToRequestQueue(jsonObjectRequest);

    }
}
