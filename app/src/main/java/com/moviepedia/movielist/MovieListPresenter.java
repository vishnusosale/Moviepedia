package com.moviepedia.movielist;

import android.content.Context;
import android.net.Uri;
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
import com.moviepedia.data.Movie;
import com.moviepedia.util.ApiConstants;
import com.moviepedia.util.VolleyNetwork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Presenter which handles the business logic behind showing the movie list
 */
class MovieListPresenter implements MovieListContract.MovieListPresenter {

    private final String TAG = getClass().getSimpleName();

    private final MovieListContract.MovieListView movieListView;
    private final Context context;
    private final List<Movie> movies;

    MovieListPresenter(MovieListContract.MovieListView movieListView, Context context) {
        this.movieListView = movieListView;
        this.context = context;
        movies = new ArrayList<>();
    }

    /**
     * Get movies list from remote servers using Volley network library
     *
     * @param page Indicate pagination
     */
    @Override
    public void getMovieList(int page) {

        if (page <= 1) {
            movieListView.showProgress();
            page = 1;
        }

        // Build the Uri to be used as the API path to request data.
        final Uri uri = new Uri.Builder()
                .scheme(ApiConstants.getScheme())
                .authority(ApiConstants.getAuthority())
                .appendPath(ApiConstants.getApiVersion())
                .appendPath(ApiConstants.getMoviePath())
                .appendPath(ApiConstants.getPopularPath())
                .appendQueryParameter(ApiConstants.getApiKeyParameter(), ApiConstants.getApiKey())
                .appendQueryParameter(ApiConstants.getLanguageParameter(), Locale.getDefault().toString())
                .appendQueryParameter(ApiConstants.getPageParameter(), String.valueOf(page))
                .build();

        final int finalPage = page;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(uri.toString(),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // Request success, process the response and update the UI

                try {
                    JSONArray jsonArray = response.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        movies.add(new Movie(
                                jsonArray.getJSONObject(i).getInt("id"),
                                jsonArray.getJSONObject(i).getString("poster_path"),
                                jsonArray.getJSONObject(i).getString("original_title"),
                                jsonArray.getJSONObject(i).getString("release_date"),
                                jsonArray.getJSONObject(i).getDouble("popularity")));
                    }

                } catch (JSONException e) {
                    movieListView.dismissProgress();
                    Log.e(TAG, e.toString());
                }

                if (finalPage <= 1) {
                    // if page is less than or equal to 1, this network request has been called
                    // for the first time. Therefore, init the screen with data
                    movieListView.dismissProgress();
                    movieListView.showMovieList(movies);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error with network request. Process and show the error to the user.

                if (finalPage <= 1) {
                    movieListView.dismissProgress();
                }

                if (error instanceof NoConnectionError) {
                    movieListView.showError("No Network Connectivity");
                } else if (error instanceof AuthFailureError) {
                    movieListView.showError("Invalid credentials");
                } else if (error instanceof NetworkError) {
                    movieListView.showError("Please check your network settings");
                } else if (error instanceof ServerError) {
                    movieListView.showError("Problem with the server");
                } else if (error instanceof TimeoutError) {
                    movieListView.showError("Server timed out. Try again");
                } else if (error instanceof ParseError) {
                    movieListView.showError("Request could not be parsed");
                }
                Log.d(TAG, error.toString());
            }
        });

        // set retry policy for the network request
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                ApiConstants.getTimeOutInMs(),
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the network request to the queue to be executed in the background
        VolleyNetwork.getInstance(context.getApplicationContext())
                .addToRequestQueue(jsonObjectRequest);

    }
}
