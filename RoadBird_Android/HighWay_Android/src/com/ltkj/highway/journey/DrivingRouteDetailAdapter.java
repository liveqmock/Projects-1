/**
 * 
 */
package com.ltkj.highway.journey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ltkj.highway.R;
import com.ltkj.highway.common.CommonFun;
import com.ltkj.highway.news.NewsInfoActivity;
import com.ltkj.highway.news.ServiceActivity;

/**
 * @author chenzy
 * @version 创建时间：2014-10-28 下午4:27:20
 */
public class DrivingRouteDetailAdapter extends BaseAdapter{

	private List<Map<String,Object>> stationList = null;
	private List<Map<String,Object>> accidentList = null;
	private List<Map<String,Object>> fixList = null;
	private Map<String,List<Map<String,Object>>> restMap = null;
	private List<String> speedList = null;
	//真正在途经路上的交通事故
	private List<Map<String,Object>> realAccidentList = new ArrayList<Map<String,Object>>();
	private List<Map<String,Object>> realFixList = new ArrayList<Map<String,Object>>();
	private List<Map<String,Object>> realServiceList = new ArrayList<Map<String,Object>>();
	
	private LayoutInflater mInflater;
	private Context mContext;
	private ViewHolder holder;
	
	/**
	 * 构造方法
	 * */
	public DrivingRouteDetailAdapter(Context c,List<Map<String,Object>> tollList,List<String>speed) {
		mInflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = c;
		stationList = tollList;
		speedList = speed;
	}

	/**
	 * 不可以点击
	 * */
	@Override
	public boolean isEnabled(int position) {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return stationList.size();
 
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

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView!=null){ //复用
			holder = (ViewHolder) convertView.getTag();
			//复用清空之前cell的数据
			holder.accidentBtn.setVisibility(View.INVISIBLE);
			holder.fixBtn.setVisibility(View.INVISIBLE);
			holder.serviceBtn.setVisibility(View.INVISIBLE);
			holder.detailLayout.setVisibility(View.VISIBLE);
		}else{
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_journeyinforoad, null);
			holder.stationName = (TextView) convertView.findViewById(R.id.stationName);
			holder.accidentBtn = (Button) convertView.findViewById(R.id.accidentIcon);
			holder.fixBtn = (Button) convertView.findViewById(R.id.fixIcon);
			holder.serviceBtn = (Button) convertView.findViewById(R.id.servicetIcon);
			holder.speedTxt = (TextView) convertView.findViewById(R.id.speed);
			holder.layout = (LinearLayout) convertView.findViewById(R.id.layout);
			holder.kmTxt = (TextView) convertView.findViewById(R.id.km);
			holder.detailLayout = (LinearLayout) convertView.findViewById(R.id.detailLayout);
			convertView.setTag(holder);
		}
		
		holder.stationName.setText(stationList.get(position).get("stationname").toString());
		
		if(position == stationList.size()-1){ //最后一个
			holder.detailLayout.setVisibility(View.INVISIBLE);
		}
		else{
			String startName = stationList.get(position).get("stationname").toString();
			String startID = stationList.get(position).get("stationid").toString();
			String endName,endID;
			if(position==stationList.size()-1){
				endName = stationList.get(position).get("stationname").toString();
				endID = stationList.get(position).get("stationid").toString();
			}else{
				endName = stationList.get(position+1).get("stationname").toString();
				endID = stationList.get(position+1).get("stationid").toString();
			}
			
			String roadID = stationList.get(position).get("roadid").toString();
			
			//交通事故图标
			if(accidentList != null ){ //说明外部类请求交通事故已返回
				Map<String,Object>map = null;
				if((map = CommonFun.searchAccidentFixIconAryByName(accidentList, startName, endName, false))!=null){
					realAccidentList.add(map);
					map.put("title", "accident");
					holder.accidentBtn.setTag(map);
					holder.accidentBtn.setOnClickListener(new IconClickListenner());
					holder.accidentBtn.setVisibility(View.VISIBLE);
				}
				
			}
			//道路施工图标
			if(fixList != null ){ //说明外部类请求道路施工已返回
				Map<String,Object>map = null;
				if((map = CommonFun.searchAccidentFixIconAryByName(fixList, startName, endName, false))!=null){
					realFixList.add(map);
					map.put("title", "fix");
					holder.fixBtn.setTag(map);
					holder.fixBtn.setOnClickListener(new IconClickListenner());
					holder.fixBtn.setVisibility(View.VISIBLE);
				}
				
			}
			//服务区图标
			if(restMap != null){ 
				Map<String,Object>map = null;
				if((map = CommonFun.searchServiceIconAryByID(restMap, roadID, startID, endID)) != null){
					map.remove("StartID");
					map.remove("EndID");
					map.put("title", "service");
					//把null改成"null"，不然传值的时候会出错
					 Set<String> key = map.keySet();
				        for (Iterator it = key.iterator(); it.hasNext();) {
				            String s = (String) it.next();
				            String obj = map.get(s).toString();
				            if(CommonFun.isNull(obj)){
				            	map.put(s, "null");
				            }
				        }  
			        holder.serviceBtn.setTag(map);
			        holder.serviceBtn.setOnClickListener(new IconClickListenner());
			        holder.serviceBtn.setVisibility(View.VISIBLE);
					realServiceList.add(map);
				}
			}
			//速度
			if(speedList!=null){
				String speed = speedList.get(position);
				holder.speedTxt.setText(speed);
				if(CommonFun.SpeedFast(speed)){
					
				}else if(CommonFun.SpeedSlow(speed)){
					holder.layout.setBackgroundResource(R.drawable.road_red);
					holder.speedTxt.setTextColor(Color.parseColor("#ff7862"));
					holder.kmTxt.setTextColor(Color.parseColor("#ff7862"));
				}else{
					holder.layout.setBackgroundResource(R.drawable.road_yellow);
					holder.speedTxt.setTextColor(Color.parseColor("#fec976"));
					holder.kmTxt.setTextColor(Color.parseColor("#fec976"));
				}
			}
			
		}
			
		// TODO Auto-generated method stub
		return convertView;
	}
	
	/**
	 * Icon监听类
	 * */
	class IconClickListenner implements OnClickListener{

		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View view) {
			Button btn = (Button) view;
			Map<String,Object> map = (Map<String, Object>) btn.getTag();
			if(map.get("title").equals("accident") || map.get("title").equals("fix")){
				Intent intent = new Intent(mContext,NewsInfoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("newsData", (Serializable) map);
				intent.putExtras(bundle);
				mContext.startActivity(intent);
			}
			else if(map.get("title").equals("service")){
				Intent intent = new Intent(mContext,ServiceActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("serviceData", (Serializable) map);
				bundle.putString("roadName", map.get("roadName").toString());
				intent.putExtras(bundle);
				mContext.startActivity(intent);
			}
			
		}
	
		
	}
	
	/**
	 * 交通事故、道路施工setter方法
	 * 用来给外部调用更新数据
	 * */

	public void setAccidentList(List<Map<String, Object>> accidentList) {
		this.accidentList = accidentList;
	}
	public void setFixList(List<Map<String, Object>> fixList) {
		this.fixList = fixList;
	}
	public void setRestMap(Map<String, List<Map<String, Object>>> restMap) {
		this.restMap = restMap;
	}
	
	/**
	 * 真实交通事故、道路施工、服务区getter方法
	 * 在点击地图模式的时候获取
	 * */
	public List<Map<String, Object>> getRealAccidentList() {
		return realAccidentList;
	}
	public List<Map<String, Object>> getRealFixList() {
		return realFixList;
	}
	public List<Map<String, Object>> getRealServiceList() {
		return realServiceList;
	}
	
	class ViewHolder{
		TextView stationName;
		Button accidentBtn;
		Button serviceBtn;
		Button fixBtn;
		TextView speedTxt;
		TextView kmTxt;
		LinearLayout layout;
		LinearLayout detailLayout;
	}

}
