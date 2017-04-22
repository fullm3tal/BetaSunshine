package com.example.android.betasunshine;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.android.betasunshine.Utility.SunshineWeatherUtils;
import com.example.android.betasunshine.data.WeatherContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by fullm3tal on 4/18/2017.
 */

public class JsonData {


    public static ContentValues[] getStringFromJson(Context context,String data) throws JSONException {

        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonListArray = jsonObject.getJSONArray("list");
        int length = jsonListArray.length();

        ContentValues[] weatherContentValues=new ContentValues[length];

        for (int i = 0; i < length; i++) {
            JSONObject jsonListObject = jsonListArray.getJSONObject(i);
            JSONObject jsonTempObject = jsonListObject.getJSONObject("temp");
            JSONArray jsonWeatherArray = jsonListObject.getJSONArray("weather");
            JSONObject jsonWeatherObject = jsonWeatherArray.getJSONObject(0);



            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
            c.add(Calendar.DATE,i);
            String formattedDate = df.format(c.getTime());

            long millisDate=0;
            Log.v("date","show "+formattedDate);
            try {
                Date dateToday=new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).parse(formattedDate);
                millisDate=dateToday.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.v("date","show "+millisDate);

            ContentValues contentValues=new ContentValues();


            double low = jsonTempObject.getDouble("min");
            String convertedTempLow= SunshineWeatherUtils.formatTemp(context,low);
            double high = jsonTempObject.getDouble("max");
            String convertedTempHigh= SunshineWeatherUtils.formatTemp(context,high);
            String pressure = jsonListObject.getString("pressure");
            String description = jsonWeatherObject.getString("description");

            contentValues.put(WeatherContract.WeatherEntry.COLUMN_DATE,millisDate);
            contentValues.put(WeatherContract.WeatherEntry.COLUMN_MAX,convertedTempHigh);
            contentValues.put(WeatherContract.WeatherEntry.COLUMN_MIN,convertedTempLow);
            contentValues.put(WeatherContract.WeatherEntry.COLUMN_DESCRIPTION,description);
            contentValues.put(WeatherContract.WeatherEntry.COLUMN_PRESSURE,pressure);

            weatherContentValues[i]=contentValues;
        }
        return weatherContentValues;
    }
}
