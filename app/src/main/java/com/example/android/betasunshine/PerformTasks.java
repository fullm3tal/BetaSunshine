package com.example.android.betasunshine;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.android.betasunshine.data.PreferencesSunshine;
import com.example.android.betasunshine.data.WeatherContract;
import com.example.android.betasunshine.utility.JsonData;
import com.example.android.betasunshine.utility.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

/**
 * Created by dakaku on 5/5/17.
 */

public class PerformTasks {
    public static final String LOAD_WEATHER_NETWORK = "load_network";

    public static void setUpTasks(final Context context, final String action) {

        if (action.equals(LOAD_WEATHER_NETWORK)) {
            String getLocation = PreferencesSunshine.getPreferredLocation(context);

            try {
                URL url = NetworkUtils.createUrl(getLocation);
                String data = NetworkUtils.getJsonResponse(url);
                ContentValues[] contentValues = JsonData.getStringFromJson(context, data);
                String name = Thread.currentThread().getName();
                Log.v("Thread running " + name, "inside setUpTasks");
                Log.v("Inside Perform tasks", "inside setUpTasks");
                int rowsDeletion = context.getContentResolver().delete(WeatherContract.WeatherEntry.CONTENT_URI, null, null);
                int retCursor = context.getContentResolver().bulkInsert(WeatherContract.WeatherEntry.CONTENT_URI, contentValues);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
