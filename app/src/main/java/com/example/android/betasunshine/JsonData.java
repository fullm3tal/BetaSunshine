package com.example.android.betasunshine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by fullm3tal on 4/18/2017.
 */

public class JsonData {



     public static ArrayList<String> getStringFromJson(String data) throws JSONException{

         ArrayList<String> jsonArrayList=new ArrayList<String>();
         JSONObject jsonObject=new JSONObject(data);
         JSONArray jsonListArray=jsonObject.getJSONArray("list");
         int length=jsonListArray.length();

         for(int i=0;i<length;i++) {
             JSONObject jsonListObject = jsonListArray.getJSONObject(i);
             JSONObject jsonTempObject=jsonListObject.getJSONObject("temp");
             JSONArray jsonWeatherArray=jsonListObject.getJSONArray("weather");
             JSONObject jsonWeatherObject=jsonWeatherArray.getJSONObject(0);

             String min=jsonTempObject.getString("min");
             String max=jsonTempObject.getString("max");
             String pressure=jsonListObject.getString("pressure");
             String description=jsonWeatherObject.getString("description");
             String jsonParsedString =min+"  "+max+" "+description+" "+pressure;
             jsonArrayList.add(i,jsonParsedString);
         }
        return jsonArrayList;
     }

}
