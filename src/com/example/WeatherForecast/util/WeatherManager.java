package com.example.WeatherForecast.util;

import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by James on 15/10/31.
 */
public class WeatherManager {

    public Bundle getWeatherInfo(String curCityCode) throws IOException {
        Bundle bundle = new Bundle();
        bundle.putString("pm25", getNetInfo(Conf.PM25URL + curCityCode));
        bundle.putString("today", getNetInfo(Conf.TodayURL + curCityCode));
        return bundle;
    }

    public String getNetInfo(String url) throws IOException {
        URL infoUrl = new URL(url);
        URLConnection conn = (HttpURLConnection) infoUrl.openConnection();
        conn.setConnectTimeout(3000);
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
