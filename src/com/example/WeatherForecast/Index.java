package com.example.WeatherForecast;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.WeatherForecast.common.Drawable;
import com.example.WeatherForecast.common.PM;
import com.example.WeatherForecast.common.Today;
import com.example.WeatherForecast.util.Conf;
import com.example.WeatherForecast.util.NetUtil;
import com.example.WeatherForecast.util.WeatherManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Index extends Activity implements View.OnClickListener {
    private ImageView cityManager;
    private ImageView share;
    private ImageView location;
    private ImageView update;
    private SharedPreferences sharedPreferences;
    private Handler handler;

    public static final int CITYSELECT_RESULT_CODE = 1;

    /**
     * Activity创建时被调用
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_index);

        initView();
        initData();
    }

    private void initView() {
        cityManager = (ImageView) this.findViewById(R.id.title_city_manager);
        share = (ImageView) this.findViewById(R.id.title_share);
        location = (ImageView) this.findViewById(R.id.title_location);
        update = (ImageView) this.findViewById(R.id.title_update);

        cityManager.setOnClickListener(this);
        share.setOnClickListener(this);
        location.setOnClickListener(this);
        update.setOnClickListener(this);
    }

    private void initData() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    Bundle data = msg.getData();
                    Gson gson = new Gson();
                    Today today = gson.fromJson(data.getString("today"), new TypeToken<Today>() {
                    }.getType());
                    PM pm = gson.fromJson(data.getString("pm25"), new TypeToken<PM>() {
                    }.getType());
                    refreshView(today, pm);
                }
            }
        };
        sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        MyApplication.curCityCode = sharedPreferences.getString("curCityCode", "101010100");
        initWeather();
    }

    private void initWeather() {
        updating(true);
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORK_NONE) {
            Toast.makeText(Index.this, "无网络连接!", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        WeatherManager manager = new WeatherManager();
                        Bundle resp = manager.getWeatherInfo(MyApplication.curCityCode);
                        Message msg = new Message();
                        msg.setData(resp);
                        msg.what = 1;
                        handler.sendMessage(msg);
                    } catch (Exception e) {
                        Toast.makeText(Index.this, "天气获取失败!", Toast.LENGTH_SHORT).show();
                        Log.i("message", "error:" + e.toString());
                    }
                }
            }).start();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_city_manager:
                Intent i = new Intent(Index.this, CityManager.class);
                startActivityForResult(i, 1);
                break;
            case R.id.title_update:
                initWeather();
                break;
            case R.id.title_share:
                break;
            case R.id.title_location:
                break;
        }
    }

    private void updating(boolean isdoing) {
        ImageView anim = (ImageView) Index.this.findViewById(R.id.title_update_anim);
        if (isdoing) {
            Animation fresh = AnimationUtils.loadAnimation(this, R.anim.fresh);
            update.setVisibility(View.INVISIBLE);
            update.setOnClickListener(null);
            anim.setVisibility(View.VISIBLE);
            anim.startAnimation(fresh);
        } else {
            update.setVisibility(View.VISIBLE);
            update.setOnClickListener(this);
            anim.clearAnimation();
            anim.setVisibility(View.INVISIBLE);
        }
    }

    private void refreshView(Today today, PM pm) {
        //title
        ((TextView) this.findViewById(R.id.title_city_name)).setText(today.result.citynm + "天气");
        //curweather
        ((TextView) this.findViewById(R.id.city)).setText(today.result.citynm);
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("HH");
        ((TextView) this.findViewById(R.id.time)).setText("今天" + s.format(d) + ":00发布");
        ((TextView) this.findViewById(R.id.humidtity)).setText("湿度:" + today.result.humidity);
        //pm2.5
        ((TextView) this.findViewById(R.id.pm2_5_num)).setText(pm.result.aqi);
        ImageView img = ((ImageView) this.findViewById(R.id.pm2_5_img));
        img.setImageResource(Drawable.getPMDrawable(pm.result.aqi));
        ((TextView) this.findViewById(R.id.pm2_5_quality)).setText(pm.result.aqi_levnm);
        //today
        ImageView timg = ((ImageView) this.findViewById(R.id.weather_img));
        timg.setImageResource(Drawable.getWeatherDrawable(today.result.weather));
        ((TextView) this.findViewById(R.id.week_today)).setText("今天 " + today.result.week);
        ((TextView) this.findViewById(R.id.temperature)).setText(today.result.temperature);
        ((TextView) this.findViewById(R.id.climate)).setText(today.result.weather);
        ((TextView) this.findViewById(R.id.wind)).setText(today.result.wind);
        updating(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case CITYSELECT_RESULT_CODE:
                MyApplication.curCityCode = data.getStringExtra("select_city");
                initWeather();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("curCityCode", MyApplication.curCityCode);
        editor.commit();
    }
}