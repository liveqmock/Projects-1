/**
 * 
 */
package com.ltkj.highway.more;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ltkj.highway.R;
import com.ltkj.highway.utils.JsonUtil;
import com.ltkj.highway.utils.PhoneCallUtil;

/**
 * @author wanggp
 * 
 *         2014年9月28日 下午3:25:13
 * 
 */
public class PhoneListAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private ViewHolder holder;
	private JSONArray jsonArray;

	public PhoneListAdapter(Context c, JSONArray jsonArray) {
		mContext = c;
		mInflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.jsonArray = jsonArray;
	}

	@Override
	public int getCount() {
		return jsonArray.length();
	}

	@Override
	public Object getItem(int position) {
		try {
			return jsonArray.get(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// 设置不可以点击
	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = mInflater.inflate(R.layout.item_phone, null);
			holder = new ViewHolder();
			holder.nameTextView = (TextView) convertView
					.findViewById(R.id.phoneNameTextView);
			holder.numberTextView = (TextView) convertView
					.findViewById(R.id.phoneNumberTextView);
			holder.linearLayout = (LinearLayout) convertView
					.findViewById(R.id.phoneLinearLayout);

			convertView.setTag(holder);
		}

		String remark = "";
		String phonenumber = "";
		JSONObject jsonObject;
		try {
			jsonObject = jsonArray.getJSONObject(position);
			remark = JsonUtil.getJSONStringValue(jsonObject, "remark");
			phonenumber = JsonUtil
					.getJSONStringValue(jsonObject, "phonenumber");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		holder.nameTextView.setText(remark);
		holder.numberTextView.setText(phonenumber);
		holder.linearLayout.setTag(position);
		holder.linearLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int tag = Integer.parseInt(v.getTag().toString());
				String titile = "";
				String number = "";
				JSONObject jsonObject;
				try {
					jsonObject = jsonArray.getJSONObject(tag);
					titile = JsonUtil.getJSONStringValue(jsonObject, "remark");
					number = JsonUtil.getJSONStringValue(jsonObject,
							"phonenumber");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				PhoneCallUtil
						.makePhoneCall((Activity) mContext, titile, number);
			}
		});

		return convertView;
	}

	private class ViewHolder {
		TextView nameTextView;
		TextView numberTextView;
		LinearLayout linearLayout;

	}

}
