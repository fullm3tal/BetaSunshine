package com.example.android.betasunshine.utility;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by fullm3tal on 4/18/2017.
 */

public class NetworkUtils {

    public static final String URL_WEATHER="http://api.openweathermap.org/data/2.5/forecast/daily";

    private static final String QUERY_PARAM = "q";
    private static final String QUERY_DAYS = "cnt";
    private static final String QUERY_APPID = "APPID";
    private static final String api_key= "a06f6039312444ed8c4962158486addd";
    private static final int days=16;

    public static URL createUrl(String urlString) throws MalformedURLException{

        Uri builtUri=Uri.parse(URL_WEATHER).buildUpon()
                .appendQueryParameter(QUERY_PARAM,urlString)
                .appendQueryParameter(QUERY_DAYS,String.valueOf(days)).appendQueryParameter(QUERY_APPID,api_key).build();
        Log.v("hi",builtUri.toString());
        URL url=new URL(builtUri.toString());
        return url;
    }


    public static String getJsonResponse(URL url) throws IOException{
        HttpURLConnection httpURLConnection= (HttpURLConnection)url.openConnection();
        try {
            InputStream inputStream = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            Boolean hasInput = scanner.hasNext();
            scanner.useDelimiter("\\A");
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        }
        finally{
            httpURLConnection.disconnect();
        }
    }
}
