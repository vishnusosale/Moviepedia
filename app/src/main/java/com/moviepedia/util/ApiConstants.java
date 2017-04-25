package com.moviepedia.util;


final public class ApiConstants {

    private static final String API_KEY = "687c09fb8d67fee2d49eea108d71400a";
    private static final String SCHEME = "https";
    private static final String AUTHORITY = "api.themoviedb.org";
    private static final String API_VERSION = "3";
    private static final String MOVIE_PATH = "movie";
    private static final String POPULAR_PATH = "popular";
    private static final String API_KEY_PARAMETER = "api_key";
    private static final String LANGUAGE_PARAMETER = "language";
    private static final String PAGE_PARAMETER = "page";
    private static final int TIME_OUT_IN_MS = 10000;

    private ApiConstants() {
    }

    /**
     * @return Time in milliseconds
     */
    public static int getTimeOutInMs() {
        return TIME_OUT_IN_MS;
    }

    /**
     * @return Registered API key with the API provider
     */
    public static String getApiKey() {
        return API_KEY;
    }

    /**
     * @return The protocol used for the network request
     */
    public static String getScheme() {
        return SCHEME;
    }

    /**
     * @return The base URL of the API provider
     */
    public static String getAuthority() {
        return AUTHORITY;
    }

    /**
     * @return Version of the API to be used
     */
    public static String getApiVersion() {
        return API_VERSION;
    }

    /**
     * @return Path to be appended to query Movie
     */
    public static String getMoviePath() {
        return MOVIE_PATH;
    }

    /**
     * @return Path to be appended to query Popular movies
     */
    public static String getPopularPath() {
        return POPULAR_PATH;
    }

    /**
     * @return The query parameter to add API key
     */
    public static String getApiKeyParameter() {
        return API_KEY_PARAMETER;
    }

    /**
     * @return The query parameter to add Language
     */
    public static String getLanguageParameter() {
        return LANGUAGE_PARAMETER;
    }

    /**
     * @return The page parameter to indicate pagination
     */
    public static String getPageParameter() {
        return PAGE_PARAMETER;
    }
}
