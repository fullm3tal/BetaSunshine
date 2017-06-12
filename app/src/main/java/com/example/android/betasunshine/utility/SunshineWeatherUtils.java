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
        temperature= (long) (temperature-273.15);

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

            case "light rain":
                return R.drawable.art_light_rain;
            case "moderate rain":
            case "very heavy rain":
            case "heavy intensity rain":
                return R.drawable.art_rain;
            case "sky is clear":
                return R.drawable.art_clear;
            case "scattered clouds":
                return R.drawable.art_light_clouds;
            case "broken clouds":
            case "overcast clouds":
                return R.drawable.art_clouds;
            case "light snow":
            case "heavy snow":
            case "snow":
                return R.drawable.art_snow;
            case "fog":
                return R.drawable.art_fog;
            case "storm":
            case "sleet":
            case "tropical storm":
            case "hurricane":
                return R.drawable.art_storm;
            default:
                Log.v("Unknown Image ","resource");
                return  0;
        }
    }

    public static int getSmallImageIdForWeather(String weatherDescription){

        switch (weatherDescription){

            case "light rain":
                return R.drawable.ic_light_rain;
            case "moderate rain":
            case "very heavy rain":
            case "heavy intensity rain":
                return R.drawable.ic_rain;
            case "sky is clear":
                return R.drawable.ic_clear;
            case "scattered clouds":
                return R.drawable.ic_light_clouds;
            case "broken clouds":
            case "overcast clouds":
                return R.drawable.ic_cloudy;
            case "light snow":
            case "heavy snow":
            case "snow":
                return R.drawable.ic_snow;
            case "fog":
                return R.drawable.ic_fog;
            case "storm":
            case "sleet":
            case "tropical storm":
            case "Hurricane":
                return R.drawable.ic_storm;
            default:
                Log.v("Unknown Image ","resource");
                return  0;
        }
    }
}
