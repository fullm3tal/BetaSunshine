package com.example.android.betasunshine.Utility;

import android.content.Context;

import com.example.android.betasunshine.data.PreferencesSunshine;

/**
 * Created by dakaku on 20/4/17.
 */

public class SunshineWeatherUtils {

    public static String formatTemp(Context context, double temp) {
        String tempInString;
        long tempertaure = Math.round(temp);
        if (PreferencesSunshine.isMetric(context)) {
            tempInString = String.valueOf(tempertaure);
            return tempInString + "\u2103";

        } else {

            double temptoFahrenheit = (tempertaure * 1.8) + 32;
            long tempFahrenheit = Math.round(temptoFahrenheit);
            tempInString = String.valueOf(tempFahrenheit);
            return tempInString + "\u2109";
        }

    }
}
