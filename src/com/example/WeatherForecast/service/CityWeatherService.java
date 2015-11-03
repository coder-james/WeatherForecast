package com.example.WeatherForecast.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
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
    private static final int UPDATE_INTERVAL = 1000 * 60;
    private Timer timer = new Timer();
    private Bundle refreshBundle;

    @Override
    public IBinder onBind(Intent intent) {
        return new WeatherBinder();
    }

    public Bundle getRefreshBundle(){
        return refreshBundle;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    WeatherManager manager = new WeatherManager();
                    refreshBundle = manager.getWeatherInfo(MyApplication.curCityCode);
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

    public class WeatherBinder extends Binder{
        public CityWeatherService getService(){
            return CityWeatherService.this;
        }
    }
}
