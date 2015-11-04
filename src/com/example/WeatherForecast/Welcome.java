package com.example.WeatherForecast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.WeatherForecast.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 15/11/4.
 */
public class Welcome extends Activity implements ViewPager.OnPageChangeListener{
    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;
    private List<View> list;
    private ImageView[] dots;
    private int[] ids = {R.id.iv1, R.id.iv2, R.id.iv3};
    private Button start;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        initView();
        initDots();
    }
    private void initView(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        list = new ArrayList<View>();
        list.add(layoutInflater.inflate(R.layout.welcome_page1, null));
        list.add(layoutInflater.inflate(R.layout.welcome_page2, null));
        list.add(layoutInflater.inflate(R.layout.welcome_page3, null));
        viewPager = (ViewPager) findViewById(R.id.pages);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(list, this);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setOnPageChangeListener(this);
        start = (Button) list.get(list.size() - 1).findViewById(R.id.guide_btn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void initDots(){
        dots = new ImageView[list.size()];
        for (int i = 0; i < list.size(); i++) {
            dots[i] = (ImageView) findViewById(ids[i]);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {}

    @Override
    public void onPageSelected(int selectpage) {
        for (int i1 = 0; i1 < ids.length; i1++) {
            if(i1 == selectpage){
                dots[i1].setImageResource(R.drawable.page_indicator_focused);
            }else{
                dots[i1].setImageResource(R.drawable.page_indicator_unfocused);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {}
}