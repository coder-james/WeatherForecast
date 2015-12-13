package com.example.WeatherForecast;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import com.example.WeatherForecast.common.Drawable;
import com.example.WeatherForecast.common.Today;
import com.example.WeatherForecast.util.Conf;
import com.example.WeatherForecast.util.Tools;
import com.example.WeatherForecast.util.WeatherManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by James on 15/12/9.
 */
public class MyWidgetProvider extends AppWidgetProvider {
    private Context context;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        this.context = context;
        Timer wTimer = new Timer();
        wTimer.scheduleAtFixedRate(new MyWeather(context, appWidgetManager), 1, 60*1000);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    public class MyWeather extends TimerTask{
        RemoteViews remoteView;
        AppWidgetManager appWidgetManager;
        ComponentName thisWidget;
        public MyWeather(Context context, AppWidgetManager appWidgetManager){
            this.appWidgetManager = appWidgetManager;
            remoteView = new RemoteViews(context.getPackageName(), R.layout.weather_widget);
            thisWidget = new ComponentName(context, MyWidgetProvider.class);
        }

        @Override
        public void run() {
            try {
                WeatherManager manager = new WeatherManager();
                Bundle data = manager.getWeatherInfo(MyApplication.curCityCode);
                Gson gson = new Gson();
                Today today = gson.fromJson(data.getString("today"), new TypeToken<Today>() {
                }.getType());
                Intent intent = new Intent();
                intent.setClass(MyWidgetProvider.this.context, MainActivity.class);
                String cityname,weather,week;
                if(! context.getResources().getConfiguration().locale.getLanguage().equals(Conf.ENGLISH)){
                    cityname = today.result.citynm;
                    weather = today.result.weather;
                    week = today.result.week;
                }else{
                    cityname = today.result.cityno;
                    weather = Tools.chToEN(today.result.weather);
                    week = Tools.getEnWeek(today.result.week);
                }
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                remoteView.setTextViewText(R.id.widget_temperature, today.result.temperature);
                remoteView.setTextViewText(R.id.widget_date, (new SimpleDateFormat("hh:mm").format(new Date())));
                remoteView.setTextViewText(R.id.widget_time, today.result.days+"  "+ week);
                remoteView.setTextViewText(R.id.widget_weather, weather);
                remoteView.setTextViewText(R.id.widget_city, cityname);
                remoteView.setImageViewResource(Drawable.getWeatherDrawable(today.result.weather), R.drawable.biz_plugin_weather_qing);
                remoteView.setOnClickPendingIntent(R.id.widget ,pendingIntent);
                appWidgetManager.updateAppWidget(thisWidget, remoteView);
            } catch (Exception e) {
                Log.i("message", "error:" + e.toString());
            }
        }
    }
}
