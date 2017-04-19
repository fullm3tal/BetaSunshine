package com.example.android.betasunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.betasunshine.Utility.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String URL_WEATHER = "https://andfun-weather.udacity.com/staticweather";
    TextView textView;
    CustomAdapter customAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.list_item);
        new WeatherTask().execute(URL_WEATHER);
    }

    public class WeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String urlString = params[0];
            String getJSON = null;
            URL url = NetworkUtils.buildUrl(urlString);
            try {
                getJSON = NetworkUtils.getJsonResponse(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return getJSON;
        }

        @Override
        protected void onPostExecute(String s) {
            ArrayList<String> finalData=new ArrayList<String>();
            try {
                finalData=JsonData.getStringFromJson(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            customAdapter=new CustomAdapter(MainActivity.this,finalData);
                    listView.setAdapter(customAdapter);
        }
    }

}
