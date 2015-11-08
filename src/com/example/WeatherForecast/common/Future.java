package com.example.WeatherForecast.common;

import java.util.List;

/**
 * Created by James on 15/11/8.
 */
public class Future {
    public String success;
    public List<Result> result;
    public static class Result{
        public String weaid;
        public String days;
        public String week;
        public String cityno;
        public String citynm;
        public String cityid;
        public String temperature;
        public String humidity;
        public String weather;
        public String weather_icon;
        public String weather_icon1;
        public String wind;
        public String winp;
        public String temp_high;
        public String temp_low;
        public String humi_high;
        public String humi_low;
        public String weatid;
        public String weatid1;
        public String windid;
        public String winpid;
    }
}
