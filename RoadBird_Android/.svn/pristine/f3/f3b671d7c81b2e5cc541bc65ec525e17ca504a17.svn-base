/**
 * 
 */
package com.ltkj.highway.news;

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
 * @version 创建时间：2014-10-21 下午2:24:18
 */

public class ServiceListAdapter extends BaseAdapter{

	private Context mContext;
	private Map<String,Object> serviceData = null;
	private LayoutInflater mInflater;
	private ViewHolder holder;
	private Object[] keyName = {"Adress","StakeNum","Park","Gas","Repast","Conven","Lav","Repair","Others"}; //用来决定显示的顺序
	private Object[] showName = {" ","桩号","停车位","加油站","餐饮","便利店","洗手间","汽修厂","其他"};
	
	public ServiceListAdapter(Context c,Map<String,Object> data){
		mContext = c;
		mInflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		data.remove("RestId");
		data.remove("Coor_Lon1");
		data.remove("Coor_Lon2");
		data.remove("Coor_Lat1");
		data.remove("Coor_Lat2");
		serviceData = data;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return keyName.length;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int arg0) {
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
	
	// 设置不可以点击
		@Override
		public boolean isEnabled(int position) {
			return false;
		}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		//tableViewCell复用
		if(convertView!=null){
			holder = (ViewHolder) convertView.getTag();
			holder.subText.setText("");
		}else{
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_service, null);
			holder.mainText = (TextView) convertView.findViewById(R.id.mainLabel);
			holder.subText = (TextView) convertView.findViewById(R.id.subLabel);
			convertView.setTag(holder);
		}
		if(position == 0){ //第一条
			holder.mainText.setText(serviceData.get(keyName[position]).toString());
		}else{ //其他位置
			holder.mainText.setText(showName[position].toString());
			if(serviceData.get(keyName[position]) != null && !serviceData.get(keyName[position]).equals("null"))
				holder.subText.setText(serviceData.get(keyName[position]).toString());
			else
				holder.subText.setText("无");
		}

		
		return convertView;
	}

	private class ViewHolder{
		TextView mainText;
		TextView subText;
	}
}
