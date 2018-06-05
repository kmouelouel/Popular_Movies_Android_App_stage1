package com.example.android.popularmoviesstage1.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String POPULAR_MOVIES_URL = "https://api.themoviedb.org/3/discover/movie";
    // TODO (1): add you API-KEY in order to run the project
    //***************
    private static final String API_KEY = "0";
    //*****************
    private static final String SORT_PARAM = "sort_by";
    //  private static final String SORT_VALUE = "popularity.desc";// vote_average.desc
    private final static String API_KEY_PARAM = "api_key";

    public static URL buildUrl(String sortByValue) {

        Uri builtUri = Uri.parse(POPULAR_MOVIES_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .appendQueryParameter(SORT_PARAM, sortByValue)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String buildPosterUrl(String posterPath) {
        return "http://image.tmdb.org/t/p/w185/" + posterPath;
    }


}
