package com.example.android.betasunshine;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.betasunshine.Utility.NetworkUtils;
import com.example.android.betasunshine.data.PreferencesSunshine;
import com.example.android.betasunshine.data.WeatherContentProvider;
import com.example.android.betasunshine.data.WeatherContract;
import com.example.android.betasunshine.data.WeatherDbHelper;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, CustomAdapter.ListViewItemListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private SQLiteDatabase db;
    CustomAdapter customAdapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    public static final int LOADER_ID = 22;
    public static boolean PREFERENCE_HAS_BEEN_UPDATED = false;
    public static final String[] PROJECTION_CONSTANTS = {WeatherContract.WeatherEntry.COLUMN_DATE,
            WeatherContract.WeatherEntry.COLUMN_MAX, WeatherContract.WeatherEntry.COLUMN_MIN,
            WeatherContract.WeatherEntry.COLUMN_DESCRIPTION, WeatherContract.WeatherEntry.COLUMN_PRESSURE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_list);

        WeatherDbHelper weatherDbHelper = new WeatherDbHelper(MainActivity.this);
        db = weatherDbHelper.getWritableDatabase();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        customAdapter = new CustomAdapter(this, this);
        progressBar = (ProgressBar) findViewById(R.id.pb_indicator);
        recyclerView.setAdapter(customAdapter);

        getSupportLoaderManager().initLoader(LOADER_ID, null, MainActivity.this);
        android.preference.PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, final Bundle args) {

        switch (loaderId) {
            case LOADER_ID:
                String sortBy = WeatherContract.WeatherEntry.COLUMN_DATE + " ASC ";
                String selection = WeatherContract.WeatherEntry.getcurrentdate();

                return new CursorLoader(MainActivity.this, WeatherContract.WeatherEntry.CONTENT_URI, PROJECTION_CONSTANTS, selection, null, sortBy);

            default:
                throw new RuntimeException("LOADER not implemented" + loaderId);

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.weather_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.settings_menu:
                Intent intent = new Intent(MainActivity.this, SettingsActvity.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, "Invalid option Selected", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            customAdapter.swapCursor(data);
            progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        customAdapter.swapCursor(null);
    }

    @Override
    public void onListItemClick(long date) {
        Intent detailActivityIntent = new Intent(MainActivity.this, DetailActivity.class);
        Uri uri= WeatherContract.WeatherEntry.buildUriWithDate(date);
        detailActivityIntent.setData(uri);
        startActivity(detailActivityIntent);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        PREFERENCE_HAS_BEEN_UPDATED = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (PREFERENCE_HAS_BEEN_UPDATED) {
            getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
            PREFERENCE_HAS_BEEN_UPDATED = false;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.preference.PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
}
