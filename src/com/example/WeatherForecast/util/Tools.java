package com.example.WeatherForecast.util;

/**
 * Created by James on 15/12/4.
 */
public class Tools {
    public static String chToEN(String chinaName){
        String result = null;
        switch (chinaName){
            case "晴": result = "fine";break;
            case "多云":
            case "阴": result = "cloudy";break;
            case "阵雨": result = "shower";break;
            case "雷阵雨": result = "thundershower";break;
            case "雷阵雨有冰雹": result = "thundershower hail";break;
            case "雨夹雪": result = "sleet";break;
            case "小雨": result = "sprinkle";break;
            case "中雨": result = "moderate rain";break;
            case "大雨": result = "heavy rain";break;
            case "暴雨": result = "rainstorm";break;
            case "大暴雨": result = "cloudburst";break;
            case "特大暴雨": result = "extraordinary rainstorm";break;
            case "阵雪": result = "snow shower";break;
            case "小雪": result = "scouther";break;
            case "中雪": result = "moderate snow";break;
            case "大雪": result = "heavy snow";break;
            case "暴雪": result = "blizzard";break;
            case "雾": result = "fog";break;
            case "冻雨": result = "ice rain";break;
            case "沙尘暴": result = "sand storm";break;
            case "小雨-中雨": result = "sprinkle-moderate";break;
            case "中雨-大雨": result = "moderate-heavyrain";break;
            case "大雨-暴雨": result = "heavy-rainstorm";break;
            case "暴雨-大暴雨": result = "rainstrom-cloudburst";break;
            case "大暴雨-特大暴雨": result = "cloudburst-extraordinary";break;
            case "小雪-中雪": result = "scouther-moderate";break;
            case "中雪-大雪": result = "moderate-heavysnow";break;
            case "大雪-暴雪": result = "heavy-blizzard";break;
            case "浮尘": result = "floating dust";break;
            case "扬沙": result = "blowing sand";break;
            case "强沙尘暴": result = "severe dust devil";break;
            case "霾": result = "haze";break;
        }
        return result;
    }

    public static String getEnWeek(String chinaName){
        String result = "";
        switch (chinaName){
            case "星期一": result = "Monday";break;
            case "星期二": result = "Tuesday";break;
            case "星期三": result = "Wednesday";break;
            case "星期四": result = "Thursday";break;
            case "星期五": result = "Friday";break;
            case "星期六": result = "Saturday";break;
            case "星期日": result = "Sunday";break;
        }
        return result;
    }

    public static String getEnWind(String chinaName, String level){
        String result = "";
        switch (chinaName){
            case "北风": result = "NWind";break;
            case "东北风": result = "ENWind";break;
            case "东风": result = "EWind";break;
            case "东南风": result = "ESWind";break;
            case "南风": result = "SWind";break;
            case "西南风": result = "WSWind";break;
            case "西风": result = "WWind";break;
            case "西北风": result = "WNWind";break;
        }
        result += " L" + level.substring(0, level.length() - 1);
        return result;
    }
}
