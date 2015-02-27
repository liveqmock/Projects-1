/**
 * 
 */
package com.ltkj.highway.journey;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ltkj.highway.R;
import com.ltkj.highway.RealTimeTrafficFragment;
import com.ltkj.highway.common.NetConnetion;
import com.ltkj.highway.utils.FileUtil;
import com.ltkj.highway.utils.JsonUtil;
import com.ltkj.highway.widget.BaseActivity;
import com.ltkj.highway.widget.IndexPopupWindow;
import com.ltkj.highway.widget.IndexPopupWindow.Callbacks;

/**
 * @author chenzy
 * @version 创建时间：2014-10-27 上午10:32:43
 */

public class DrivingRouteDetailActivity extends BaseActivity implements
		IndexPopupWindow.Callbacks {

	@ViewInject(id = R.id.common_header_bar_left_item, click = "goBack")
	private Button returnButton;
	@ViewInject(id = R.id.common_header_bar_title)
	private TextView titleTextView;
	@ViewInject(id = R.id.common_header_bar_right_item, click = "goMap")
	private Button mapButton;
	@ViewInject(id = R.id.timeLabel)
	private TextView timeLabel;
	@ViewInject(id = R.id.moneyLabel)
	private TextView moneyLabel;
	@ViewInject(id = R.id.kilometreLabel)
	private TextView kilometreLabel;
	@ViewInject(id = R.id.list)
	private ListView list;

	private int type;
	private String totalFee = "";
	private String totalDistance = "";
	private String totalTime = "";
	private String requestIdentifier = "";

	private List<String> speedList = null;
	private List<String> roadStationList = null;
	private Map<String, List<String>> roadStationMap = null; // 按路ID分类好的station
	private List<String> roadList = null; // 按顺序的路
	private DrivingRouteDetailAdapter myAdapter;

	private List<Map<String, Object>> stationList = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> accidentList = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> fixList = new ArrayList<Map<String, Object>>();
	private Map<String, List<Map<String, Object>>> restMap = new HashMap<String, List<Map<String, Object>>>();

	private List<Map<String, Object>> passByList = new ArrayList<Map<String, Object>>();

	private Handler handler = new Handler();
	private IndexPopupWindow index = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drivingroutedetail);

		// 获取传值
		Intent intent = getIntent();
		type = intent.getIntExtra("type", 1);
		totalDistance = intent.getStringExtra("totalDistance");
		totalFee = intent.getStringExtra("totalFee");
		totalTime = intent.getStringExtra("totalTime");
		requestIdentifier = intent.getStringExtra("requestIdentifier");

		mapButton.setBackgroundResource(R.drawable.map);
		mapButton.setVisibility(View.VISIBLE);

		initData();

	}

	private void initData() {
		switch (type) {
		case 1:
			titleTextView.setText("最短距离");
			break;
		case 2:
			titleTextView.setText("最少收费");
			break;
		case 3:
			titleTextView.setText("最短时间");
			break;
		}
		timeLabel.setText(totalTime + "分");
		moneyLabel.setText(totalFee + "元");
		kilometreLabel.setText(totalDistance + "公里");
		mapButton.setVisibility(View.VISIBLE);

		// 网络请求数据
		queryStationInfo();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		delayHandler.sendEmptyMessageDelayed(0, 100);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		stationList.clear();
		if(index!=null){
			index.dismiss();
			index = null;
		}
		delayHandler.removeCallbacksAndMessages(null);
		
		//异步请求的数据，可能退出时还没返回数据
		
		restMap.clear();
		accidentList.clear();
		fixList.clear();
		
		fixList=null;
		accidentList=null;
		restMap=null;
		stationList=null;
		System.gc();
		super.onDestroy();
	}

	// 延时加载引导页
	private MyHandler delayHandler = new MyHandler(DrivingRouteDetailActivity.this) {
		@Override
		public void handleMessage(Message msg) {
			DrivingRouteDetailActivity outer = mOuter.get();
			switch (msg.what) {
			case 0:
				// 引导页
				SharedPreferences mPerferences = PreferenceManager
						.getDefaultSharedPreferences(DrivingRouteDetailActivity.this);
				String str = mPerferences.getString(
						"DrivingRouteDetailActivity", null);
				if (str == null)
					return;
				if (str.equals("show")) {

					index = new IndexPopupWindow(
							DrivingRouteDetailActivity.this,
							"DrivingRouteDetailActivity");
					index.mCallbacks = (Callbacks) DrivingRouteDetailActivity.this;
					index.showPopupWindow(outer.returnButton); //弱引用

					SharedPreferences.Editor mEditor = mPerferences.edit();
					mEditor.putString("DrivingRouteDetailActivity", "hide")
							.commit();
				}
				break;
			}
		}

	};

	/**
	 * 引导页消失接口
	 * */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ltkj.highway.IndexPopupWindow.Callbacks#indexGone()
	 */
	@Override
	public void indexGone() {
		// TODO Auto-generated method stub
		goMap(null);
	}

	private void initListView() {
		// 调用该方法时已有数据可以直接初始化
		myAdapter = new DrivingRouteDetailAdapter(
				DrivingRouteDetailActivity.this, stationList, speedList);
		list.setAdapter(myAdapter);
	}

	/**
	 * 网络请求获得具体经过的收费站
	 * */
	private void queryStationInfo() {
		startProgressDialog("加载中");
		AjaxParams params = new AjaxParams();
		params.put("requestIdentifier", requestIdentifier);
		params.put("type", String.valueOf(type));
		NetConnetion dao = new NetConnetion();
		dao.getRoadPlanDetail(params, new AjaxCallBack<Object>() {

			@Override
			public void onSuccess(Object t) {
				try {
					JSONObject json = new JSONObject(t.toString());
					int responseCode = JsonUtil.getJSONIntegerValue(json,
							"responseCode");
					if (responseCode == 0) {
						JSONArray dataArray = json.getJSONArray("planDetail");
						speedList = JsonUtil.getListString(JsonUtil
								.getMap(dataArray.get(0).toString())
								.get("speed").toString());
						roadStationList = JsonUtil.getListString(JsonUtil
								.getMap(dataArray.get(0).toString())
								.get("station").toString());

						// 分离路ID和站ID
						roadStationMap = separateStationAndRoad(roadStationList);
						// 请求交通事故和道路施工
						queryAccidentFixData();
						// 获得服务区
						queryServicData();
						// 初始化表视图
						initListView();

					} else {
						String errorMessage = null;
						if (responseCode == 1) {
							errorMessage = "获取数据失败";
						} else if (responseCode == 5) {
							errorMessage = "无权限访问！";
						} else if (responseCode == -1) {
							errorMessage = "连接服务器失败！";
						}
						Toast toast = Toast.makeText(
								DrivingRouteDetailActivity.this, errorMessage,
								Toast.LENGTH_LONG);
						toast.show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stopProgressDialog();
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				stopProgressDialog();
			}

		});
	}

	/**
	 * 分离路ID和站ID，分别获得路IDs与站IDs
	 * */
	private Map<String, List<String>> separateStationAndRoad(List<String> list) {
		list.add("0|0");
		Map<String, Object> stationByRoadID = JsonUtil.getMap(FileUtil
				.getFromAssets(DrivingRouteDetailActivity.this,
						"data/StationByRoadID.json"));

		Map<String, List<String>> roadStationMap = new HashMap<String, List<String>>();
		List<String> tempList = new ArrayList<String>();
		String[] eachs = list.get(0).split("\\|");
		String lastRoad = eachs[0];
		for (int i = 0; i < list.size(); i++) {
			// 获得以路ID为KEY的站集合（若头尾重复经过同一条路，顺序可能有误），用来请求交通事故、服务区
			String[] each = list.get(i).split("\\|");
			String road = each[0];
			boolean isDifRoad = false; // 不是同一条路
			if (!road.equals(lastRoad)) {
				roadStationMap.put(lastRoad, tempList);
				lastRoad = road;
				if (i == list.size() - 1)
					break;
				tempList = new ArrayList<String>();
				isDifRoad = true;
			}
			String station = each[1];
			tempList.add(station);

			// 获得站集合（保证顺序无误）
			List<Map<String, Object>> ary = new ArrayList<Map<String, Object>>();
			ary = JsonUtil.getList(stationByRoadID.get(road).toString());
			for (int j = 0; j < ary.size(); j++) {
				Map<String, Object> obj = new HashMap<String, Object>();
				obj = JsonUtil.getMap(ary.get(j).toString());
				if (obj.get("stationid").toString().equals(station)) {
					stationList.add((Map<String, Object>) obj);
					if (isDifRoad) { // 不是同一条路，加入到途经点集合
						passByList.add(obj);
					}
				}
			}

		}
		return roadStationMap;
	}

	/**
	 * 请求交通事故和收费站
	 * */
	private int accidentCount = 0, fixCount = 0; // 计算已请求的高速路数目

	private void queryAccidentFixData() {
		final Set roadIDs = roadStationMap.keySet();
		Iterator roadID = roadIDs.iterator();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh.mm.ss");
		String date = sDateFormat.format(new java.util.Date());

		// 遍历经过的每一条高速路，并各请求5条数据
		while (roadID.hasNext()) {
			String thisRoadID = roadID.next().toString();
			NetConnetion dao = new NetConnetion();

			// 请求交通事故
			AjaxParams accidentParams = new AjaxParams();
			accidentParams.put("pageSize", "5");
			accidentParams.put("queryTime", date);
			accidentParams.put("type", "1");
			accidentParams.put("road", thisRoadID);
			dao.getTrafficinfo(accidentParams, new AjaxCallBack<Object>() {

				@Override
				public void onSuccess(Object t) {
					// TODO Auto-generated method stub
					super.onSuccess(t);
					accidentCount++;
					try {
						JSONObject json = new JSONObject(t.toString());
						int responseCode = JsonUtil.getJSONIntegerValue(json,
								"responseCode");
						if (responseCode == 0) {
							JSONArray dataArray = json
									.getJSONArray("trafficInfo");
							// 把请求到的数据都叠加到accidentList中
							accidentList.addAll(JsonUtil.getList(dataArray
									.toString()));

							if (accidentCount == roadIDs.size()) {// 途径高速路的数据都添加完毕，再一次更新表视图
								myAdapter.setAccidentList(accidentList);
								myAdapter.notifyDataSetChanged();
							}
						} else {
							String errorMessage = null;
							if (responseCode == 1) {
								errorMessage = "获取数据失败";
							} else if (responseCode == 5) {
								errorMessage = "无权限访问！";
							} else if (responseCode == -1) {
								errorMessage = "连接服务器失败！";
							}
							Toast toast = Toast.makeText(
									DrivingRouteDetailActivity.this,
									errorMessage, Toast.LENGTH_LONG);
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
					Toast toast = Toast.makeText(
							DrivingRouteDetailActivity.this, "网络请求失败",
							Toast.LENGTH_LONG);
					toast.show();
				}

			});

			// 请求道路施工数据
			AjaxParams fixParams = new AjaxParams();
			fixParams.put("pageSize", "5");
			fixParams.put("queryTime", date);
			fixParams.put("type", "2");
			fixParams.put("road", thisRoadID);
			dao.getTrafficinfo(fixParams, new AjaxCallBack<Object>() {

				@Override
				public void onSuccess(Object t) {
					// TODO Auto-generated method stub
					super.onSuccess(t);
					fixCount++;
					try {
						JSONObject json = new JSONObject(t.toString());
						int responseCode = JsonUtil.getJSONIntegerValue(json,
								"responseCode");
						if (responseCode == 0) {
							JSONArray dataArray = json
									.getJSONArray("trafficInfo");
							// 把请求到的数据都叠加到accidentList中
							fixList.addAll(JsonUtil.getList(dataArray
									.toString()));

							if (fixCount == roadIDs.size()) { // 途径高速路的数据都添加完毕，再一次更新表视图
								myAdapter.setFixList(fixList);
								myAdapter.notifyDataSetChanged();
							}

						} else {
							String errorMessage = null;
							if (responseCode == 1) {
								errorMessage = "获取数据失败";
							} else if (responseCode == 5) {
								errorMessage = "无权限访问！";
							} else if (responseCode == -1) {
								errorMessage = "连接服务器失败！";
							}
							Toast toast = Toast.makeText(
									DrivingRouteDetailActivity.this,
									errorMessage, Toast.LENGTH_LONG);
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
					Toast toast = Toast.makeText(
							DrivingRouteDetailActivity.this, "网络请求失败",
							Toast.LENGTH_LONG);
					toast.show();
				}

			});
		}
	}

	/**
	 * 请求服务区数据
	 * */
	private void queryServicData() {
		// 使用线程提高效率
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					JSONObject serviceObj;
					serviceObj = new JSONObject(FileUtil.getFromAssets(
							DrivingRouteDetailActivity.this,
							"data/serviceArea.json"));
					Iterator iter = (Iterator) roadStationMap.keySet()
							.iterator();
					// 获得途经路的ID
					while (iter.hasNext()) {
						String road = iter.next().toString();
						if (serviceObj.get(road) == null) { // 该高速路没有服务区
							return;
						} else {
							List<Map<String, Object>> list = JsonUtil
									.getList(JsonUtil
											.getMap(serviceObj.get(road)
													.toString()).get("RESTS")
											.toString());
							// 加上高速路名
							for (int i = 0; i < list.size(); i++) {
								list.get(i)
										.put("roadName",
												JsonUtil.getMap(
														serviceObj.get(road)
																.toString())
														.get("RoadName")
														.toString());
							}
							restMap.put(road, list);
						}
					}

				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						myAdapter.setRestMap(restMap);
						myAdapter.notifyDataSetChanged();
					}

				});
			}

		}).start();

	}

	/**
	 * 地图模式
	 * */
	public void goMap(View view) {

		// 获取正真途经的交通事故、道路施工、服务区
		List<Map<String, Object>> realAccidentList = myAdapter
				.getRealAccidentList();
		List<Map<String, Object>> realFixList = myAdapter.getRealFixList();
		List<Map<String, Object>> realServiceList = myAdapter
				.getRealServiceList();

		Intent intent = new Intent(DrivingRouteDetailActivity.this,
				DrivingRouteDetailMapActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("type", String.valueOf(type));
		bundle.putSerializable("station", (Serializable) stationList);
		bundle.putSerializable("accident", (Serializable) realAccidentList);
		bundle.putSerializable("fix", (Serializable) realFixList);
		bundle.putSerializable("service", (Serializable) realServiceList);
		bundle.putSerializable("passBy", (Serializable) passByList);
		intent.putExtras(bundle);
		
		realAccidentList = null;
		realFixList = null;
		realServiceList = null;
		
		startActivity(intent);
	}
	
	/**
	 * 防止handler泄露的内部静态类
	 * 
	 * */
	static class MyHandler extends Handler{
		//弱引用
		public WeakReference<DrivingRouteDetailActivity> mOuter;
		
		public MyHandler(DrivingRouteDetailActivity activity){
			mOuter = new WeakReference<DrivingRouteDetailActivity>(activity);
		}
	}
}
