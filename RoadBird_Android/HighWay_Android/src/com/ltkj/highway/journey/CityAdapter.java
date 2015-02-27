/**
 * 
 */
package com.ltkj.highway.journey;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ltkj.highway.R;

/**
 * @author wanggp
 * 
 *         2014年10月20日 下午3:22:49
 * 
 */
public class CityAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private ViewHolder holder;
	private List<String> dataList;
	private int clickTemp = -1;

	public CityAdapter(Context c, List<String> list) {
		mContext = c;
		mInflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dataList = list;
	}
	

	// 标识选择的Item
	public void setSeclection(int position) {
		clickTemp = position;
	}
	
	


	public void setDataList(List<String> dataList) {
		this.dataList = dataList;
	}


	@Override
	public int getCount() {
		if (dataList != null)
			return dataList.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = mInflater.inflate(R.layout.item_city, null);
			holder = new ViewHolder();
			holder.selCityImageView = (ImageView) convertView
					.findViewById(R.id.selCityImageView);
			holder.cityNameTextView = (TextView) convertView
					.findViewById(R.id.cityNameTextView);
			convertView.setTag(holder);
		}

		if (position == 0)
			holder.selCityImageView.setVisibility(View.VISIBLE);
		else
			holder.selCityImageView.setVisibility(View.GONE);

		// 点击时改变背景色
		if (clickTemp == position) {
			convertView.setBackgroundResource(R.color.headerbar_bgcolor);
		} else {
			convertView.setBackgroundResource(R.color.white);
		}

		String city = dataList.get(position);
		holder.cityNameTextView.setText(city);
		// 最后一个item，绿色字体显示
		if (position == dataList.size() - 1)

			holder.cityNameTextView.setTextColor(mContext.getResources()
					.getColor(R.color.headerbar_font));
		else
			holder.cityNameTextView.setTextColor(mContext.getResources()
					.getColor(R.color.black));

		return convertView;
	}

	private class ViewHolder {
		ImageView selCityImageView;
		TextView cityNameTextView;

	}

}
