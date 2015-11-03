package com.example.WeatherForecast.common;

/**
 * Created by James on 15/10/13.
 */
public class Today {
    public String success;
    public Result result;
    public static class Result{
        public String weaid;
        public String days;   //日期
        public String week;   //星期
        public String cityno;
        public String citynm;    //城市/地区
        public String cityid;    //气象编号
        public String temperature;  //温度
        public String temperature_curr;  //实时温度
        public String humidity;   //湿度
        public String weather;   //天气
        public String weather_icon;   //天气图标
        public String weather_icon1;  //天气图标1
        public String wind;   //风向
        public String winp;   //风力
        public String temp_high;   //最高温度
        public String temp_low;   //最低温度
        public String temp_curr;  //实时温度
        public String humi_high;   //最高湿度
        public String humi_low;   //最低湿度
        public String weatid;    //天气ID
        public String weatid1;   //天气ID1
        public String windid;   //风向ID
        public String winpid;   //风力ID
    }
}
