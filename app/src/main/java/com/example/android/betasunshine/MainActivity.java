package com.example.android.betasunshine;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.betasunshine.Utility.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<String>>, CustomAdapter.ListViewItemListener {

    public static final String URL_WEATHER = "https://andfun-weather.udacity.com/staticweather";
    CustomAdapter customAdapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    public static final int LOADER_ID = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        customAdapter = new CustomAdapter(this);
        progressBar = (ProgressBar) findViewById(R.id.pb_indicator);
        recyclerView.setAdapter(customAdapter);

        getSupportLoaderManager().initLoader(LOADER_ID, null, MainActivity.this);

    }

    @Override
    public Loader<ArrayList<String>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<String>>(this) {

            ArrayList<String> mWeatherData;

            @Override
            protected void onStartLoading() {
                if (mWeatherData != null) {
                    deliverResult(mWeatherData);
                }
                else

                {
                    progressBar.setVisibility(View.VISIBLE);
                    forceLoad();
                }


            }

            @Override
            public ArrayList<String> loadInBackground() {
                Log.v("loader", "callbacks");
                ArrayList<String> finalArrayData = new ArrayList<>();
                URL url = NetworkUtils.buildUrl(URL_WEATHER);

                try {
                    String data = NetworkUtils.getJsonResponse(url);
                    finalArrayData = JsonData.getStringFromJson(data);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
                return finalArrayData;
            }

            public void deliverResult(ArrayList<String> data) {
                mWeatherData = data;
                super.deliverResult(data);
            }
        };
    }


    @Override
    public void onLoadFinished(Loader<ArrayList<String>> loader, ArrayList<String> data) {
        customAdapter.setWeatherData(data);
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<String>> loader) {

    }

    @Override
    public void onListItemClick(String data) {
        Intent detailActivityIntent = new Intent(MainActivity.this, DetailActivity.class);
        detailActivityIntent.putExtra(Intent.EXTRA_TEXT, data);
        startActivity(detailActivityIntent);
    }
}
