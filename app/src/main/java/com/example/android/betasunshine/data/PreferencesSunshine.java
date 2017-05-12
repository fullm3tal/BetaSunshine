package com.example.android.betasunshine.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.android.betasunshine.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by dakaku on 20/4/17.
 */

public class PreferencesSunshine {
    public static String getPreferredLocation(Context context){

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String locationKey=context.getString(R.string.pref_location_key);
        String locationDefault=context.getString(R.string.pref_location_default);

        return pref.getString(locationKey,locationDefault);
    }

    public static boolean isMetric(Context context){
        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(context);

        String unitsKey=context.getString(R.string.pref_units_key);
        String unitsDefault =context.getString(R.string.pref_temp_metric_value);
        String preferredUnits=pref.getString(unitsKey,unitsDefault);
        String metric=context.getString(R.string.pref_temp_metric_value);
        boolean preferredBoolValue;
        if(metric.equals(preferredUnits))
        {
            preferredBoolValue=true;
        }
        else
        {
            preferredBoolValue=false;
        }
        return preferredBoolValue;
    }

    public static String getDatefromMillis(long timeInMillis){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd yyyy");
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        return  simpleDateFormat.format(calendar.getTime());
    }
}
