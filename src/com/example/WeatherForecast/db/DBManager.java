package com.example.WeatherForecast.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.WeatherForecast.common.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 15/10/19.
 */
public class DBManager {
    public static final String CITY_DB_NAME = "city.db";
    private String table = "city";
    private SQLiteDatabase db;

    public DBManager(Context context) {
        db = context.openOrCreateDatabase(CITY_DB_NAME, Context.MODE_PRIVATE, null);
    }

    public List<City> query() {
        List<City> cities = new ArrayList<City>();
        Cursor c = db.rawQuery("select * from " + table, null);
        while (c.moveToNext()) {
            City city = new City(c.getString(c.getColumnIndex("province")), c.getString(c.getColumnIndex("city")),
                    c.getString(c.getColumnIndex("number")), c.getString(c.getColumnIndex("allpy")),
                    c.getString(c.getColumnIndex("allfirstpy")), c.getString(c.getColumnIndex("firstpy")));
            cities.add(city);
        }
        c.close();
        return cities;
    }

    public void closeDB() {
        db.close();
    }
}
