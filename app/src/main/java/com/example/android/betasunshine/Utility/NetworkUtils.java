package com.example.android.betasunshine.Utility;

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


    public static URL buildUrl(String urlString){
        URL url=null;
        try {
            url=new URL(urlString);
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
