/**
 * 
 */
package com.ltkj.highway.highwaylist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import net.tsz.afinal.annotation.view.ViewInject;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.ltkj.highway.R;
import com.ltkj.highway.common.CommonFun;
import com.ltkj.highway.news.NewsInfoActivity;
import com.ltkj.highway.news.ServiceActivity;
import com.ltkj.highway.utils.FileUtil;
import com.ltkj.highway.utils.JsonUtil;
import com.ltkj.highway.widget.BaseActivity;

/**
 * @author chenzy
 * @version 创建时间：2014-10-16 上午11:04:41
 */

public class HighWayListAtMapActivity extends BaseActivity{

	@ViewInject(id = R.id.common_header_bar_left_item, click = "goBack")
	private Button returnButton;
	@ViewInject(id = R.id.common_header_bar_title)
	private TextView titleTextView;
	@ViewInject(id = R.id.bmapView)
	private MapView mapView;
	@ViewInject(id = R.id.imgZoomOut, click = "zoomOut")
	private ImageView imgZoomOut;
	@ViewInject(id = R.id.imgZoomIn, click = "zoomIn")
	private ImageView imgZoomIn;
	
	
	BitmapDescriptor toll_map = BitmapDescriptorFactory
			.fromResource(R.drawable.toll_map);
	BitmapDescriptor service_map = BitmapDescriptorFactory
			.fromResource(R.drawable.service_map);
	BitmapDescriptor accident_map = BitmapDescriptorFactory
			.fromResource(R.drawable.accident_map);
	BitmapDescriptor fix_map = BitmapDescriptorFactory
			.fromResource(R.drawable.fix_map);
	
	private List<Map<String,Object>> stationList = new ArrayList<Map<String,Object>>();
	private List<Map<String,Object>> restList = new ArrayList<Map<String,Object>>();
	private List<Map<String,Object>> accidentList = new ArrayList<Map<String,Object>>();
	private List<Map<String,Object>> fixList = new ArrayList<Map<String,Object>>();
	private List<String> speedList = new ArrayList<String>();
	private String roadID,roadName;
	private boolean activityChange = false;
	
	@Override
	public void goBack(View view) {
		// TODO Auto-generated method stub
		super.goBack(view);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_highwaylistatmap);
		
		//获取从Intent中传来的值
		Bundle data = getIntent().getExtras();
		stationList = (List<Map<String, Object>>) data.getSerializable("data");
		restList = (List<Map<String, Object>>) data.getSerializable("service");
		accidentList = (List<Map<String, Object>>) data.getSerializable("accident");
		fixList = (List<Map<String, Object>>) data.getSerializable("fix");
		roadName = data.getString("roadName");
		titleTextView.setText(roadName);
		roadID = data.getString("roadID");
		speedList = (List<String>) data.getSerializable("speed");
		
		showToll();
		showService();
		showAccident();
		showFix();
		hiddenZoomControl();
		
		OnMarkerClick();
		showRoad();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		activityChange = true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		mapView.onResume();
		activityChange = false;
		super.onResume();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		displayCurrLocation(null);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mapView.onDestroy();
		super.onDestroy();
		toll_map.recycle();
		service_map.recycle();
		accident_map.recycle();
		fix_map.recycle();
		
		stationList.clear();
		restList.clear();
		accidentList.clear();
		fixList.clear();
		fixList=null;
		accidentList=null;
		restList=null;
		stationList=null;
		System.gc();
	}
	
	/**
	 * 标注当前位置
	 * 
	 * @param view
	 */
	public synchronized void displayCurrLocation(View view) {
		new LocationThread().start();
	}

	class LocationThread extends Thread {

		@Override
		public void run() {
			for (int i = 0; i < 500; i++) {
				double latitude = Double.parseDouble(stationList.get(0).get("ycode").toString());
				double longitude = Double.parseDouble(stationList.get(0).get("xcode").toString());
				if (latitude == 0.0 || longitude == 0.0) {
					try {
						sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					// 显示以第一个站的经纬度为中心地图
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
			// 显示以第一个站的经纬度为中心地图
			double latitude = Double.parseDouble(stationList.get(0).get("ycode").toString());
			double longitude = Double.parseDouble(stationList.get(0).get("xcode").toString());
			LatLng latLng = new LatLng(latitude, longitude);
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
			mapView.getMap().animateMapStatus(u);

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
	 * 初始化收费站的信息
	 * */
	public void showToll(){
		if(stationList!=null){
			for(int j=0; j<stationList.size(); j++){ //每一个站
				LatLng ll = new LatLng(Double.parseDouble((String) stationList.get(j).get("ycode")), Double.parseDouble((String) stationList.get(j).get("xcode")));
				OverlayOptions oo = new MarkerOptions().position(ll).icon(toll_map)
						.zIndex(9);
				Marker marker = (Marker)(mapView.getMap().addOverlay(oo));
				Bundle bundle = new Bundle();
				bundle.putSerializable("name", (Serializable) stationList.get(j).get("stationname"));
				marker.setExtraInfo(bundle);
				marker.setTitle("toll");
			}
		}
	}
	
	/**
	 * 显示交通事故
	 * */
	public void showAccident(){
		if(accidentList!=null){
			for(int j=0; j<accidentList.size(); j++){ 
				LatLng ll = new LatLng(Double.parseDouble((String) accidentList.get(j).get("coor_y")), Double.parseDouble((String) accidentList.get(j).get("coor_x")));
				OverlayOptions oo = new MarkerOptions().position(ll).icon(accident_map)
						.zIndex(9);
				Marker marker = (Marker)(mapView.getMap().addOverlay(oo));
				Bundle bundle = new Bundle();
				bundle.putSerializable("newsData", (Serializable) accidentList.get(j));
				marker.setExtraInfo(bundle);
				marker.setTitle("accident");
			}
		}
	}
	
	
	/**
	 * 显示道路施工
	 * */
	public void showFix(){
		if(fixList!=null){
			for(int j=0; j<fixList.size(); j++){ 
				LatLng ll = new LatLng(Double.parseDouble((String) fixList.get(j).get("coor_y")), Double.parseDouble((String) fixList.get(j).get("coor_x")));
				OverlayOptions oo = new MarkerOptions().position(ll).icon(fix_map)
						.zIndex(9);
				Marker marker = (Marker)(mapView.getMap().addOverlay(oo));
				Bundle bundle = new Bundle();
				bundle.putSerializable("newsData", (Serializable) fixList.get(j));
				marker.setExtraInfo(bundle);
				marker.setTitle("fix");
			}
		}
	}
	
	/**
	 * 显示服务区
	 * */
	public void showService(){
		if(restList==null || restList.size()==0)
			return;
		else{
			for(int i=0; i<restList.size(); i++){
				Map<String,Object>service = restList.get(i);
				service.remove("StartID");
				service.remove("EndID");
				
				if(!CommonFun.isNull(restList.get(i).get("Coor_Lon1")) && !CommonFun.isNull(restList.get(i).get("Coor_Lat1"))){
					LatLng ll = new LatLng(Double.parseDouble(restList.get(i).get("Coor_Lat1").toString()), Double.parseDouble( restList.get(i).get("Coor_Lon1").toString()));
					OverlayOptions oo = new MarkerOptions().position(ll).icon(service_map)
							.zIndex(9);
					Marker marker = (Marker)(mapView.getMap().addOverlay(oo));
					Bundle bundle = new Bundle();
					bundle.putSerializable("serviceData", (Serializable) service);
					bundle.putString("roadName", roadName);
					marker.setExtraInfo(bundle);
					marker.setTitle("service");
				}
				if(!CommonFun.isNull(restList.get(i).get("Coor_Lon2")) && !CommonFun.isNull(restList.get(i).get("Coor_Lat2"))){
					LatLng ll = new LatLng(Double.parseDouble( restList.get(i).get("Coor_Lat2").toString()), Double.parseDouble( restList.get(i).get("Coor_Lon2").toString()));
					OverlayOptions oo = new MarkerOptions().position(ll).icon(service_map)
							.zIndex(9);
					Marker marker = (Marker)(mapView.getMap().addOverlay(oo));
					Bundle bundle = new Bundle();
					bundle.putSerializable("serviceData", (Serializable) service);
					bundle.putString("roadName", roadName);
					marker.setExtraInfo(bundle);
					marker.setTitle("service");
				}
			}
		}
	}
	
	/**
	 * 显示高速公路
	 * */
	public void showRoad(){
		
			
		
		final Handler roadHandler = new Handler();
		new Thread(new Runnable(){

			@Override
			public void run() {
				try{
					JSONObject eachRoadMap;
					eachRoadMap = new JSONObject(FileUtil.getFromAssets(app, "data/RoadCoordinate/"+roadID.toString()+".json"));
					JSONArray eachStationList = eachRoadMap.getJSONArray("Line");
					for(int j=0; j<eachStationList.length(); j++){ //两两收费站
						String startID = eachStationList.getJSONObject(j).get("startID").toString();
						String endID = eachStationList.getJSONObject(j).get("endID").toString();
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
						
						final OverlayOptions polylineOption;
						if(speedList!=null){
							String speed = CommonFun.searchSpeed(speedList, startID, endID);
							if(CommonFun.SpeedFast(speed)){
								polylineOption = new PolylineOptions().points(pts).color(0x5500FF00).width(10);
							}else if(CommonFun.SpeedSlow(speed)){
								polylineOption = new PolylineOptions().points(pts).color(0x55ff7862).width(10);
							}else{
								polylineOption = new PolylineOptions().points(pts).color(0x55ffff00).width(10);
							}
						}else{ //没有请求到速度，默认绿色
							polylineOption = new PolylineOptions().points(pts).color(0x5500FF00).width(10);
						}
						if(activityChange){
							eachRoadMap = null;
							eachStationList=null;
							return;
						}
						roadHandler.post(new Runnable(){
	
							@Override
							public void run() {
								// TODO Auto-generated method stub
								mapView.getMap().addOverlay(polylineOption); //将两两收费站之间的经纬度画上地图中
							}
							
						});
					}
					eachRoadMap = null;
					eachStationList=null;
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}).start();
		
	}
		

	
	/**
	 * Marker的点击事件
	 * */
	public void OnMarkerClick(){
		mapView.getMap().setOnMarkerClickListener(new OnMarkerClickListener(){

			@Override
			public boolean onMarkerClick(Marker marker) {
				Button button = new Button(HighWayListAtMapActivity.this);
				InfoWindow mInfoWindow = null;
				LatLng ll = marker.getPosition();
				
				if(marker.getTitle().equals("toll")){ //收费站
					button.setBackgroundResource(R.drawable.station_callout_bg);
					button.setText(marker.getExtraInfo().get("name").toString());
					mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll, -60, null);
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
							Intent intent = new Intent(HighWayListAtMapActivity.this,ServiceActivity.class);
							intent.putExtras(bundle);
							startActivity(intent);
						}
					});
				
				}
				else if(marker.getTitle().equals("accident")||marker.getTitle().equals("fix")){
					final Bundle bundle = marker.getExtraInfo();
					Map<String,Object> data = (Map<String, Object>) bundle.getSerializable("newsData");  
					button.setText(data.get("createTime").toString());
					button.setBackgroundResource(R.drawable.station_callout_bg);
					mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll, -60, new OnInfoWindowClickListener(){

						@Override
						public void onInfoWindowClick() {
							// TODO Auto-generated method stub
							Intent intent = new Intent(HighWayListAtMapActivity.this,NewsInfoActivity.class);
							intent.putExtras(bundle);
							startActivity(intent);
						}
					});
				}else //点击到当前位置
				{
					return true;
				}
				
				mapView.getMap().showInfoWindow(mInfoWindow);
				return true;
			}
			
		});
	}
}
