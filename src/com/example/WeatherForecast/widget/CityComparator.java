package com.example.WeatherForecast.widget;

import com.example.WeatherForecast.common.City;

import java.util.Comparator;


public class CityComparator implements Comparator<City> {

	@Override
	public int compare(City o1, City o2) {
		return o1.getFirstpy().compareTo(o2.getFirstpy());
	}

}
