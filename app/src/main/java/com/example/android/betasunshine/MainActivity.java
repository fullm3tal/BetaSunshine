package com.example.android.betasunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.betasunshine.data.WeatherContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, CustomAdapter.ListViewItemListener, SharedPreferences.OnSharedPreferenceChangeListener {

    public final String LOG_TAG = MainActivity.this.getClass().getSimpleName();
    CustomAdapter customAdapter;
    @BindView(R.id.rv_list)
    RecyclerView recyclerView;

    public static final int LOADER_ID = 22;
    public static boolean PREFERENCE_HAS_BEEN_UPDATED = false;
    public static final String[] PROJECTION_CONSTANTS = {WeatherContract.WeatherEntry.COLUMN_DATE,
            WeatherContract.WeatherEntry.COLUMN_MAX, WeatherContract.WeatherEntry.COLUMN_MIN,
            WeatherContract.WeatherEntry.COLUMN_DESCRIPTION, WeatherContract.WeatherEntry.COLUMN_PRESSURE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(LOG_TAG,"Async task Loader called, onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        passingWeatherServiceIntent();
        setUpAdapter();
        startMainAsyncLoader();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, final Bundle args) {
        switch (loaderId) {
            case LOADER_ID:
                Log.v(LOG_TAG,"Async task Loader, onCreate Loader");
                String sortBy = WeatherContract.WeatherEntry.COLUMN_DATE + " ASC ";
                String selection = WeatherContract.WeatherEntry.getcurrentdate();
                return new CursorLoader(MainActivity.this, WeatherContract.WeatherEntry.CONTENT_URI, PROJECTION_CONSTANTS, selection, null, sortBy);
            default:
                throw new RuntimeException("LOADER not implemented" + loaderId);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weather_menu, menu);
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
        Log.v(LOG_TAG,"Async task Loader, onLoadFinished ");
        customAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.v(LOG_TAG,"Async task Loader called, onLoaderReset");
        customAdapter.swapCursor(null);
    }

    @Override
    public void onListItemClick(long date) {
        Intent detailActivityIntent = new Intent(MainActivity.this, DetailActivity.class);
        Uri uri = WeatherContract.WeatherEntry.buildUriWithDate(date);
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
        Log.v(LOG_TAG,"Async task Loader called, onStart");
        if (PREFERENCE_HAS_BEEN_UPDATED) {
            getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
            PREFERENCE_HAS_BEEN_UPDATED = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG,"Async task Loader called, onDestroy");
        stopService(new Intent(MainActivity.this,WeatherService.class));
        android.preference.PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    private void passingWeatherServiceIntent() {
        Intent intent = new Intent(MainActivity.this, WeatherService.class);
        Log.v(LOG_TAG, "Intent Service Calling");
        intent.setAction(PerformTasks.LOAD_WEATHER_NETWORK);
        startService(intent);
    }

    private void setUpAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        customAdapter = new CustomAdapter(this, this);
        recyclerView.setAdapter(customAdapter);
    }

    private void startMainAsyncLoader() {
        Log.v(LOG_TAG,"Async task Loader called");
        getSupportLoaderManager().initLoader(LOADER_ID, null, MainActivity.this);
        android.preference.PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

}
