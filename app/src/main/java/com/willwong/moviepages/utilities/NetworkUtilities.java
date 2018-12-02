package com.willwong.moviepages.utilities;

/**
 * Created by WillWong on 11/18/18.
 * Credits to tmDB for data resources
 * @ www.themoviedb.org
 */

import android.util.Log;
import android.net.Uri;

import java.net.URL;
import java.io.InputStream;
import java.io.IOException;
import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

public class NetworkUtilities {
    private static final String TAG = NetworkUtilities.class.getSimpleName();

    private static final String NUMPAGES = "1";
    private static final String API = "api_key";
    private static final String KEY = "3f5a704976c612ec2031093f0b3aaaca";//my developer key
    private static final String URL = "https://api.themoviedb.org/3/";
    private static final String queryType = "search/";
    private static final String MV = "movie?";
    private static final String QUERY = "query";
    //Details URL for buildDetailsUrl
    private static final String detailsURL = "https://api.themoviedb.org/3/movie/api_key=3f5a704976c612ec2031093f0b3aaaca&language=en-US";

    public static URL buildSearchUrl(String url) {
        String searchUrl = URL + queryType + MV;
        Uri builtUri = Uri.parse(searchUrl).buildUpon()
                .appendQueryParameter(API,KEY).appendQueryParameter(QUERY, url)
                .appendQueryParameter("page", NUMPAGES).build();
        URL newUrl = null;
        try {
            newUrl = new URL(builtUri.toString());
            Log.d(TAG, builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + newUrl);
        return newUrl;
    }
    public static URL buildDetailsUrl(String movieId) {
        String detailsURL = "https://api.themoviedb.org/3/movie/"+movieId+"?api_key=3f5a704976c612ec2031093f0b3aaaca&language=en-US";
        URL url = null;
        try {
            url = new URL(detailsURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG,"Details URI " + url);
        return url;


    }
    public static URL buildVideosUrl(String movieId) {
        String videosUrl = "https://api.themoviedb.org/3/movie/"+movieId+"/videos?api_key=3f5a704976c612ec2031093f0b3aaaca&language=en-US";
        URL url = null;
        try {
            url = new URL(videosUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG,"Videos URI " + url);
        return url;

    }
    public static URL buildImagesUrl(String movieId) {
        String imagesUrl = "https://api.themoviedb.org/3/movie/+"+movieId+"/images?api_key=3f5a704976c612ec2031093f0b3aaaca&language=en-US&include_image_language=en";
        URL url = null;
        try {
            url = new URL(imagesUrl);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.v(TAG,"Images Uri " + url);
        return url;
    }
    public static String httpUrlResponse(URL url) throws IOException{
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        if (conn == null) {
            Log.d(TAG, "Connection Problem");
        }
        try {
            InputStream in = conn.getInputStream();

            Scanner scan = new Scanner(in);
            scan.useDelimiter("\\A");

            boolean hasInput = scan.hasNext();
            if (hasInput) {
                return scan.next();
            } else {
                return null;
            }
        } catch (IOException e) {
            Log.v(TAG, "Connection refused");
        }finally {
            conn.disconnect();;
        }
        return null;
    }
    //TODO
    /*public static URL buildDetailsUrl(String url) {
    }*/
    public static String imageDownloadUrl(String path) {
        String fullPath = "http://image.tmdb.org/t/p/w185_and_h278_bestv2"+path;

        return fullPath;
    }
}
