/**
 * 
 */
package com.ltkj.highway;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ltkj.highway.po.QueryPathRecord;

/**
 * @author wanggp
 * 
 */
public class QueryPathRecordListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ViewHolder holder;
	private List<Object> dataList;
	private Boolean haveMore;

	public QueryPathRecordListAdapter(Context c, List<QueryPathRecord> list,
			boolean haveMore) {
		mInflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.haveMore = haveMore;
		setDataList(list);

	}

	public void setDataList(List<QueryPathRecord> list) {
		dataList = new ArrayList<Object>();
		if (list != null)
			dataList.addAll(list);

		if (dataList.size() == 0)
			dataList.add("无查询记录");
		else if (haveMore)
			dataList.add("显示更多");
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
			convertView = mInflater
					.inflate(R.layout.item_querypathrecord, null);
			holder = new ViewHolder();

			holder.titleTextView = (TextView) convertView
					.findViewById(R.id.titleTextView);
			convertView.setTag(holder);
		}

		String title = "";
		Object object = dataList.get(position);
		if (object instanceof QueryPathRecord) {
			QueryPathRecord queryPathRecord = (QueryPathRecord) object;

			title = queryPathRecord.getStartStationName() + "-"
					+ queryPathRecord.getEndStationName();
		} else {
			title = (String) object;
		}
		holder.titleTextView.setText(title);
		return convertView;
	}

	private class ViewHolder {

		TextView titleTextView;

	}

}
