package com.example.WeatherForecast.common;

import android.test.RenamingDelegatingContext;
import com.example.WeatherForecast.R;

/**
 * Created by James on 15/10/13.
 */
public class Drawable {
    public static int getPMDrawable(String drawable){
        int aqi = Integer.parseInt(drawable);
        int res = 0;
        if(aqi <= 50){
            res = R.drawable.biz_plugin_weather_0_50;
        }else if(aqi <=100){
            res = R.drawable.biz_plugin_weather_51_100;
        }else if(aqi <=150){
            res = R.drawable.biz_plugin_weather_101_150;
        }else if(aqi <= 200){
            res = R.drawable.biz_plugin_weather_151_200;
        }else if(aqi <= 300){
            res = R.drawable.biz_plugin_weather_201_300;
        }else{
            res = R.drawable.biz_plugin_weather_greater_300;
        }
        return res;
    }
    public static int getWeatherDrawable(String drawable){
        int res = 0;
        switch(drawable){
            case "晴":
                res = R.drawable.biz_plugin_weather_qing;break;
            case "多云":
                res = R.drawable.biz_plugin_weather_duoyun;break;
            case "阴":
                res = R.drawable.biz_plugin_weather_yin;break;
            case "阵雨":
                res = R.drawable.biz_plugin_weather_zhenyu;break;
            case "雷阵雨":
                res = R.drawable.biz_plugin_weather_leizhenyu;break;
            case "雷阵雨有冰雹":
                res = R.drawable.biz_plugin_weather_leizhenyubingbao;break;
            case "雨夹雪":
                res = R.drawable.biz_plugin_weather_yujiaxue;break;
            case "小雨":
                res = R.drawable.biz_plugin_weather_xiaoyu;break;
            case "中雨":
                res = R.drawable.biz_plugin_weather_zhongyu;break;
            case "大雨":
                res = R.drawable.biz_plugin_weather_dayu;break;
            case "大暴雨":
                res = R.drawable.biz_plugin_weather_dabaoyu;break;
            case "特大暴雨":
                res = R.drawable.biz_plugin_weather_tedabaoyu;break;
            case "阵雪":
                res = R.drawable.biz_plugin_weather_zhenxue;break;
            case "小雪":
                res = R.drawable.biz_plugin_weather_xiaoxue;break;
            case "中雪":
                res = R.drawable.biz_plugin_weather_zhongxue;break;
            case "大雪":
                res = R.drawable.biz_plugin_weather_daxue;break;
            case "暴雪":
                res = R.drawable.biz_plugin_weather_baoxue;break;
            case "雾":
                res = R.drawable.biz_plugin_weather_wu;break;
            case "冻雨":
                res = R.drawable.biz_plugin_weather_leizhenyubingbao;break;
            case "沙尘暴":
                res = R.drawable.biz_plugin_weather_shachenbao;break;
            case "小雨-中雨":
                res = R.drawable.biz_plugin_weather_xiaoyu;break;
            case "中雨-大雨":
                res = R.drawable.biz_plugin_weather_zhongyu;break;
            case "大雨-暴雨":
                res = R.drawable.biz_plugin_weather_dayu;break;
            case "暴雨-大暴雨":
                res = R.drawable.biz_plugin_weather_baoyu;break;
            case "大暴雨-特大暴雨":
                res = R.drawable.biz_plugin_weather_dabaoyu;break;
            case "小雪-中雪":
                res = R.drawable.biz_plugin_weather_xiaoxue;break;
            case "中雪-大雪":
                res = R.drawable.biz_plugin_weather_zhongxue;break;
            case "大雪-暴雪":
                res = R.drawable.biz_plugin_weather_daxue;break;
            case "沙尘":
                res = R.drawable.biz_plugin_weather_shachenbao;break;
            case "扬沙":
                res = R.drawable.biz_plugin_weather_shachenbao;break;
            case "强沙尘暴":
                res = R.drawable.biz_plugin_weather_shachenbao;break;
            case "霾":
                res = R.drawable.biz_plugin_weather_wu;break;
        }
        return res;
    }
}
