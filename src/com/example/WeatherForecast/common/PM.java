package com.example.WeatherForecast.common;

/**
 * Created by James on 15/10/13.
 */
public class PM {
    public String success;
    public Result result;
    public static class Result{
        public String weaid;
        public String cityno;
        public String citynm;
        public String cityid;
        public String aqi;       //aqi值
        public String aqi_scope; //指数范围
        public String aqi_levid; //级别编号
        public String aqi_levnm; //级别
        public String aqi_remark;//注意事项
    }
}
