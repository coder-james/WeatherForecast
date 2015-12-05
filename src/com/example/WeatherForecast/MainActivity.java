package com.example.WeatherForecast;

import android.content.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.example.WeatherForecast.fragment.FutureWeatherFragment1;
import com.example.WeatherForecast.fragment.FutureWeatherFragment2;
import com.example.WeatherForecast.service.CityWeatherService;
import com.example.WeatherForecast.util.Conf;
import com.example.WeatherForecast.util.NetUtil;
import com.example.WeatherForecast.util.Tools;
import com.example.WeatherForecast.util.WeatherManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends FragmentActivity{
    private ImageView cityManager;
    private ImageView share;
    private ImageView location;
    private ImageView update;
    private SharedPreferences sharedPreferences;
    private Handler handler;
    private ViewPager viewPager;

    private List<Fragment> fragmentList;
    private ImageView[] dots;
    private int[] ids = {R.id.future_iv1, R.id.future_iv2};

    public static final int CITYSELECT_RESULT_CODE = 1;
    private static final int REFRESH_CURR = 1;
    private static final int BROADCAST_CURR = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mainactivity);

        initView();
        initData();
    }

    private void initView() {
        cityManager = (ImageView) this.findViewById(R.id.title_city_manager);
        share = (ImageView) this.findViewById(R.id.title_share);
        location = (ImageView) this.findViewById(R.id.title_location);
        update = (ImageView) this.findViewById(R.id.title_update);
        viewPager = (ViewPager) this.findViewById(R.id.future_views);

        cityManager.setOnClickListener(myClickListener);
        share.setOnClickListener(myClickListener);
        location.setOnClickListener(myClickListener);
        update.setOnClickListener(myClickListener);
    }

    private void initData() {
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORK_NONE) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
        } else {
            updating(true);
            startService(new Intent(this, CityWeatherService.class));
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(CityWeatherService.REFRESH_ACTION);
        registerReceiver(intentReceiver, filter);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == REFRESH_CURR || msg.what == BROADCAST_CURR) {
                    Bundle data = msg.getData();
                    Gson gson = new Gson();
                    Today today = gson.fromJson(data.getString("today"), new TypeToken<Today>() {
                    }.getType());
                    PM pm = gson.fromJson(data.getString("pm25"), new TypeToken<PM>() {
                    }.getType());
                    if(today.success.equals("1") && pm.success.equals("1")){
                        refreshView(today, pm, data.getString("future"));
                    }else{
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.request_failed), Toast.LENGTH_SHORT).show();
                        updating(false);
                    }
                }
            }
        };
        sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        MyApplication.curCityCode = sharedPreferences.getString("curCityCode", "101010100");
        fragmentList = new ArrayList<>();
        dots = new ImageView[ids.length];
        for (int i = 0; i < ids.length; i++) {
            dots[i] = (ImageView) findViewById(ids[i]);
        }
        viewPager.setOnPageChangeListener(myPageChangeListener);
    }

    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Message msg = handler.obtainMessage();
            msg.what = BROADCAST_CURR;
            msg.setData(intent.getExtras());
            handler.sendMessage(msg);
        }
    };
    private void initWeather() {
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORK_NONE) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
        } else {
            updating(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String error = getResources().getString(R.string.access_weather_failed);
                    try {
                        WeatherManager manager = new WeatherManager();
                        Bundle resp = manager.getWeatherInfo(MyApplication.curCityCode);
                        Message msg = new Message();
                        msg.setData(resp);
                        msg.what = REFRESH_CURR;
                        handler.sendMessage(msg);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                        Log.i("message", "error:" + e.toString());
                    }
                }
            }).start();
        }
    }

    private View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.title_city_manager:
                    Intent i = new Intent(MainActivity.this, CityListActivity.class);
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
    };

    private void updating(boolean isdoing) {
        ImageView anim = (ImageView) MainActivity.this.findViewById(R.id.title_update_anim);
        if (isdoing) {
            Animation fresh = AnimationUtils.loadAnimation(this, R.anim.fresh);
            update.setVisibility(View.INVISIBLE);
            update.setOnClickListener(null);
            anim.setVisibility(View.VISIBLE);
            anim.startAnimation(fresh);
        } else {
            update.setVisibility(View.VISIBLE);
            update.setOnClickListener(myClickListener);
            anim.clearAnimation();
            anim.setVisibility(View.INVISIBLE);
        }
    }

    private void refreshView(Today today, PM pm, String future) {
        String cityname,weather,wind,week,jintian;
        Log.i("test",getResources().getConfiguration().locale.getLanguage());
        if(! getResources().getConfiguration().locale.getLanguage().equals(Conf.ENGLISH)){
            cityname = today.result.citynm;
            weather = today.result.weather;
            wind = today.result.wind + today.result.winp;
            week = today.result.week;
            jintian = getResources().getString(R.string.jintian);
        }else{
            cityname = today.result.cityno;
            weather = Tools.chToEN(today.result.weather);
            wind = Tools.getEnWind(today.result.wind, today.result.winp);
            week = Tools.getEnWeek(today.result.week);
            jintian = "";
        }
        //title
        ((TextView) this.findViewById(R.id.title_city_name)).setText(cityname + getResources().getString(R.string.tianqi));
        //curweather
        ((TextView) this.findViewById(R.id.city)).setText(cityname);
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("HH");
        ((TextView) this.findViewById(R.id.time)).setText(jintian + s.format(d) + ":00 " + getResources().getString(R.string.fabu));
        ((TextView) this.findViewById(R.id.humidtity)).setText(getResources().getString(R.string.shidu) + ":" + today.result.humidity);
        //pm2.5
        ((TextView) this.findViewById(R.id.pm2_5_num)).setText(pm.result.aqi);
        ImageView img = ((ImageView) this.findViewById(R.id.pm2_5_img));
        img.setImageResource(Drawable.getPMDrawable(pm.result.aqi));
        ((TextView) this.findViewById(R.id.pm2_5_quality)).setText(pm.result.aqi_levnm);
        //today
        ImageView timg = ((ImageView) this.findViewById(R.id.weather_img));
        timg.setImageResource(Drawable.getWeatherDrawable(today.result.weather));
        ((TextView) this.findViewById(R.id.week_today)).setText(jintian + " " + week);
        ((TextView) this.findViewById(R.id.temperature)).setText(today.result.temperature);
        ((TextView) this.findViewById(R.id.climate)).setText(weather);
        ((TextView) this.findViewById(R.id.wind)).setText(wind);

        FutureWeatherFragment1 fragment1 = FutureWeatherFragment1.newInstance(future);
        FutureWeatherFragment2 fragment2 = FutureWeatherFragment2.newInstance(future);
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        updating(false);
    }
    public class MyViewPagerAdapter extends FragmentPagerAdapter{
        public MyViewPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    private ViewPager.OnPageChangeListener myPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {}

        @Override
        public void onPageSelected(int selectpage) {
            for (int i1 = 0; i1 < ids.length; i1++) {
                if(i1 == selectpage){
                    dots[i1].setImageResource(R.drawable.page_indicator_focused);
                }else{
                    dots[i1].setImageResource(R.drawable.page_indicator_unfocused);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {}
    };

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
        unregisterReceiver(intentReceiver);
        stopService(new Intent(this, CityWeatherService.class));
        super.onDestroy();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("curCityCode", MyApplication.curCityCode);
        editor.commit();
    }
}