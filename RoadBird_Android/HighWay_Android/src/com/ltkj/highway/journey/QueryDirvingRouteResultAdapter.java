/**
 * 
 */
package com.ltkj.highway.journey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ltkj.highway.R;

/**
 * @author wanggp
 * 
 *         2014年10月27日 上午11:16:41
 * 
 */
public class QueryDirvingRouteResultAdapter extends BaseAdapter {

//	private Context mContext;
	private LayoutInflater mInflater;
	private ViewHolder holder;

	private JSONArray planArray;

	public QueryDirvingRouteResultAdapter(Context c, JSONArray planArray) {
//		mContext = c;
		mInflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.planArray = planArray;

	}

	@Override
	public int getCount() {
		if (planArray != null)
			return planArray.length();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		if (planArray != null && planArray.length() > position) {
			JSONObject jsonObject = null;
			try {
				jsonObject = (JSONObject) planArray.get(position);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return jsonObject;
		} else
			return null;
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
			convertView = mInflater.inflate(
					R.layout.item_querydirvingrouteresult, null);
			holder = new ViewHolder();
			holder.titleTextView = (TextView) convertView
					.findViewById(R.id.typeTextView);
			holder.moneyTextView = (TextView) convertView
					.findViewById(R.id.moneyTextView);
			holder.timeTextView = (TextView) convertView
					.findViewById(R.id.timeTextView);
			holder.kmTextView = (TextView) convertView
					.findViewById(R.id.kmTextView);
			convertView.setTag(holder);
		}

		JSONObject jsonObject;
		int type = 1;
		String totalFee = "";
		String totalDistance = "";
		String totalTime = "";
		try {
			jsonObject = planArray.getJSONObject(position);
			type = jsonObject.getInt("type");
			totalFee = jsonObject.getString("totalFee");
			totalDistance = jsonObject.getString("totalDistance");
			totalTime = jsonObject.getString("totalTime");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String title = "";

		if (type == 1) {
			title = "最短距离";
		} else if (type == 2) {
			title = "最少收费";
		} else {
			title = "最短时间";
		}

		holder.titleTextView.setText(title);
		holder.timeTextView.setText(totalTime + "分钟");
		holder.kmTextView.setText(totalDistance + "公里");
		holder.moneyTextView.setText(totalFee + "元");

		return convertView;
	}

	private class ViewHolder {
		TextView titleTextView;
		TextView moneyTextView;
		TextView timeTextView;
		TextView kmTextView;

	}

}
