package com.example.WeatherForecast.util;

/**
 * Created by James on 15/10/9.
 */
public final class Conf {
    private static final String appkey = "15755";
    private static final String secret = "0ddf5fc1bd2224cee24dc171dfad01c9";
    private static final String sign = "ddf9929fa197d8dd6a5c648a99ebe748";
    private static final String prefix = "http://api.k780.com:88/?appkey=" + appkey + "&sign="
            + sign + "&format=json&";
    public static final String CityListURL = prefix + "app=weather.city";
    public static final String FutureURL = prefix + "app=weather.future&weaid=";
    public static final String TodayURL = prefix + "app=weather.today&weaid=";
    public static final String WeatherTypeURL = prefix + "app=weather.wtype";
    public static final String HistoryURL = prefix + "app=weather.history&date=2015-07-20&weaid=";
    public static final String PM25URL = prefix + "app=weather.pm25&weaid=";
    public static final String ENGLISH = "en";
}
