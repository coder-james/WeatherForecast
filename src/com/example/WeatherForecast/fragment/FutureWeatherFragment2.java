package com.example.WeatherForecast.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.WeatherForecast.R;
import com.example.WeatherForecast.common.Drawable;
import com.example.WeatherForecast.common.Future;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by James on 15/11/8.
 */
public class FutureWeatherFragment2 extends Fragment {
    private ImageView weatherImg1;
    private TextView week1;
    private TextView temperature1;
    private ImageView weatherImg2;
    private TextView week2;
    private TextView temperature2;
    private ImageView weatherImg3;
    private TextView week3;
    private TextView temperature3;
    public static final String ARGUMENT = "future";

    public static FutureWeatherFragment2 newInstance(String argument){
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);
        FutureWeatherFragment2 futureWeatherFragment2 = new FutureWeatherFragment2();
        futureWeatherFragment2.setArguments(bundle);
        return futureWeatherFragment2;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_future, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();

    }

    private void initView(){
        weatherImg1 = (ImageView) getView().findViewById(R.id.weather_img1);
        week1 = (TextView) getView().findViewById(R.id.week_today1);
        temperature1 = (TextView) getView().findViewById(R.id.temperature1);
        weatherImg2 = (ImageView) getView().findViewById(R.id.weather_img2);
        week2 = (TextView) getView().findViewById(R.id.week_today2);
        temperature2 = (TextView) getView().findViewById(R.id.temperature2);
        weatherImg3 = (ImageView) getView().findViewById(R.id.weather_img3);
        week3 = (TextView) getView().findViewById(R.id.week_today3);
        temperature3 = (TextView) getView().findViewById(R.id.temperature3);
    }
    private void initData(){
        String data = getArguments().getString(ARGUMENT);
        Gson gson = new Gson();
        Future future = gson.fromJson(data, new TypeToken<Future>() {
        }.getType());
        weatherImg1.setImageResource(Drawable.getWeatherDrawable(future.result.get(3).weather));
        week1.setText(future.result.get(3).week);
        temperature1.setText(future.result.get(3).temperature);
        weatherImg2.setImageResource(Drawable.getWeatherDrawable(future.result.get(4).weather));
        week2.setText(future.result.get(4).week);
        temperature2.setText(future.result.get(4).temperature);
        weatherImg3.setImageResource(Drawable.getWeatherDrawable(future.result.get(5).weather));
        week3.setText(future.result.get(5).week);
        temperature3.setText(future.result.get(5).temperature);
    }
}
