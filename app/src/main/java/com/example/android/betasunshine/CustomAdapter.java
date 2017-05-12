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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.betasunshine.data.PreferencesSunshine;
import com.example.android.betasunshine.data.WeatherContract;
import com.example.android.betasunshine.utility.SunshineWeatherUtils;

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
    public static final int VIEW_TYPE_TODAY=0;
    public static final int VIEW_TYPE_FUTURE=1;

    public CustomAdapter(Context context, ListViewItemListener listener) {
        mListViewItemListener = listener;
        mContext = context;
    }

    interface ListViewItemListener {
        void onListItemClick(long date);
    }

    @Override
    public WeatherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutForId;
        switch (viewType){
            case VIEW_TYPE_TODAY:
                layoutForId = R.layout.list_today_forecast;
                break;
            case VIEW_TYPE_FUTURE:
                layoutForId = R.layout.list_item_weather;
                break;
            default:
                throw new UnsupportedOperationException("Unknown View");
        }
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

        @BindView(R.id.tv_weather_date)
        TextView textViewDate;
        @BindView(R.id.tv_weather_description)
        TextView textViewDescription;
        @BindView(R.id.tv_weather_max)
        TextView textViewMax;
        @BindView(R.id.tv_weather_min)
        TextView textViewMin;
        @BindView(R.id.tv_weather_image)
        ImageView imageViewWeather;


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
        int resId;
        Log.v("Inside Custom adapter  ","Image Display ");
        String description = mCursor.getString(mCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_DESCRIPTION));
        int viewType=getItemViewType(position);
        switch (viewType){
            case VIEW_TYPE_TODAY:
                resId= SunshineWeatherUtils.getLargeImageIdForWeather(description);
                break;
            case VIEW_TYPE_FUTURE:
                resId=SunshineWeatherUtils.getSmallImageIdForWeather(description);
                break;
            default:
                throw new UnsupportedOperationException("unknown Id");
        }

        long date = mCursor.getLong(mCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_DATE));
        String convertedDate = PreferencesSunshine.getDatefromMillis(date);
        String max = mCursor.getString(mCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MAX));
        String min = mCursor.getString(mCursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MIN));


        holder.imageViewWeather.setImageResource(resId);
        holder.textViewDate.setText(convertedDate);
        holder.textViewDescription.setText(description);
        holder.textViewMax.setText(max);
        holder.textViewMin.setText(min);
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return VIEW_TYPE_TODAY;
        }
        else
        {
            return VIEW_TYPE_FUTURE;
        }
    }
}
