package com.example.android.betasunshine;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.betasunshine.data.WeatherContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    @BindView(R.id.textview_date)
    TextView textViewDate;
    @BindView(R.id.textview_max)
    TextView textViewMax;
    @BindView(R.id.textview_min)
    TextView textViewMin;
    @BindView(R.id.textview_description)
    TextView textViewDescription;
    @BindView(R.id.textview_pressure)
    TextView textViewPressure;
    Uri mUri;
    public static final int DETAIL_LOADER_ID = 479;
    public static final String[] PROJECTION_CONSTANT_DETAIL = {WeatherContract.WeatherEntry.COLUMN_DATE,
            WeatherContract.WeatherEntry.COLUMN_MAX, WeatherContract.WeatherEntry.COLUMN_MIN,
            WeatherContract.WeatherEntry.COLUMN_DESCRIPTION, WeatherContract.WeatherEntry.COLUMN_PRESSURE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        enableActionBar();
        getIntentFromMainActivity();
        startDetailActivityLoader();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weather_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.settings_menu:
                Intent intent = new Intent(DetailActivity.this, SettingsActvity.class);
                startActivity(intent);
                break;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;

            default:
                Toast.makeText(this, "Invalid option Selected", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        switch (id) {
            case DETAIL_LOADER_ID:
                return new CursorLoader(this, mUri, PROJECTION_CONSTANT_DETAIL, null, null, null);

            default:
                throw new RuntimeException("Loader not implemented " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        boolean cursorHasValidData = false;
        if (data != null && data.moveToFirst()) {
            cursorHasValidData = true;
        }

        if (!cursorHasValidData) {
            return;
        }
        long detailDate = data.getLong(data.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_DATE));
        String detailHigh = data.getString(data.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MAX));
        String detailLow = data.getString(data.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MIN));
        String detailDescription = data.getString(data.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_DESCRIPTION));
        String detailPressure = data.getString(data.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_PRESSURE));

        textViewDate.setText(String.valueOf(detailDate));
        textViewMax.setText(detailHigh);
        textViewMin.setText(detailLow);
        textViewDescription.setText(detailDescription);
        textViewPressure.setText(detailPressure);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void startDetailActivityLoader() {
        getSupportLoaderManager().initLoader(DETAIL_LOADER_ID, null, this);
    }

    private void getIntentFromMainActivity() {
        Intent receivedDataIntent = getIntent();
        mUri = receivedDataIntent.getData();
    }

    private void enableActionBar() {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
