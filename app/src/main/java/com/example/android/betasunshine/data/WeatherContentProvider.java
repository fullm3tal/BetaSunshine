package com.example.android.betasunshine.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.net.URLDecoder;

/**
 * Created by dakaku on 21/4/17.
 */

public class WeatherContentProvider extends ContentProvider {
    public static final int WEATHERS = 101;
    public static final int WEATHERS_WITH_DATE = 102;
    public UriMatcher sUriMatcher = buildUriMatcher();

    public UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(WeatherContract.AUTHORITY, WeatherContract.PATHS, WEATHERS);
        uriMatcher.addURI(WeatherContract.AUTHORITY, WeatherContract.PATHS + "/#", WEATHERS_WITH_DATE);
        return uriMatcher;

    }

    WeatherDbHelper weatherDbHelper;

    @Override
    public boolean onCreate() {

        Context context = getContext();
        weatherDbHelper = new WeatherDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int matchId = sUriMatcher.match(uri);
        Cursor returnCursor;
        switch (matchId) {
            case WEATHERS:
                returnCursor = weatherDbHelper.getReadableDatabase().query(WeatherContract.WeatherEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case WEATHERS_WITH_DATE:
                String getDate = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{getDate};

                returnCursor = weatherDbHelper.getReadableDatabase().query(WeatherContract.WeatherEntry.TABLE_NAME, projection,
                        WeatherContract.WeatherEntry.COLUMN_DATE + " = ? ", selectionArguments, null, null, sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);

        }
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        final SQLiteDatabase db = weatherDbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case WEATHERS:
                db.beginTransaction();
                int rowsInserted = 0;
                try {

                    for (ContentValues contentValues : values) {
//                        long weatherDate=contentValues.getAsLong(WeatherContract.WeatherEntry.COLUMN_DATE);
                        long id = db.insert(WeatherContract.WeatherEntry.TABLE_NAME, null, contentValues);
                        if (id != -1) {
                            rowsInserted++;
                        }

                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;

            default:
                return super.bulkInsert(uri, values);
        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (null == selection) {
            selection = "1";
        }
        int rowsDeleted;
        switch (sUriMatcher.match(uri)) {
            case WEATHERS:

                rowsDeleted = weatherDbHelper.getWritableDatabase().delete(WeatherContract.WeatherEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
