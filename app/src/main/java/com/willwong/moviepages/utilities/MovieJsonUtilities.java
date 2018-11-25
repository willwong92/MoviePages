package com.willwong.moviepages.utilities;

/**
 * Created by WillWong on 11/19/18.
 */

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
public class MovieJsonUtilities {
    public static String[] getMovieStringsFromJson(Context context, String movieDetailsJson)
            throws JSONException{

        final String results = "results";

        final String OWM_MOVIE_ID = "id";

        final String OWM_TITLE = "title";

        final String OWM_OVERVIEW = "overview";

        final String OWM_RELEASE_DATE = "release_date";

        String[] parsedMovieData = null;

        JSONObject movieJson = new JSONObject(movieDetailsJson);


        // Parse the results element array which is of one element long
        // The elements contain movie id, title, overview, and release date.

        JSONArray jsonArray = movieJson.getJSONArray(results);

        JSONObject resultsObject = jsonArray.getJSONObject(0);

        //Store the selected elements in an Arraylist
        ArrayList<String> list = new ArrayList<String>();

        String mTitle = resultsObject.getString(OWM_TITLE);

        list.add(mTitle);

        String release_date = resultsObject.getString(OWM_RELEASE_DATE);

        list.add(release_date);

        String overView = resultsObject.getString(OWM_OVERVIEW);

        list.add(overView);
        // Convert arraylist to array and return the array.
        int length = list.size();

        parsedMovieData = new String[length];

        for (int i = 0; i < parsedMovieData.length; i++) {
            parsedMovieData[i] = list.get(i);
        }
        return parsedMovieData;
    }
}
