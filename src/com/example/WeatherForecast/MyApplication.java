package com.example.WeatherForecast;

import android.app.Application;
import android.os.Environment;
import com.example.WeatherForecast.common.City;
import com.example.WeatherForecast.db.DBManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 15/10/20.
 */
public class MyApplication extends Application {

    private DBManager dbManager;
    private static List<City> mList;
    public static String curCityCode;

    @Override
    public void onCreate() {
        super.onCreate();
        openDBManager();
        dbManager = new DBManager(this);
        initCityList();
    }

    private void openDBManager() {
        String dir = "/data" + Environment.getDataDirectory().getAbsolutePath()
                + File.separator + getPackageName()
                + File.separator + "databases";
        File dirfile = new File(dir);
        if (!dirfile.exists())
            dirfile.mkdir();
        String path = dir + File.separator + DBManager.CITY_DB_NAME;
        File dbfile = new File(path);
        if (!dbfile.exists()) {
            try {
                InputStream is = getAssets().open("city.db");
                FileOutputStream fos = new FileOutputStream(dbfile);
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    fos.flush();
                }
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initCityList() {
        mList = new ArrayList<City>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mList = dbManager.query();
            }
        }).start();
    }

    public static List<City> getmList() {
        return mList;
    }

    @Override
    public void onTerminate() {
        dbManager.closeDB();
        super.onTerminate();
    }
}
