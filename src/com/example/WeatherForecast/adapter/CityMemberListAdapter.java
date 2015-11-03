package com.example.WeatherForecast.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.WeatherForecast.R;
import com.example.WeatherForecast.common.City;

import java.util.List;

public class CityMemberListAdapter extends BaseAdapter implements SectionIndexer {

	private LayoutInflater inflater;

	private Activity mActivity;

	private List<City> list;

	public CityMemberListAdapter(Activity mActivity, List<City> list) {
		this.mActivity = mActivity;
		this.list = list;
	}

	public void updateListView(List<City> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.items_city_list, null);
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.city_item_text);
			holder.catalog = (TextView) convertView.findViewById(R.id.city_item_catalog);
			holder.line = (TextView) convertView.findViewById(R.id.city_item_line);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final City city = list.get(position);

		if (city != null) {
			// 根据position获取分类的首字母的Char ascii值
			int section = getSectionForPosition(position);
			// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
			if (position == getPositionForSection(section)) {
				holder.catalog.setVisibility(View.VISIBLE);
				holder.catalog.setText(city.getFirstpy());
				holder.line.setVisibility(View.VISIBLE);
			} else {
				holder.catalog.setVisibility(View.GONE);
				holder.line.setVisibility(View.GONE);
			}
			holder.text.setText(city.getCity());
		}
		return convertView;
	}

	class ViewHolder {
		TextView catalog;
		TextView text;
		TextView line;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getFirstpy().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getFirstpy();
			char firstChar = sortStr.charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public Object[] getSections() {
		return null;
	}

}
