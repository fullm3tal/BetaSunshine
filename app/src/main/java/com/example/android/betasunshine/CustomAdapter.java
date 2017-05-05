package com.example.android.betasunshine;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.betasunshine.data.WeatherContract;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dakaku on 19/4/17.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.WeatherHolder> {
    public final String LOG_TAG = CustomAdapter.this.getClass().getSimpleName();
    Cursor mCursor;
    Context mContext;
    ListViewItemListener mListViewItemListener;

    public CustomAdapter(Context context, ListViewItemListener listener) {
        mListViewItemListener = listener;
        mContext = context;
    }

    interface ListViewItemListener {
        void onListItemClick(long date);
    }

    @Override
    public WeatherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutForId = R.layout.list_item_weather;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutForId, parent, shouldAttachToParentImmediately);
        WeatherHolder weatherHolder = new WeatherHolder(view);
        return weatherHolder;
    }

    @Override
    public void onBindViewHolder(WeatherHolder holder, int position) {
        populatingViewsWithData(holder, position);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }


    public class WeatherHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_weather)
        TextView textView;

        public WeatherHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            long date = mCursor.getLong(mCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_DATE));
            mListViewItemListener.onListItemClick(date);
        }
    }


    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    private void populatingViewsWithData(WeatherHolder holder, int position) {
        mCursor.moveToPosition(position);
        String date = mCursor.getString(mCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_DATE));
        String max = mCursor.getString(mCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MAX));
        String min = mCursor.getString(mCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MIN));
        String description = mCursor.getString(mCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_DESCRIPTION));
        String pressure = mCursor.getString(mCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_PRESSURE));
        Log.v(LOG_TAG,"Async task Loader called, Custom Adapter");
        String weatherOfTheDay = date + " " + max + " " + min + " " + description + " " + pressure;
        holder.textView.setText(weatherOfTheDay);
    }
}
