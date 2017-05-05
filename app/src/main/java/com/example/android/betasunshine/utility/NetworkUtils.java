package com.example.android.betasunshine.utility;

import android.net.Uri;

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

    public static final String URL_WEATHER = "https://andfun-weather.udacity.com/staticweather";
    public static final String QUERY_PARAM="q";
    public static final String QUERY_FORMAT="mode";
    public static final String QUERY_UNIT="units";
    public static final String QUERY_FORMAT_VALUE="json";
    public static final String QUERY_UNIT_VALUE="metric";
    public static final String QUERY_DAYS="cnt";
    public static final int days=14;

    public static URL buildUrl(String urlString){
        Uri builtUri=Uri.parse(URL_WEATHER).buildUpon()
                .appendQueryParameter(QUERY_PARAM,urlString).
                        appendQueryParameter(QUERY_FORMAT,QUERY_FORMAT_VALUE)
                .appendQueryParameter(QUERY_UNIT,QUERY_UNIT_VALUE)
                .appendQueryParameter(QUERY_DAYS,String.valueOf(days)).build();

        URL url=null;
        try {
            url=new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

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
