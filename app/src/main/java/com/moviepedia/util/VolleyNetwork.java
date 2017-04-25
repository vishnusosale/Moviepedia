package com.moviepedia.util;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Singleton class which provides {@link RequestQueue} to add API requests to queue and making
 * http calls
 */
public class VolleyNetwork {

    @SuppressLint("StaticFieldLeak")
    private static VolleyNetwork mInstance;
    @SuppressLint("StaticFieldLeak")
    private static Context mCtx;
    private RequestQueue mRequestQueue;

    private VolleyNetwork(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleyNetwork getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyNetwork(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(com.android.volley.Request<T> req) {
        getRequestQueue().add(req);
    }
}
