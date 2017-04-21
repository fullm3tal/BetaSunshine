package com.example.android.betasunshine.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dakaku on 20/4/17.
 */

public class WeatherContract {

    public static final String AUTHORITY="com.example.android.betasunshine";
    public static final Uri BASE_CONTENT_URI=Uri.parse("content// "+AUTHORITY);
    public  static final String PATHS="weather";



    public static final class WeatherEntry implements BaseColumns {

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATHS).build();

        public static final String TABLE_NAME = "weather";
        public static final String COLUMN_MAX = "max";
        public static final String COLUMN_MIN = "min";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PRESSURE = "pressure";

    }
}
