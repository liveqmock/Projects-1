/**
 * 
 */
package com.ltkj.highway.highwaylist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

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
 * @version 创建时间：2014-10-16 上午9:23:57
 */

public class HighWayListAdapter extends BaseAdapter {

	private List<Map<String,Object>> staionList = null;
	private LayoutInflater mInflater;
	private Context mContext;
	
	private List<Map<String,Object>> restList = null; //该路段所有服务区信息
	private List<String> restNum = null; //只包含该路段服务区的startID
	private List<Map<String,Object>> accidentList = null;
	private List<Map<String,Object>> fixList = null;
	private List<String> speedList = null;
	
	private String roadID,roadName;
	private boolean initServiceFlag = false; //更新了服务区信息之后，初始化一次
	private ViewHolder holder;
	
	/**
	 * 构造函数
	 * 
	 * c:上下午
	 * stationList:站列表
	 * roadId:本高速路ID
	 * road:本高速路名
	 * */
	public HighWayListAdapter(Context c,List<Map<String,Object>> stationList,String roadId,String road) {
		mInflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.staionList = stationList;
		roadID = roadId;
		roadName = road;
		mContext = c;
		
	}
	
	/**
	 * 初始化服务数据
	 * */
	private void initServicData(){
		initServiceFlag = true;
		if(restList==null) return;
		try{
			//一次性请求，获得服务区的startID
			restNum = new ArrayList<String>();
			for(int i=0; i<restList.size(); i++){
				JSONObject rest = new JSONObject(restList.get(i));
				JSONArray start = new JSONArray(rest.get("StartID").toString());
				restNum.add(start.get(1).toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(staionList.size()>0)
			return staionList.size();
		else
			return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 * 点击事件中调用
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
	/**
	 * 不可以点击
	 * */
	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView != null){
			holder = (ViewHolder) convertView.getTag();
			//复用时，把之前cell的数据先清空
			holder.detailLayout.setVisibility(View.VISIBLE);
			holder.downServiceBtn.setVisibility(View.INVISIBLE);
		    holder.upServiceBtn.setVisibility(View.INVISIBLE);
		    holder.downAccidentBtn.setVisibility(View.INVISIBLE);
		    holder.upAccidentBtn.setVisibility(View.INVISIBLE);
		    holder.upFixBtn.setVisibility(View.INVISIBLE);
		    holder.downFixBtn.setVisibility(View.INVISIBLE);
		}else{
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_highwayinforoad, null);
			
			holder.stationName = (TextView) convertView.findViewById(R.id.stationName);
			holder.downAccidentBtn = (Button) convertView.findViewById(R.id.downAccident);
			holder.downFixBtn = (Button) convertView.findViewById(R.id.downFix);
			holder.downServiceBtn = (Button) convertView.findViewById(R.id.downservice);
			holder.upAccidentBtn = (Button) convertView.findViewById(R.id.upAccident);
			holder.upFixBtn = (Button) convertView.findViewById(R.id.upFix);
			holder.upServiceBtn = (Button) convertView.findViewById(R.id.upservice);
			
			holder.downSpeedTxt = (TextView) convertView.findViewById(R.id.downSpeed);
			holder.upSpeedTxt = (TextView) convertView.findViewById(R.id.upSpeed);
			holder.downLayout = (LinearLayout) convertView.findViewById(R.id.downLayout);
			holder.upLayout = (LinearLayout) convertView.findViewById(R.id.upLayout);
			holder.downKM = (TextView) convertView.findViewById(R.id.downkm);
			holder.upKM = (TextView) convertView.findViewById(R.id.upkm);
			
			holder.detailLayout = (LinearLayout) convertView.findViewById(R.id.detailLayout);
			convertView.setTag(holder);
		}
		
		if(position == staionList.size()-1) //最后一个只显示站名
		{	
			holder.detailLayout.setVisibility(View.INVISIBLE);
			holder.stationName.setText(staionList.get(position).get("stationname").toString());
		}
		else
		{	
			String startName = staionList.get(position).get("stationname").toString();
			String startID = staionList.get(position).get("stationid").toString();
			String endName,endID;
			if(position==staionList.size()-1){
				endName = staionList.get(position).get("stationname").toString();
				endID = staionList.get(position).get("stationid").toString();
			}else{
				endName = staionList.get(position+1).get("stationname").toString();
				endID = staionList.get(position+1).get("stationid").toString();
			}
			
			//收费站名
			holder.stationName.setText(staionList.get(position).get("stationname").toString());
			
			//服务区图标
			if(restList != null){ 
				if(!initServiceFlag) //没初始化，则初始化（当服务区信息传过来时会直接刷新表视图，因此只能放在这里）
					initServicData();
				for(int i=0; i<restNum.size(); i++){
					if(restNum.get(i).equals(staionList.get(position).get("stationid")) ){
						Map<String,Object> map = restList.get(i);
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
					    //设置tag
				        holder.downServiceBtn.setTag(map);
				        holder.upServiceBtn.setTag(map);
						//设置点击事件
				        holder.downServiceBtn.setOnClickListener(new IconClickListenner());
				        holder.upServiceBtn.setOnClickListener(new IconClickListenner());
						//设置图标可见
				        holder.downServiceBtn.setVisibility(View.VISIBLE);
				        holder.upServiceBtn.setVisibility(View.VISIBLE);
					}
				}	
			}
			//交通事故图标
			if(accidentList != null ){ //说明外部类请求交通事故已返回
				Map<String,Object>map = null;
				if((map = CommonFun.searchAccidentFixIconAryByName(accidentList, startName, endName, true))!=null){
					map.put("title", "accident");
					holder.downAccidentBtn.setTag(map);
					holder.downAccidentBtn.setOnClickListener(new IconClickListenner());
					holder.downAccidentBtn.setVisibility(View.VISIBLE);
				}
				if((map = CommonFun.searchAccidentFixIconAryByName(accidentList, endName, startName, true))!=null){
					map.put("title", "accident");
					holder.upAccidentBtn.setTag(map);
					holder.upAccidentBtn.setOnClickListener(new IconClickListenner());
					holder.upAccidentBtn.setVisibility(View.VISIBLE);
				}
				
			}
			//道路施工图标
			if(fixList != null ){ //说明外部类请求交通事故已返回
				Map<String,Object>map = null;
				if((map = CommonFun.searchAccidentFixIconAryByName(fixList, startName, endName, true))!=null){
					map.put("title", "fix");
					holder.downFixBtn.setTag(map);
					holder.downFixBtn.setOnClickListener(new IconClickListenner());
					holder.downFixBtn.setVisibility(View.VISIBLE);
				}
				if((map = CommonFun.searchAccidentFixIconAryByName(fixList, endName, startName, true))!=null){
					map.put("title", "fix");
					holder.upFixBtn.setTag(map);
					holder.upFixBtn.setOnClickListener(new IconClickListenner());
					holder.upFixBtn.setVisibility(View.VISIBLE);
				}
				
			}
			//速度
			if(speedList!=null){
				String downSpeed = CommonFun.searchSpeed(speedList, startID, endID);
				String upSpeed = CommonFun.searchSpeed(speedList, endID, startID);
				holder.downSpeedTxt.setText(downSpeed);
				holder.upSpeedTxt.setText(upSpeed);
				//改变图片颜色
				if(CommonFun.SpeedFast(downSpeed)){ //顺畅（默认）
				}else if(CommonFun.SpeedSlow(downSpeed)){ //缓慢（红）
					holder.downLayout.setBackgroundResource(R.drawable.road_red_down);
					holder.downSpeedTxt.setTextColor(Color.parseColor("#ff7862"));
					holder.downKM.setTextColor(Color.parseColor("#ff7862"));
				}else{ //一般（黄）
					holder.downLayout.setBackgroundResource(R.drawable.road_yellow_down);
					holder.downSpeedTxt.setTextColor(Color.parseColor("#fec976"));
					holder.downKM.setTextColor(Color.parseColor("#fec976"));
				}
				if(CommonFun.SpeedFast(upSpeed)){ 
				}else if(CommonFun.SpeedSlow(downSpeed)){ 
					holder.upLayout.setBackgroundResource(R.drawable.road_red_up);
					holder.upSpeedTxt.setTextColor(Color.parseColor("#ff7862"));
					holder.upKM.setTextColor(Color.parseColor("#ff7862"));
				}else{ 
					holder.upLayout.setBackgroundResource(R.drawable.road_yellow_up);
					holder.upSpeedTxt.setTextColor(Color.parseColor("#fec976"));
					holder.upKM.setTextColor(Color.parseColor("#fec976"));
				}	

			}
		}
		
		return convertView;
	}
	
	/**
	 * 图标点击事件
	 * */
	class IconClickListenner implements OnClickListener{

		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View view) {
			Button btn = (Button) view;
			Map<String,Object> map = (Map<String, Object>) btn.getTag();
			if(map.get("title").equals("service")){ //服务区
				Intent intent = new Intent(mContext,ServiceActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("serviceData", (Serializable) map);
				bundle.putString("roadName", roadName);
				intent.putExtras(bundle);
				mContext.startActivity(intent);
			}else if(map.get("title").equals("accident") || map.get("title").equals("fix")){
				Intent intent = new Intent(mContext,NewsInfoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("newsData", (Serializable) map);
				intent.putExtras(bundle);
				mContext.startActivity(intent);
			}
			
		}
		
	}
	
	
	/**
	 * 交通事故、道路施工、站速度setter方法
	 * 用来给外部调用更新数据
	 * */
	public void setAccidentList(List<Map<String, Object>> accidentList) {
		this.accidentList = accidentList;
	}
	public void setFixList(List<Map<String, Object>> fixList) {
		this.fixList = fixList;
	}
	public void setSpeedList(List<String> speedList) {
		this.speedList = speedList;
	}
	public void setRestList(List<Map<String, Object>> restList) {
		this.restList = restList;
	}
	
	/**
	 * Holder类
	 * */
	private class ViewHolder{
		TextView stationName;
		LinearLayout detailLayout;
		
		Button downServiceBtn;
		Button downAccidentBtn;
		Button downFixBtn;
		
		Button upServiceBtn;
		Button upAccidentBtn;
		Button upFixBtn;
		
		TextView downSpeedTxt;
		TextView upSpeedTxt;
		LinearLayout downLayout;
		LinearLayout upLayout; 
		TextView downKM; 
		TextView upKM; 
	}

}
