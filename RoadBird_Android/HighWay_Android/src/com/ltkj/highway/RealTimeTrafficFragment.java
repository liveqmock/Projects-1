package com.ltkj.highway;


import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.ltkj.highway.common.CommonFun;
import com.ltkj.highway.common.HighwayApp;
import com.ltkj.highway.common.NetConnetion;
import com.ltkj.highway.highwaylist.HighWayActivity;
import com.ltkj.highway.news.NewsActivity;
import com.ltkj.highway.news.NewsInfoActivity;
import com.ltkj.highway.news.ServiceActivity;
import com.ltkj.highway.utils.FileUtil;
import com.ltkj.highway.utils.JsonUtil;
import com.ltkj.highway.widget.CustomToast;
import com.ltkj.highway.widget.FunMorePopupWindow;
import com.ltkj.highway.widget.IndexPopupWindow;
/**
 * 
 * @author wanggp
 * 
 */
@SuppressLint("NewApi")
public class RealTimeTrafficFragment extends Fragment implements FunMorePopupWindow.Callbacks,IndexPopupWindow.Callbacks{

	@ViewInject(id = R.id.bmapView)
	private MapView mapView;
	@ViewInject(id = R.id.imgZoomOut, click = "zoomOut")
	private ImageView imgZoomOut;
	@ViewInject(id = R.id.imgZoomIn, click = "zoomIn")
	private ImageView imgZoomIn;
	@ViewInject(id = R.id.imgLocation, click = "displayCurrLocation")
	private ImageView imgLocation;
	@ViewInject(id = R.id.imgMessage, click = "onClickMessage")
	private ImageView imgMessage;
	@ViewInject(id = R.id.imgFunMore, click = "onClickFunMore")
	private ImageView imgFunMore;
	@ViewInject(id = R.id.HighWayBtn, click = "onClickHighWay")
	private Button highWayBtn;
	
	private HighwayApp app;
	private FunMorePopupWindow funMorePop = null;
	private IndexPopupWindow index = null;
	
	private LocationClient mLocationClient; //定位
	private MyLocationListener mMyLocationListener;
	private double latitude, longitude;
	private String address = null;
	private boolean isFirstLocation = true;
	
	//显示在地图上的标注集合
	private Marker curLocationMarker = null;
	private ArrayList<Marker> tollMarkers = null;
	private ArrayList<Marker> accidentMarkers = null;
	private ArrayList<Marker> fixMarkers = null;
	private ArrayList<Marker> serviceMarkers = null;
	
	final int accidentNum = 0;
	final int fixNum = 1;
	final int tollNum = 2;
	final int serviceNum = 3;
	final int allRoadNum = 4;
	
	
	private boolean tollFlag = true;
	private boolean accidentFlag = true;
	private boolean fixFlag = true;
	private boolean serviceFlag = true;
	private boolean allRoadFlag = true;

	
	//网络请求得到的原始数据
	private ArrayList<String> nearRoadIDs = null;
	private List<PolylineOptions>polyList = null; //全省高速路径缓存
	private List<PolylineOptions>nearRoadPolyList = new ArrayList<PolylineOptions>(); //附近高速路径缓存
	private Map<String,List<String>>speedMap = null;
	
	
	//位图
	BitmapDescriptor toll_map = BitmapDescriptorFactory
			.fromResource(R.drawable.toll_map);
	BitmapDescriptor accident_map = BitmapDescriptorFactory
			.fromResource(R.drawable.accident_map);
	BitmapDescriptor fix_map = BitmapDescriptorFactory
			.fromResource(R.drawable.fix_map);
	BitmapDescriptor service_map = BitmapDescriptorFactory
			.fromResource(R.drawable.service_map);
	
	 private static Handler handler=new Handler();
	 Thread showAllRoadThread = null;
	 boolean threadStopFlag = false;
	 boolean activityChange = false; //判断是否已经跳转到别的界面，防止全省高速线程返回时出错
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_realtimetraffic,
				container, false);

		app = (HighwayApp) getActivity().getApplication();
		FinalActivity.initInjectedView(this, view);
		initBDLocationClient(); //初始化定位

		return view;
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		// 初始化控件
		initWidget();
		//监听marker点击事件
		OnMarkerClick();
	}
	
	@Override  
	   public void setUserVisibleHint(boolean isVisibleToUser) {  
	       super.setUserVisibleHint(isVisibleToUser);  
	       if (isVisibleToUser) {  
	           //相当于Fragment的onResume  
	    	   activityChange = false;
	   		   delayHandler.sendEmptyMessageDelayed(0, 100); //引导页
	   		
	       } else {  
	           //相当于Fragment的onPause  

	       }  
	   }  
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		startBDLocation(); //开启定位
		mapView.onResume();
		activityChange = false;
		curLocationMarker=null;
		displayCurrLocation(null);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mapView.onPause();
		
		//页面跳转标志，用来处理handler
		activityChange = true;
		threadStopFlag = true;
		delayHandler.removeCallbacksAndMessages(null); //防止message对handler的引用

		if(funMorePop!=null){
			funMorePop.dismiss();
			funMorePop = null;
		}
		if(index!=null){ 
			index.dismiss();
			index = null;
		}
		mapView.getMap().clear();
		stopBDLocation(); //停止定位
		//回收
		recycle();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		//位图回收
		mapView.onDestroy();
		toll_map.recycle();
		accident_map.recycle();
		fix_map.recycle();
		service_map.recycle();
		
		if(polyList!=null)
			polyList.clear(); 
		polyList = null;
		nearRoadPolyList.clear(); nearRoadPolyList=null;
		super.onDestroy();
	}
	/**
	 * 百度地图定位
	 * */
	private void initBDLocationClient() {
		mLocationClient = new LocationClient(getActivity().getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Battery_Saving);
		option.setIsNeedAddress(true);
		option.setScanSpan(30000);// 设置发起定位请求的间隔时间为30000ms＝30s
		option.setCoorType("bd09ll"); // 设置坐标类型
		mLocationClient.setLocOption(option);
		mLocationClient.start();
		mLocationClient.requestLocation();
	}

	public void stopBDLocation() {
		if (mLocationClient != null) {
			mLocationClient.stop();
		}
	}
	
	public void startBDLocation() {
		if (mLocationClient!=null && !mLocationClient.isStarted()) {
			mLocationClient.start();
		}
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			address = location.getAddrStr();
			
			if(isFirstLocation){
				isFirstLocation = false;
				curLocationMarker=null;
				displayCurrLocation(null);
				//查询附近路ID
				queryNearRoadID();
			}
			
			if(curLocationMarker!=null) //每次定位修改当前位置
				curLocationMarker.setPosition(new LatLng(latitude, longitude)); //修改当前位置
			String newCity = StringUtils.remove(location.getCity(), "市");
			if (!app.getCity().equals(newCity)) {
				
				// 不同城市，重新加载天气
				app.setCityChange(true);
			}
		}

	}
	
	//延时加载引导页
	private MyHandler delayHandler = new MyHandler(RealTimeTrafficFragment.this){  
	    @Override  
	    public void handleMessage(Message msg) { 
	    	RealTimeTrafficFragment outer = mOuter.get();
	        switch (msg.what) {  
	        case 0:  
	    		//引导页
	        	SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(getActivity());  
	        	String str = mPerferences.getString("RealTimeTrafficFragment", null);
	        	if(str==null) return;
	        	if(str.equals("show")){
	    			
	    			index = new IndexPopupWindow(getActivity(),"RealTimeTrafficFragment");
	    			index.mCallbacks = RealTimeTrafficFragment.this;
		    		index.showPopupWindow(outer.imgFunMore);//弱引用
		    		
		    		SharedPreferences.Editor mEditor = mPerferences.edit();
		   		 	mEditor.putString("RealTimeTrafficFragment", "hide").commit();
	    		}
	            break; 
	        case 1:
	        	//显示附近的高速路
	        	showNearRoad();
	        	break;
	        }  
	    }  
	      
	};  
	
	/**
	 * 引导页消失接口
	 * */
	/* (non-Javadoc)
	 * @see com.ltkj.highway.IndexPopupWindow.Callbacks#indexGone()
	 */
	@Override
	public void indexGone() {
		// TODO Auto-generated method stub
		onClickFunMore(null);
	}


	private void initWidget() {
		// 隐藏缩放控件
		hiddenZoomControl();
		tollMarkers = new ArrayList<Marker>();
		accidentMarkers = new ArrayList<Marker>();
		fixMarkers = new ArrayList<Marker>();
		serviceMarkers = new ArrayList<Marker>();
	}

	private void hiddenZoomControl() {
		View zoom = null;
		int childCount = mapView.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View childView = mapView.getChildAt(i);
			if (childView instanceof ZoomControls) {
				zoom = childView;
				break;
			}
		}
		zoom.setVisibility(View.GONE);
	}

	

	/**
	 * 标注当前位置
	 * 
	 * @param view
	 */

	public synchronized void displayCurrLocation(View view) {
		
		nearRoadPolyList.clear();
		if(nearRoadIDs!=null){
			mapView.getMap().clear();
			funMorePop = null;
			curLocationMarker=null;
			queryNearRoadID(); //重新查询
		}
		new LocationThread().start();
	}

	class LocationThread extends Thread {

		@Override
		public void run() {
			for (int i = 0; i < 500; i++) {
				if (latitude == 0.0 || longitude == 0.0) {
					try {
						sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					// 显示以当前位置为中心地图
					locationHandler.sendEmptyMessage(1);
					break;
				}
			}
		}

	}

	@SuppressLint("HandlerLeak")
	private Handler locationHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// 显示以当前位置为中心地图
			LatLng latLng = new LatLng(latitude, longitude);
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
			mapView.getMap().animateMapStatus(u);
			if (curLocationMarker == null) {
				// 标注当前位置
				BitmapDescriptor bitmap = BitmapDescriptorFactory
						.fromResource(R.drawable.gps_loc);
				OverlayOptions option = new MarkerOptions().position(latLng)
						.icon(bitmap);
				curLocationMarker = (Marker) mapView.getMap()
						.addOverlay(option);
				curLocationMarker.setTitle("curLocation");
			} else {
				curLocationMarker.setPosition(latLng);
			}

			super.handleMessage(msg);
		}

	};

	/**
	 * 放大
	 * 
	 * @param view
	 */
	public void zoomIn(View view) {
		MapStatusUpdate u = MapStatusUpdateFactory.zoomIn();
		mapView.getMap().animateMapStatus(u);
	}

	/**
	 * 缩小
	 * 
	 * @param view
	 */
	public void zoomOut(View view) {
		MapStatusUpdate u = MapStatusUpdateFactory.zoomOut();
		mapView.getMap().animateMapStatus(u);
	}
	
	/**
	 * 高速资讯
	 * 
	 * @param view
	 */
	public void onClickMessage(View view){
		Intent intent = new Intent(getActivity().getApplicationContext(), NewsActivity.class);
		startActivity(intent);
	}
	
	/**
	 * 更多功能
	 * 
	 * @param view
	 */
	public void onClickFunMore(View view){
		
		if(funMorePop == null)
		{
			funMorePop = new FunMorePopupWindow(getActivity());
		}
		funMorePop.showPopupWindow(imgFunMore);
		funMorePop.mCallbacks = this; //设置代理
	
	}
	
	/**
	 * 高速公路列表
	 * 
	 * @param view
	 */
	public void onClickHighWay(View view){
		Intent intent = new Intent(getActivity().getApplicationContext(),HighWayActivity.class);
		startActivity(intent);
	}
	
	/**
	 * 查询附近的高速路ID，获得IDs之后马上查询附近高速路以及速度
	 * */
	public void queryNearRoadID(){
		AjaxParams params = new AjaxParams();
		params.put("ycode", String.valueOf(latitude));
		params.put("xcode", String.valueOf(longitude));
		NetConnetion dao = new NetConnetion();
		dao.getNearroads(params, new AjaxCallBack<Object>(){

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				
				try {
					JSONObject json = new JSONObject(t.toString());
					int responseCode = JsonUtil.getJSONIntegerValue(json, "responseCode");
					if(responseCode==0)
					{
						Object roadArray =  json.get("road");
						nearRoadIDs = new ArrayList<String>();
						String[] roadIDString = roadArray.toString().split(",");
						for(int i=0; i<roadIDString.length; i++)
						{
							nearRoadIDs.add(roadIDString[i]);
						}
						//查询附近高速路的通行速度
						querySpeed();
						//延时附近高速路
						delayHandler.sendEmptyMessageDelayed(1, 500); 
						
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
						Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG);
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
			}
			
		});
	}
	
	/**
	 * 查询附近的交通事故
	 * */
	public void queryAccident(){
		if(nearRoadIDs!=null){
			final CustomToast myToast = new CustomToast(getActivity().getApplicationContext());
			myToast.show("显示附近交通事故中..", -1);
			AjaxParams params = new AjaxParams();
			params.put("pageSize", "10");
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh.mm.ss");     
			String date = sDateFormat.format(new java.util.Date()); 
			params.put("queryTime", date);
			params.put("type", "1");
			String roadsString = null;
			for(int i=0; i<nearRoadIDs.size(); i++){
				roadsString = roadsString+","+nearRoadIDs.get(i);
			}
			params.put("road",roadsString);
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

							List<Map<String, Object>> accidentList = JsonUtil.getList(dataArray.toString());
							//画到地图上
							accidentMarkers.clear();
							for(int i=0; i<accidentList.size(); i++){
								LatLng ll = new LatLng(Double.parseDouble(accidentList.get(i).get("coor_y").toString()), Double.parseDouble(accidentList.get(i).get("coor_x").toString()));
								OverlayOptions oo = new MarkerOptions().position(ll).icon(accident_map)
										.zIndex(9);
								Marker marker = (Marker)(mapView.getMap().addOverlay(oo));
								Bundle bundle = new Bundle();
								bundle.putSerializable("newsData", (Serializable) accidentList.get(i));
								marker.setExtraInfo(bundle);
								marker.setTitle("accident");
								accidentMarkers.add(marker);
							}
							accidentList = null;
							if(accidentMarkers.size() == 0) //附近没有交通事故
							{
								Toast.makeText(getActivity(), "附近没有交通事故！", Toast.LENGTH_SHORT).show();
							}
							myToast.hide();
						}else
						{
							myToast.hide();
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
							Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG);
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
					myToast.hide();
					super.onFailure(t, errorNo, strMsg);
					Toast toast = Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_LONG);
					toast.show();
				}
				
			});
		}
		else{ //还没请求到附近的高速路ID
			Toast.makeText(getActivity(), "未获取到附近高速路ID，请稍后尝试..", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * 查询附近的道路施工
	 * */
	public void queryFix(){
		if(nearRoadIDs!=null){
			final CustomToast myToast = new CustomToast(getActivity().getApplicationContext());
			myToast.show("显示附近道路施工中..", -1);
			AjaxParams params = new AjaxParams();
			params.put("pageSize", "10");
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh.mm.ss");     
			String date = sDateFormat.format(new java.util.Date()); 
			params.put("queryTime", date);
			params.put("type", "2");
			String roadsString = null;
			for(int i=0; i<nearRoadIDs.size(); i++){
				roadsString = roadsString+","+nearRoadIDs.get(i);
			}
			params.put("road",roadsString);
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
							List<Map<String, Object>> fixList = JsonUtil.getList(dataArray.toString());
							//画到地图上
							fixMarkers.clear();
							for(int i=0; i<fixList.size(); i++){
								LatLng ll = new LatLng(Double.parseDouble(fixList.get(i).get("coor_y").toString()), Double.parseDouble(fixList.get(i).get("coor_x").toString()));
								OverlayOptions oo = new MarkerOptions().position(ll).icon(fix_map)
										.zIndex(9);
								Marker marker = (Marker)(mapView.getMap().addOverlay(oo));
								Bundle bundle = new Bundle();
								bundle.putSerializable("newsData", (Serializable) fixList.get(i));
								marker.setExtraInfo(bundle);
								marker.setTitle("fix");
								fixMarkers.add(marker);
							}
							fixList = null;
							if(fixMarkers.size() == 0) //附近没有道路正在施工
							{
								Toast.makeText(getActivity(), "附近没有道路正在施工！", Toast.LENGTH_SHORT).show();
							}
							myToast.hide();
						}else
						{
							myToast.hide();
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
							Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG);
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
					myToast.hide();
					Toast toast = Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_LONG);
					toast.show();
				}
				
			});
		}
		else{ //还没请求到附近的高速路ID
			Toast.makeText(getActivity(), "未获取到附近高速路ID，请稍后尝试..", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	/**
	 * 查询附近高速路的速度
	 * */
	public void querySpeed(){
		if(nearRoadIDs!=null){
			speedMap = new HashMap<String,List<String>>();
			String roadStr = "";
			for(int i=0; i<nearRoadIDs.size(); i++)
				roadStr = roadStr + nearRoadIDs.get(i) +",";
			roadStr = roadStr.substring(0, roadStr.length()-1);
			
			AjaxParams params = new AjaxParams();
			params.put("road",roadStr);
			NetConnetion dao = new NetConnetion();
			dao.getRoadSpeed(params, new AjaxCallBack<Object>(){

				@Override
				public void onSuccess(Object t) {
					// TODO Auto-generated method stub
					super.onSuccess(t);
					try {
						JSONObject json = new JSONObject(t.toString());
						JSONArray roadSpeed = json.getJSONArray("roadSpeed");
						if(speedMap!=null) //防止界面已经跳转
							for(int i=0; i<roadSpeed.length(); i++){
								JSONArray eachRoadSpeed = roadSpeed.getJSONObject(i).getJSONArray("speed");
								speedMap.put(roadSpeed.getJSONObject(i).getString("road"), JsonUtil.getListString(eachRoadSpeed.toString()));
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
				}
				
			});
			
		
		}
		else{
			Toast.makeText(getActivity(), "未获取到附近高速路ID，请稍后尝试..", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 显示附近的高速路
	 * */
	public void showNearRoad(){
		if(nearRoadPolyList.size()==0){ //第一次画
			new Thread(new Runnable(){

				@Override
				public void run() {
					try {
						// TODO Auto-generated method stub
						for(int i=0;i<nearRoadIDs.size();i++){ //每一条路
	
							JSONObject eachRoadMap;
							eachRoadMap = new JSONObject(FileUtil.getFromAssets(app, "data/RoadCoordinate/"+nearRoadIDs.get(i).toString()+".json"));
							JSONArray eachStationList = eachRoadMap.getJSONArray("Line");
							for(int j=0; j<eachStationList.length(); j++){ //两两收费站
								String speed = "100"; //默认速度
								if(speedMap!=null && speedMap.get(nearRoadIDs.get(i))!=null){//已获得速度
									String startID = eachStationList.getJSONObject(j).get("startID").toString();
									String endID = eachStationList.getJSONObject(j).get("endID").toString();
									speed = CommonFun.searchSpeed(speedMap.get(nearRoadIDs.get(i)), startID, endID);
								}
								JSONArray points = eachStationList.getJSONObject(j).getJSONArray("points");
								List<LatLng> pts = new ArrayList<LatLng>();
								if(points.length()==0)continue;
								for(int p=0; p<points.length(); p++){ //每一个经纬度
									JSONObject point = points.getJSONObject(p);
									LatLng ll = new LatLng(Double.parseDouble(point.get("lat").toString()),Double.parseDouble(point.get("lon").toString()));
									pts.add(ll);
								}
								if(pts.size()<2){ //少于2个点画不了
									continue;
								}
								final PolylineOptions polylineOption;
								if(CommonFun.SpeedFast(speed)){//高速路径的颜色
									polylineOption = new PolylineOptions().points(pts).color(0x5500FF00).width(10);
								}else if(CommonFun.SpeedSlow(speed)){
									polylineOption = new PolylineOptions().points(pts).color(0x55ff7862).width(10);
								}else{
									polylineOption = new PolylineOptions().points(pts).color(0x55ffff00).width(10);
								}
								nearRoadPolyList.add(polylineOption);
								handler.post(new Runnable() {
	
									@Override
									public void run() { //多线程返回
										// TODO Auto-generated method stub
										if(!activityChange){
											mapView.getMap().addOverlay(polylineOption); //将两两收费站之间的经纬度画上地图中
										}
									} 
	
								});
							}
							eachRoadMap = null;
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}).start();
		}
		else{
			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					for(int i=0; i<nearRoadPolyList.size(); i++){
						if(!activityChange)
							mapView.getMap().addOverlay(nearRoadPolyList.get(i)); //将两两收费站之间的经纬度画上地图中
						else
							return;
					}
				}
				
			}).start();
		}
		
	}


	/**
	 * 接口方法：返回点击的funMoreButton的Num
	 * 
	 * */
	/* (non-Javadoc)
	 * @see com.ltkj.highway.FunMorePopupWindow.Callbacks#onItemsSelected(int)
	 */
	@Override
	public void onItemsSelected(int i) {
		// TODO Auto-generated method stub
		switch(i){
		case accidentNum:
			showAccident();
			break;
		case fixNum:
			showFix();
			break;
		case serviceNum:
			showService();
			break;
		case tollNum:
			showToll();
			break;
		case allRoadNum:
			showAllRoad();
			break;
		}
	}
	
	/**
	 * 显示附近的交通事故
	 * */
	public void showAccident(){
		if(accidentFlag)
		{
			accidentFlag = false;
			queryAccident();
		}
		else{
			accidentFlag = true;
			//回收
			if(accidentMarkers!=null && accidentMarkers.size()>0){
				for(int i=0; i<accidentMarkers.size(); i++){
					accidentMarkers.get(i).remove();
				}
				mapView.getMap().hideInfoWindow();
			}
		}
		
	
	}
	
	/**
	 * 显示附近的道路施工
	 * */
	public void showFix(){
		if(fixFlag)
		{
			fixFlag = false;
			queryFix();
		}
		else{
			fixFlag = true;
			//回收
			if(fixMarkers!=null && fixMarkers.size()>0){
				for(int i=0; i<fixMarkers.size(); i++){
					fixMarkers.get(i).remove();
				}
				mapView.getMap().hideInfoWindow();
			}
		}
		
		
	}
	
	/**
	 * 显示附近的收费站
	 * */
	public void showToll(){
		
		if(tollFlag)
		{
			tollFlag = false;
			if(nearRoadIDs!=null){
				tollMarkers.clear();
				Map<String,Object>allStations = JsonUtil.getMap(FileUtil.getFromAssets(getActivity().getApplicationContext(), "data/StationByRoadID.json"));
				for(int i=0; i<nearRoadIDs.size(); i++) //每一条路
				{
					List<Map<String,Object>>eachRoad = JsonUtil.getList(allStations.get(nearRoadIDs.get(i)).toString());
					for(int j=0; j<eachRoad.size(); j++){ //每一个站
						LatLng ll = new LatLng(Double.parseDouble((String) eachRoad.get(j).get("ycode")), Double.parseDouble((String) eachRoad.get(j).get("xcode")));
						OverlayOptions oo = new MarkerOptions().position(ll).icon(toll_map)
								.zIndex(9);
						Marker marker = (Marker)(mapView.getMap().addOverlay(oo));
						Bundle bundle = new Bundle();
						bundle.putSerializable("name", (Serializable) eachRoad.get(j).get("stationname"));
						marker.setExtraInfo(bundle);
						marker.setTitle("toll");
						tollMarkers.add(marker);
					}
				}
				allStations=null;
				if(tollMarkers.size() == 0) //附近没有加油站
				{
					Toast.makeText(getActivity(), "附近没有加油站！", Toast.LENGTH_SHORT).show();
				}

			}
			else{
				Toast.makeText(getActivity(), "未获取到附近高速路ID，请稍后尝试..", Toast.LENGTH_SHORT).show();
			}
		}
		else
		{
			tollFlag = true;
			if(tollMarkers!=null && tollMarkers.size()>0){
				for(int i=0; i<tollMarkers.size(); i++){
					tollMarkers.get(i).remove();
				}
				mapView.getMap().hideInfoWindow();
			}
		}
	}
	
	/**
	 * 显示附近的服务区
	 * */
	public void showService(){
		
		if(serviceFlag)
		{
			serviceFlag = false;
			if(nearRoadIDs!=null){
				serviceMarkers.clear();
				Map<String , Object>allService = JsonUtil.getMap(FileUtil.getFromAssets(getActivity().getApplicationContext(), "data/serviceArea.json"));
				for(int i=0; i<nearRoadIDs.size(); i++){ //每一条路
					//若没有这条路的服务区信息
					if(allService.get(nearRoadIDs.get(i)) == null) continue;
					
					Map<String, Object>eachRoadMap = JsonUtil.getMap(allService.get(nearRoadIDs.get(i)).toString());
					List<Map<String,Object>>eachRoad = JsonUtil.getList(eachRoadMap.get("RESTS").toString());
					
					
					for(int j=0; j<eachRoad.size(); j++){ //每一个服务区
						
						//除去map中的array,否则在点击marker跳转时会出异常
						Map<String,Object>service = eachRoad.get(j);
						service.remove("StartID");
						service.remove("EndID");
						
						if(eachRoad.get(j).get("Coor_Lon1")!=null && eachRoad.get(j).get("Coor_Lat1")!=null){
							LatLng ll = new LatLng(Double.parseDouble(eachRoad.get(j).get("Coor_Lat1").toString()), Double.parseDouble( eachRoad.get(j).get("Coor_Lon1").toString()));
							OverlayOptions oo = new MarkerOptions().position(ll).icon(service_map)
									.zIndex(9);
							Marker marker = (Marker)(mapView.getMap().addOverlay(oo));
							Bundle bundle = new Bundle();
							bundle.putSerializable("serviceData", (Serializable) service);
							bundle.putString("roadName", eachRoadMap.get("RoadName").toString());
							marker.setExtraInfo(bundle);
							marker.setTitle("service");
							serviceMarkers.add(marker);
						}
						if(eachRoad.get(j).get("Coor_Lon2")!=null && eachRoad.get(j).get("Coor_Lat2")!=null){
							LatLng ll = new LatLng(Double.parseDouble( eachRoad.get(j).get("Coor_Lat2").toString()), Double.parseDouble( eachRoad.get(j).get("Coor_Lon2").toString()));
							OverlayOptions oo = new MarkerOptions().position(ll).icon(service_map)
									.zIndex(9);
							Marker marker = (Marker)(mapView.getMap().addOverlay(oo));
							Bundle bundle = new Bundle();
							bundle.putSerializable("serviceData", (Serializable) service);
							bundle.putString("roadName", eachRoadMap.get("RoadName").toString());
							marker.setExtraInfo(bundle);
							marker.setTitle("service");
							serviceMarkers.add(marker);
						}
					}
				}
				allService = null;
				if(serviceMarkers.size() == 0) //附近没有服务区
				{
					Toast.makeText(getActivity(), "附近没有服务区！", Toast.LENGTH_SHORT).show();
				}
			}
			else{
				Toast.makeText(getActivity(), "未获取到附近高速路ID，请稍后尝试..", Toast.LENGTH_SHORT).show();
			}
			
		}
		else
		{
			serviceFlag = true;
			if(serviceMarkers!=null && serviceMarkers.size()>0){
				for(int i=0; i<serviceMarkers.size(); i++){
					serviceMarkers.get(i).remove();
				}
				mapView.getMap().hideInfoWindow();
			}
		}
	}
	
	/**
	 * 显示所有的高速路
	 * */
	public void showAllRoad(){
		
		if(allRoadFlag){
			allRoadFlag = false;
			threadStopFlag = false;
		
			if(polyList==null) //第一次画
			{
				final CustomToast myToast = new CustomToast(getActivity().getApplicationContext());
				myToast.show("正在请求高速路信息...", -1);
				
				polyList = new ArrayList<PolylineOptions>();	
				 final boolean change = activityChange; //获得当前的状态值
				showAllRoadThread = new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							//获得所有的路ID
							JSONArray road = new JSONArray(FileUtil.getFromAssets(app, "data/Road.json"));
							List<String> roadList = new ArrayList<String>();
							for(int i=0; i<road.length(); i++){
								roadList.add(road.getJSONObject(i).getString("ROADID"));
							}
							for(int i=0;i<roadList.size();i++){ //每一条路
								String fileStr = FileUtil.getFromAssets(app, "data/RoadCoordinate/"+roadList.get(i)+".json");
								if(fileStr==null || fileStr.length()==0) continue;
								Log.i("road",roadList.get(i));
								JSONObject eachRoadMap = new JSONObject(fileStr);
								JSONArray eachStationList = eachRoadMap.getJSONArray("Line");
								for(int j=0; j<eachStationList.length(); j++){ //两两收费站
									JSONArray points = eachStationList.getJSONObject(j).getJSONArray("points");
									List<LatLng> pts = new ArrayList<LatLng>();
									if(points.length()==0) continue;
									for(int p=0; p<points.length(); p++){ //每一个经纬度
										JSONObject point = points.getJSONObject(p);
										LatLng ll = new LatLng(Double.parseDouble(point.get("lat").toString()),Double.parseDouble(point.get("lon").toString()));
										pts.add(ll);
									}
									if(pts.size()<2){ //少于2个点画不了
										continue;
									}
									final PolylineOptions polylineOption = new PolylineOptions().points(pts).color(0x5500FF00).width(10);
									polyList.add(polylineOption); //先把所有的高速路的缓存
									
									if(threadStopFlag||activityChange) //用户取消了按键
										myToast.hide();
								}
								eachRoadMap=null;
							}
							//请求完数据，一次性画
//							myToast.hide();
//							myToast.show("已缓存高速路数据，正在绘制高速路..", -1);
							for(int i=0; i<polyList.size(); i++){
								if(!change && !threadStopFlag){
									mapView.getMap().addOverlay(polyList.get(i));
								}
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
							myToast.hide();
						}
					}
					
				});
				showAllRoadThread.start();
				
				
			}else{//不是第一次画，已缓存有数据
				Toast.makeText(getActivity(), "正在绘制高速路...", Toast.LENGTH_LONG).show();
				new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						for(int i=0; i<polyList.size(); i++){
							if(!activityChange && !threadStopFlag)
								mapView.getMap().addOverlay(polyList.get(i)); //将两两收费站之间的经纬度画上地图中
							else
								return;
						}
					}
					
				}).start();
				
			}
			
		}else{
			allRoadFlag = true;
			showAllRoadThread = null;
			threadStopFlag = true;
			
			mapView.getMap().clear();
			//重新绘制当前位置marker
			curLocationMarker=null;
			displayCurrLocation(null);
		}
		
	}
	
	
	
	/**
	 * Marker的点击事件
	 * */
	public void OnMarkerClick(){
		mapView.getMap().setOnMarkerClickListener(new OnMarkerClickListener(){

			@Override
			public boolean onMarkerClick(Marker marker) {
				Button button = new Button(getActivity().getApplicationContext());
				InfoWindow mInfoWindow = null;
				LatLng ll = marker.getPosition();
				
				if(marker.getTitle().equals("toll")){ //收费站
					button.setBackgroundResource(R.drawable.station_callout_bg);
					button.setText(marker.getExtraInfo().get("name").toString());
					mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll,  -60, null);
					
				}
				else if(marker.getTitle().equals("accident") || marker.getTitle().equals("fix")){ //交通事故、道路施工
					final Bundle bundle = marker.getExtraInfo();
					Map<String,Object> data = (Map<String, Object>) bundle.getSerializable("newsData");
					button.setText(data.get("occurTime").toString());
					button.setBackgroundResource(R.drawable.station_callout_bg);
					mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll, -60, new OnInfoWindowClickListener(){

						@Override
						public void onInfoWindowClick() {
							// TODO Auto-generated method stub
							Intent intent = new Intent(getActivity().getApplicationContext(),NewsInfoActivity.class);
							intent.putExtras(bundle);
							startActivity(intent);
						}
					});
				}
				else if(marker.getTitle().equals("service")){ //服务区
					final Bundle bundle = marker.getExtraInfo();
					Map<String,Object> data = (Map<String, Object>) bundle.getSerializable("serviceData");
					button.setText(data.get("RestName").toString());
					button.setBackgroundResource(R.drawable.station_callout_bg);
					mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll, -60, new OnInfoWindowClickListener(){

						@Override
						public void onInfoWindowClick() {
							// TODO Auto-generated method stub
							Intent intent = new Intent(getActivity().getApplicationContext(),ServiceActivity.class);
							intent.putExtras(bundle);
							startActivity(intent);
						}
					});
				}else if(marker.getTitle().equals("curLocation"))//点击到当前位置
				{
					if(address!=null){
						button.setBackgroundResource(R.drawable.popup);
						button.setText(" "+address+" ");
						button.setTextColor(Color.BLACK);
						mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll,  -60, null);
					}else
						return true;
				}
				
				mapView.getMap().showInfoWindow(mInfoWindow);
				return true;
			}
			
		});
		
		//点击地图隐藏infoWindow
		 mapView.getMap().setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng arg0) {
				// TODO Auto-generated method stub
				mapView.getMap().hideInfoWindow();
			}

			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				// TODO Auto-generated method stub
				return false;
			}  });
	}

	/**
	 * 回收内存，重置标志
	 * */
	private void recycle(){
		
		app.setLatitude(latitude);
		app.setLongitude(longitude);
		app.setAddress(address);
		
		tollFlag = true;
		accidentFlag = true;
		fixFlag = true;
		serviceFlag = true;
		allRoadFlag = true;
		
		if(tollMarkers!=null)
			tollMarkers.clear();
		if(accidentMarkers!=null)
			accidentMarkers.clear();
		if(fixMarkers!=null)
			fixMarkers.clear();
		if(serviceMarkers!=null)
			serviceMarkers.clear();
		
		tollMarkers=null;
		accidentMarkers=null;
		fixMarkers=null;
		serviceMarkers=null;
		speedMap=null;
		showAllRoadThread=null;
		
		System.gc();
	}
	
	/**
	 * 防止handler泄露的内部静态类
	 * 
	 * */
	static class MyHandler extends Handler{
		//弱引用
		public WeakReference<RealTimeTrafficFragment> mOuter;
		
		public MyHandler(RealTimeTrafficFragment fragment){
			mOuter = new WeakReference<RealTimeTrafficFragment>(fragment);
		}
	}
}
