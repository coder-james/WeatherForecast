package com.example.WeatherForecast.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by James on 15/11/4.
 */
public class MyPagerAdapter extends PagerAdapter {
    private List<View> views;

    public MyPagerAdapter(List<View> views){
        this.views = views;
    }
    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}
