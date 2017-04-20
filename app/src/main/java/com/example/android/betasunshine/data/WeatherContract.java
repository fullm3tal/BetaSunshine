package com.example.android.betasunshine.data;

import android.provider.BaseColumns;

/**
 * Created by dakaku on 20/4/17.
 */

public class WeatherContract {

    public class WeatherEntry implements BaseColumns {
        public static final String TABLE_NAME = "weather";
        public static final String COLUMN_MAX = "max";
        public static final String COLUMN_MIN = "min";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PRESSURE = "pressure";

    }
}
