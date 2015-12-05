package com.example.WeatherForecast.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.example.WeatherForecast.MyApplication;
import com.example.WeatherForecast.util.WeatherManager;

import java.io.IOException;
import java.net.BindException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by James on 15/10/31.
 */
public class CityWeatherService extends Service {
    private static final int UPDATE_INTERVAL = 1000 * 60 * 60;
    private Timer timer = new Timer();
    public static final String REFRESH_ACTION = "refresh_action";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    WeatherManager manager = new WeatherManager();
                    Intent intent = new Intent();
                    intent.setAction(REFRESH_ACTION);
                    Bundle bundle = manager.getWeatherInfo(MyApplication.curCityCode);
                    intent.putExtras(bundle);
                    sendBroadcast(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, UPDATE_INTERVAL);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
