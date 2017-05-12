package com.example.android.betasunshine.utility;

import android.content.Context;
import android.util.Log;

import com.example.android.betasunshine.R;
import com.example.android.betasunshine.data.PreferencesSunshine;

/**
 * Created by dakaku on 20/4/17.
 */

public class SunshineWeatherUtils {

    public static String formatTemp(Context context, double temp) {
        String tempInString;
        long temperature = Math.round(temp);
        if (PreferencesSunshine.isMetric(context)) {
            tempInString = String.valueOf(temperature);
            return tempInString + "\u2103";

        } else {

            double temptoFahrenheit = (temperature * 1.8) + 32;
            long tempFahrenheit = Math.round(temptoFahrenheit);
            tempInString = String.valueOf(tempFahrenheit);
            return tempInString + "\u2109";
        }

    }
    public static int getLargeImageIdForWeather(String weatherDescription){

        switch (weatherDescription){

            case "Light Rain":
                return R.drawable.art_light_rain;
            case "Moderate Rain":
                return R.drawable.art_rain;
            case "Clear":
                return R.drawable.art_clear;
            case "Scattered Clouds":
                return R.drawable.art_light_clouds;
            case "Broken Clouds":
                return R.drawable.art_clouds;
            case "Light Snow":
            case "Heavy Snow":
            case "Snow":
                return R.drawable.art_snow;
            case "Fog":
                return R.drawable.art_fog;
            case "Storm":
            case "Sleet":
            case "Tropical Storm":
            case "Hurricane":
                return R.drawable.art_storm;
            default:
                Log.v("Unknown Image ","resource");
                return  0;
        }
    }

    public static int getSmallImageIdForWeather(String weatherDescription){

        switch (weatherDescription){

            case "Light Rain":
                return R.drawable.ic_light_rain;
            case "Moderate Rain":
                return R.drawable.ic_rain;
            case "Clear":
                return R.drawable.ic_clear;
            case "Scattered Clouds":
                return R.drawable.ic_light_clouds;
            case "Broken Clouds":
                return R.drawable.ic_cloudy;
            case "Light Snow":
            case "Heavy Snow":
            case "Snow":
                return R.drawable.ic_snow;
            case "Fog":
                return R.drawable.ic_fog;
            case "Storm":
            case "Sleet":
            case "Tropical Storm":
            case "Hurricane":
                return R.drawable.ic_storm;
            default:
                Log.v("Unknown Image ","resource");
                return  0;
        }
    }
}
