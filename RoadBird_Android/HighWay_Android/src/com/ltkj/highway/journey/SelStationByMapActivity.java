package com.ltkj.highway.journey;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.tsz.afinal.annotation.view.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.ltkj.highway.R;
import com.ltkj.highway.po.Station;
import com.ltkj.highway.utils.FileUtil;
import com.ltkj.highway.widget.BaseActivity;

public class SelStationByMapActivity extends BaseActivity {

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
	@ViewInject(id = R.id.roadStationTextView)
	private TextView roadStationTextView;
	@ViewInject(id = R.id.btnConfirm, click = "btnConfirm")
	private Button btnConfirm;

	private JSONObject selStationJSONObject;
	/**
	 * 高速公路列表
	 */
	private JSONArray roadArray;

	/**
	 * 城市
	 */
	private String city;
	/**
	 * 站（城市所属）
	 */
	private JSONArray stationJsonArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selstationbymap);
		city = getIntent().getStringExtra("city");

		initStationData();
		initWidget();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mapView.onDestroy();
		super.onDestroy();
		roadArray=null;
		stationJsonArray=null;
		selStationJSONObject=null;
		System.gc();
	}
	
	private void initStationData() {
		String stationByCityString = FileUtil.getFromAssets(this,
				"data/StationByCity.json");
		try {
			JSONObject stationJsonObject = new org.json.JSONObject(
					stationByCityString);
			stationJsonArray = stationJsonObject.getJSONArray(city);
			stationByCityString = null;
		} catch (Exception e) {

		}
	}

	private void initWidget() {
		titleTextView.setText("选择站点");
		hiddenZoomControl();
		addInfosOverlay();
		onMarkerClick();

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

	private void addInfosOverlay() {
		if (stationJsonArray == null || stationJsonArray.length() == 0)
			return;
		LatLng latLng = null;
		OverlayOptions overlayOptions = null;
		Marker marker = null;
		BitmapDescriptor toll_map = BitmapDescriptorFactory
				.fromResource(R.drawable.toll_map);
		try {
			for (int i = 0; i < stationJsonArray.length(); i++) {
				JSONObject stationJsonObject = stationJsonArray
						.getJSONObject(i);
				double xcode = stationJsonObject.getDouble("XCODE");
				double ycoce = stationJsonObject.getDouble("YCODE");

				// 位置
				latLng = new LatLng(ycoce, xcode);
				// 图标
				overlayOptions = new MarkerOptions().position(latLng)
						.icon(toll_map).zIndex(9);
				marker = (Marker) (mapView.getMap().addOverlay(overlayOptions));

				Bundle bundle = new Bundle();
				Map<String, Object> stationMap = new HashMap<String, Object>();
				stationMap.put("station", stationJsonObject);
				bundle.putSerializable("station", (Serializable) stationMap);
				marker.setExtraInfo(bundle);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 将地图移到到最后一个经纬度位置
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
		mapView.getMap().setMapStatus(u);
	}

	/**
	 * 站点击事件
	 */
	private void onMarkerClick() {
		mapView.getMap().setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {
				// 获得marker中的数据
				@SuppressWarnings("unchecked")
				Map<String, Object> stationMap = (Map<String, Object>) marker
						.getExtraInfo().getSerializable("station");
				// 选择站
				selStationJSONObject = (JSONObject) stationMap.get("station");
				// 显示站信息
				displaySelStation();

				String stationName = "";
				try {
					stationName = selStationJSONObject.getString("STATIONNAME");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Button button = new Button(getApplicationContext());
				button.setBackgroundResource(R.drawable.station_callout_bg);
				button.setText(stationName);

				// 将marker所在的经纬度的信息转化成屏幕上的坐标
				final LatLng ll = marker.getPosition();

				InfoWindow mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll, -60, null);
				mapView.getMap().showInfoWindow(mInfoWindow);
				return true;
			}
		});

		mapView.getMap().setOnMapClickListener(new OnMapClickListener() {

			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onMapClick(LatLng arg0) {
				mapView.getMap().hideInfoWindow();

			}
		});
	}

	/**
	 * 显示选中站
	 */
	private void displaySelStation() {
		try {
			int roadID = selStationJSONObject.getInt("ROADID");
			String stationName = selStationJSONObject.getString("STATIONNAME");
			if (roadArray == null || roadArray.length() == 0) {
				String roadString = FileUtil.getFromAssets(this,
						"data/Road.json");
				roadArray = new org.json.JSONArray(roadString);
			}

			for (int i = 0; i < roadArray.length(); i++) {
				JSONObject road = roadArray.getJSONObject(i);
				int rid = road.getInt("ROADID");
				if (rid == roadID) {
					roadStationTextView.setText(road.getString("ROADNAME")
							+ "-" + stationName);
					break;
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
	 * 确定
	 * 
	 * @param view
	 */
	public void btnConfirm(View view) {
		if (selStationJSONObject == null) {
			return;
		} else {
			Intent intent = new Intent();
			Station station = new Station();
			station.jsonToStation(selStationJSONObject);
			intent.putExtra("selStation", station);
			setResult(RESULT_OK, intent);
			finish();
		}

	}
}
