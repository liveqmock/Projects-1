package com.ltkj.highway.highwaylist;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ltkj.highway.R;
import com.ltkj.highway.common.NetConnetion;
import com.ltkj.highway.utils.FileUtil;
import com.ltkj.highway.utils.JsonUtil;
import com.ltkj.highway.widget.BaseActivity;

@SuppressLint("NewApi") public class HighWayInfoActivity extends BaseActivity{

	@ViewInject(id = R.id.common_header_bar_left_item, click = "goBack")
	private Button returnButton;
	@ViewInject(id = R.id.common_header_bar_title)
	private TextView titleTextView;
	@ViewInject(id = R.id.common_header_bar_right_item, click = "goMap")
	private Button mapButton;
	@ViewInject(id = R.id.list)
	private ListView listView;
	
	private String roadName;
	private String roadID;
	private List<Map<String,Object>> stationList = new ArrayList<Map<String,Object>>();
	private List<Map<String,Object>> restList = new ArrayList<Map<String,Object>>();
	private List<Map<String,Object>> accidentList = new ArrayList<Map<String,Object>>();
	private List<Map<String,Object>> fixList = new ArrayList<Map<String,Object>>();
	private List<String> speedList = new ArrayList<String>();
	private HighWayListAdapter myAdapter;
	private Handler handler = new Handler();

	@Override
	public void goBack(View view) {
		// TODO Auto-generated method stub
		super.goBack(view);
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_highwayinfo);
		
		//获取从Intent中传来的值
		Bundle data = getIntent().getExtras();
		roadName = data.getString("roadName");
		roadID = data.getString("roadID");
		stationList = (List<Map<String, Object>>) data.getSerializable("station");
	
		//设置表视图
		titleTextView.setText(roadName);
		mapButton.setBackgroundResource(R.drawable.map);
		mapButton.setVisibility(View.VISIBLE);
		
		//获取数据
		initServicData();
		queryAccident();
		queryFix();
		querySpeed();
		
		myAdapter = new HighWayListAdapter(this,stationList,roadID,roadName);
		listView.setAdapter(myAdapter);
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		stationList.clear();
		//异步请求的数据，可能退出时还没返回数据
		
		restList.clear();
		accidentList.clear();
		fixList.clear();
		
		fixList=null;
		accidentList=null;
		restList=null;
		stationList=null;
		System.gc();
		super.onDestroy();
	}

	
	/**
	 * 地图模式
	 * */
	public void goMap(View view){
		Intent intent = new Intent(HighWayInfoActivity.this,HighWayListAtMapActivity.class);
		Bundle data = new Bundle();
		data.putSerializable("data", (Serializable) stationList);
		data.putSerializable("service", (Serializable) restList);
		data.putSerializable("accident",(Serializable) accidentList);
		data.putSerializable("fix",(Serializable) fixList);
		data.putSerializable("speed", (Serializable) speedList);
		data.putString("roadName", roadName);
		data.putString("roadID",roadID);
		intent.putExtras(data);
		startActivity(intent);
	}

	/**
	 * 初始化服务区数据
	 * */
	private void initServicData(){
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				JSONObject serviceObj;
				try {
					serviceObj = new JSONObject( FileUtil.getFromAssets(HighWayInfoActivity.this, "data/serviceArea.json") );
					if(serviceObj.get(roadID) == null){ //该高速路没有服务区
						return;
					}else{
						restList = JsonUtil.getList(JsonUtil.getMap(serviceObj.get(roadID).toString()).get("RESTS").toString());
						
					}
					serviceObj = null;
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				handler.post(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						myAdapter.setRestList(restList);
						myAdapter.notifyDataSetChanged();
					}
					
				});
			}
			
		}).start();
		
	}
	
	/**
	 * 查询附近的交通事故
	 * */
	public void queryAccident(){
		AjaxParams params = new AjaxParams();
		params.put("pageSize", "5");
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh.mm.ss");     
		String date = sDateFormat.format(new java.util.Date()); 
		params.put("queryTime", date);
		params.put("type", "1");
		params.put("road",roadID);
		NetConnetion dao = new NetConnetion();
		dao.getTrafficinfo(params, new AjaxCallBack<Object>(){

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				
				try {
					JSONObject json = new JSONObject(t.toString());
					int responseCode = JsonUtil.getJSONIntegerValue(json, "responseCode");
					if(responseCode==0)
					{
						JSONArray dataArray = json.getJSONArray("trafficInfo");
						//将JSONArray转换成List
						accidentList = new ArrayList<Map<String, Object>>();
						accidentList = JsonUtil.getList(dataArray.toString());
						//刷新表视图
						if(accidentList.size()!=0){
							myAdapter.setAccidentList(accidentList);
							myAdapter.notifyDataSetChanged();
						}
						
					}else
					{
						String errorMessage = null;
						if(responseCode==1)
						{
							errorMessage="获取数据失败";
						}
						else if(responseCode==5)
						{
							errorMessage="无权限访问！";
						}
						else if(responseCode==-1)
						{
							errorMessage="连接服务器失败！";
						}
						Toast toast = Toast.makeText(HighWayInfoActivity.this, errorMessage, Toast.LENGTH_LONG);
						toast.show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				Toast toast = Toast.makeText(HighWayInfoActivity.this, "网络请求失败", Toast.LENGTH_LONG);
				toast.show();
			}
			
		});
	}
	
	/**
	 * 查询附近的道路施工
	 * */
	public void queryFix(){
		AjaxParams params = new AjaxParams();
		params.put("pageSize", "5");
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh.mm.ss");     
		String date = sDateFormat.format(new java.util.Date()); 
		params.put("queryTime", date);
		params.put("type", "2");
		params.put("road",roadID);
		NetConnetion dao = new NetConnetion();
		dao.getTrafficinfo(params, new AjaxCallBack<Object>(){

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				
				try {
					JSONObject json = new JSONObject(t.toString());
					int responseCode = JsonUtil.getJSONIntegerValue(json, "responseCode");
					if(responseCode==0)
					{
						JSONArray dataArray = json.getJSONArray("trafficInfo");
						//将JSONArray转换成List
						fixList = new ArrayList<Map<String, Object>>();
						fixList = JsonUtil.getList(dataArray.toString());
						//刷新表视图
						if(fixList.size()!=0){
							myAdapter.setFixList(fixList);
							myAdapter.notifyDataSetChanged();
						}
					}else
					{
						String errorMessage = null;
						if(responseCode==1)
						{
							errorMessage="获取数据失败";
						}
						else if(responseCode==5)
						{
							errorMessage="无权限访问！";
						}
						else if(responseCode==-1)
						{
							errorMessage="连接服务器失败！";
						}
						Toast toast = Toast.makeText(HighWayInfoActivity.this, errorMessage, Toast.LENGTH_LONG);
						toast.show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				Toast toast = Toast.makeText(HighWayInfoActivity.this, "网络请求失败", Toast.LENGTH_LONG);
				toast.show();
			}
			
		});
	}
	
	/**
	 * 请求速度
	 * */
	public void querySpeed(){
		AjaxParams params = new AjaxParams();
		params.put("road",roadID);
		NetConnetion dao = new NetConnetion();
		dao.getRoadSpeed(params, new AjaxCallBack<Object>(){

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				try {
					JSONObject json = new JSONObject(t.toString());
					JSONArray speedAry = json.getJSONArray("roadSpeed").getJSONObject(0).getJSONArray("speed");
					for(int i=0; i<speedAry.length(); i++)
						speedList.add(speedAry.getString(i));
					myAdapter.setSpeedList(speedList);
					myAdapter.notifyDataSetChanged();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
			}
			
		});
	}
}
