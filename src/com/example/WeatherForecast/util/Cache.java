package com.example.WeatherForecast.util;


import android.os.Environment;
import android.util.Log;

import java.io.*;

/**
 * Created by James on 15/12/5.
 */
public class Cache {
    private static String curFileName = "weather_today.txt";
    private static String pmFileName = "weather_pm.txt";
    private static String futureFileName = "weather_future.txt";
    public static void saveWeatherCur(String data){
        saveFile(curFileName, data);
    }
    public static void saveWeatherPM(String data){
        saveFile(pmFileName, data);
    }
    public static void saveWeatherFuture(String data){
        saveFile(futureFileName, data);
    }
    private static void saveFile(String fileName, String data){
        try {
            File file = new File(Environment.getExternalStorageDirectory(), fileName);
            if (!file.exists()){
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String readWeatherCur(){
        return readFile(curFileName);
    }
    public static String readWeatherPm(){
        return readFile(pmFileName);
    }
    public static String readWeatherFuture(){
        return readFile(futureFileName);
    }
    private static String readFile(String fileName){
        String result = null;
        try {
            File file = new File(Environment.getExternalStorageDirectory(), fileName);
            FileInputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, len);
            }
            byte[] data = outputStream.toByteArray();
            result = new String(data);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
