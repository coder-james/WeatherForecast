package com.example.WeatherForecast;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.example.WeatherForecast.adapter.CityMemberListAdapter;
import com.example.WeatherForecast.common.City;
import com.example.WeatherForecast.widget.CityComparator;
import com.example.WeatherForecast.widget.CustomEditText;
import com.example.WeatherForecast.widget.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by James on 15/10/19.
 */
public class CityListActivity extends Activity
        implements SideBar.OnTouchingLetterChangedListener, TextWatcher, AdapterView.OnItemClickListener {
    private ImageView mBackBtn;
    private SideBar mSideBar;
    private TextView mDialog;
    private ListView mListView;
    private CustomEditText mSearchInput;
    private CityComparator cityComparator;
    private List<City> sortDataList = new ArrayList<City>();
    private List<City> filterList;
    private CityMemberListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.citymanager);

        initView();
        initData();
    }

    private void initView() {
        mBackBtn = (ImageView) this.findViewById(R.id.title_back);
        mListView = (ListView) this.findViewById(R.id.cities_member);
        mSideBar = (SideBar) this.findViewById(R.id.cities_sidrbar);
        mDialog = (TextView) this.findViewById(R.id.cities_dialog);
        mSearchInput = (CustomEditText) this.findViewById(R.id.cities_search_input);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mSideBar.setTextView(mDialog);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        cityComparator = new CityComparator();
        sortDataList = MyApplication.getmList();
        Collections.sort(sortDataList, cityComparator);
        mAdapter = new CityMemberListAdapter(this, sortDataList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mSideBar.setOnTouchingLetterChangedListener(this);
        mSearchInput.addTextChangedListener(this);
        filterList = new ArrayList<City>(sortDataList);
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        int position = 0;
        if (mAdapter != null) {
            position = mAdapter.getPositionForSection(s.charAt(0));
        }
        if (position != -1) {
            mListView.setSelection(position);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        String filterStr = s.toString();
        if (TextUtils.isEmpty(filterStr)) {
            Collections.copy(filterList, sortDataList);
        } else {
            filterList.clear();
            for (City city : sortDataList) {
                String cityname = city.getCity();
                String allpy = city.getAllpy();
                String allfistpy = city.getAllfistpy();
                if (cityname.indexOf(filterStr) != -1 || allfistpy.indexOf(filterStr.toUpperCase()) != -1 || allpy.startsWith(filterStr.toUpperCase())) {
                    filterList.add(city);
                }
            }
        }
        Collections.sort(filterList, cityComparator);
        mAdapter.updateListView(filterList);
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        setResult(MainActivity.CITYSELECT_RESULT_CODE, getIntent().putExtra("select_city", filterList.get(position).getNumber()));
        finish();
    }
}