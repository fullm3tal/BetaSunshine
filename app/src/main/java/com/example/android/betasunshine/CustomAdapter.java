package com.example.android.betasunshine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dakaku on 19/4/17.
 */

public class CustomAdapter extends ArrayAdapter<String> {
    Context mContext;
    ArrayList<String> mWeatherArrayList;
    TextView textViewWeather;

    public CustomAdapter(Context context, ArrayList<String> stringArrayList) {
        super(context, -1, stringArrayList);
        mContext = context;
        mWeatherArrayList = stringArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        if (convertView == null) {
            rowView = LayoutInflater.from(mContext).inflate(R.layout.list_item_weather, parent, false);
        }
        textViewWeather=(TextView)rowView.findViewById(R.id.tv_weather);
        textViewWeather.setText(mWeatherArrayList.get(position));
        return rowView;
    }
}
