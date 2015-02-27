/**
 * 
 */
package com.ltkj.highway.journey;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ltkj.highway.R;

/**
 * @author wanggp
 * 
 *         2014年10月21日 下午3:55:04
 * @param <T>
 * 
 */
public class SelStationAdapter extends ArrayAdapter<Object> {

	private Activity activity;
	private LayoutInflater mInflater;
	private List<Map<String,Object>> searchResultList;
	private List<Object> originalList;
	private boolean isSearch = false;
	private ViewHolder holder;

	/**
	 * @param context
	 * @param resource
	 */
	public SelStationAdapter(Context context, int resource, List<Object> objects) {

		super(context, resource, objects);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		activity = (Activity) context;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		
		Object object = getItem(position);
		if (object instanceof String && !isSearch)
			return false;
		else
			return true;
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(isSearch)
			return searchResultList.size();
		else
			return originalList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Object object = getItem(position);
		if ( !isSearch && object instanceof String ) {
			View view = convertView;
			view = mInflater.inflate(R.layout.item_tag_station, null);
			TextView cityTextView = (TextView) view
					.findViewById(R.id.cityTextView);
			cityTextView.setText((String) object);
			LinearLayout linearLayout = (LinearLayout) view
					.findViewById(R.id.mapLinearLayout);
			linearLayout.setTag(object);
			linearLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String city = (String) v.getTag();
					// 地图模式选择
					selStationByMap(city);

				}
			});
			return view;

		} else {

			if (convertView != null
					&& (convertView.getTag() instanceof ViewHolder)) {
				holder = (ViewHolder) convertView.getTag();
			} else {
				convertView = mInflater.inflate(R.layout.item_station, null);
				holder = new ViewHolder();
				holder.stationNameTextView = (TextView) convertView
						.findViewById(R.id.stationNameTextView);
				convertView.setTag(holder);
			}
			
			if(isSearch){
				if(position>=searchResultList.size()) return null;
				if(searchResultList!=null && searchResultList.size()>0)
					holder.stationNameTextView.setText(searchResultList.get(position).get("STATIONNAME").toString());
			}
			else
			{
				JSONObject jsonObject = (JSONObject) object;
				String stationname = "";
				try {
					stationname = jsonObject.getString("STATIONNAME");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				holder.stationNameTextView.setText(stationname);
			}
			
			return convertView;
		}
	}

	private class ViewHolder {
		TextView stationNameTextView;
	}

	/**
	 * 地图模式选择站
	 * 
	 * @param city
	 */
	private void selStationByMap(String city) {

		Intent intent = new Intent(activity, SelStationByMapActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("city", city);
		intent.putExtras(bundle);
		activity.startActivityForResult(intent, 0);

	}


	public void setSearch(boolean isSearch) {
		this.isSearch = isSearch;
	}
	public boolean isSearch() {
		return isSearch;
	}
	public void setSearchResultList(List<Map<String, Object>> searchResultList) {
		this.searchResultList = searchResultList;
	}
	public List<Map<String, Object>> getSearchResultList() {
		return searchResultList;
	}
	public void setOriginalList(List<Object> originalList) {
		this.originalList = originalList;
	}
}
