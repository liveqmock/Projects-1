/**
 * 
 */
package com.ltkj.highway.highwaylist;


import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ltkj.highway.R;


/**
 * @author chenzy
 * @version 创建时间：2014-11-7 上午10:25:34
 */

public class HighWaySearchAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private ViewHolder holder;
	private List<Map<String,Object>> originalList = null;
	private List<Map<String,Object>> resultList = null;
	private boolean isSearching = false;
	
	public HighWaySearchAdapter(Context c,List<Map<String,Object>>originalList){
		mInflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.originalList = originalList;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(isSearching){
			return resultList.size();
		}
		else{
			return originalList.size();
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = mInflater.inflate(R.layout.item_highway, null);
			holder = new ViewHolder();
			holder.highWayName = (TextView) convertView
					.findViewById(R.id.highwayName);
			convertView.setTag(holder);
		}
		
		if(isSearching){
			holder.highWayName.setText(resultList.get(position).get("ROADNAME").toString());
		}else{
			holder.highWayName.setText(originalList.get(position).get("ROADNAME").toString());
		}
		
		return convertView;
	}

	class ViewHolder{
		TextView highWayName;
	}
	
	/**
	 * getter、setter方法
	 * */
	public List<Map<String, Object>> getResultList() {
		return resultList;
	}

	public void setResultList(List<Map<String, Object>> resultList) {
		this.resultList = resultList;
	}

	public boolean isSearch() {
		return isSearching;
	}

	public void setSearch(boolean isSearch) {
		this.isSearching = isSearch;
	}

}
