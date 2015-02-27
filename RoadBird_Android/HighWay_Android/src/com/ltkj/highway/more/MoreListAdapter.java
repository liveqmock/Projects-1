/**
 * 
 */
package com.ltkj.highway.more;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ltkj.highway.R;

/**
 * @author wanggp
 * 
 */
public class MoreListAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private ViewHolder holder;
	private List<HashMap<String, Object>> dataList;

	public MoreListAdapter(Context c, List<HashMap<String, Object>> list) {
		mContext = c;
		mInflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dataList = list;
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = mInflater.inflate(R.layout.item_more, null);
			holder = new ViewHolder();
			holder.titleTextView = (TextView) convertView
					.findViewById(R.id.title_textView);
			convertView.setTag(holder);
		}

		HashMap<String, Object> hashMap = (HashMap<String, Object>) dataList
				.get(position);

		String title = (String) hashMap.get("title");
		holder.titleTextView.setText(title);

		int resId = Integer.parseInt(hashMap.get("img").toString());
		Resources res = mContext.getResources();
		Drawable img_left = res.getDrawable(resId);
		img_left.setBounds(0, 0, img_left.getMinimumWidth(),
				img_left.getMinimumHeight());
		holder.titleTextView.setCompoundDrawables(img_left, null, null, null);
		return convertView;
	}

	private class ViewHolder {
		// ImageView leftImageView;
		TextView titleTextView;

	}

}
