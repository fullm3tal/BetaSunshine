package com.example.android.betasunshine;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.betasunshine.data.WeatherContract;


/**
 * Created by dakaku on 5/5/17.
 */

public class WeatherService extends IntentService {

    public WeatherService(){
        super("WeatherService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action=  intent.getAction();
        Log.v("LOG_TAG","inside onStartCommand");
        PerformTasks.setUpTasks(this,action);
          stopSelf();
    }
}
