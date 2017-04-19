package com.example.android.betasunshine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dakaku on 19/4/17.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.WeatherHolder> {
    ArrayList<String> mWeatherArrayList;
    ListViewItemListener mListViewItemListener;

    public CustomAdapter(ListViewItemListener listener) {
        mListViewItemListener=listener;
    }

    interface ListViewItemListener {
        public void onListItemClick(String data);
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

        String weatherOfTheDay = mWeatherArrayList.get(position);
        holder.textView.setText(weatherOfTheDay);
    }

    @Override
    public int getItemCount() {
        if (mWeatherArrayList == null) return 0;
        return mWeatherArrayList.size();
    }


    public class WeatherHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;

        public WeatherHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_weather);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int getItemPosition=getAdapterPosition();
            String weatherForDay=mWeatherArrayList.get(getItemPosition);
            mListViewItemListener.onListItemClick(weatherForDay);
        }
    }

public void setWeatherData(ArrayList<String> data){
    mWeatherArrayList=data;
    notifyDataSetChanged();
}
}
