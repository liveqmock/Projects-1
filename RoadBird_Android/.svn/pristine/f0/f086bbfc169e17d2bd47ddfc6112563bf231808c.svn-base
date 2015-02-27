package com.ltkj.highway.journey;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ltkj.highway.R;
import com.ltkj.highway.common.HighwayApp;
import com.ltkj.highway.common.NetConnetion;
import com.ltkj.highway.po.QueryPathRecord;
import com.ltkj.highway.po.Station;
import com.ltkj.highway.widget.BaseActivity;

public class QueryDirvingRouteResultActivity extends BaseActivity {

	@ViewInject(id = R.id.common_header_bar_left_item, click = "goBack")
	private Button returnButton;
	@ViewInject(id = R.id.common_header_bar_title)
	private TextView titleTextView;
	@ViewInject(id = R.id.resultListView, itemClick = "listClick")
	private ListView resultListView;
	@ViewInject(id = R.id.tipTextView)
	private TextView tipTextView;

	private Station startStation, endStation;
	private String requestIdentifier;
	private JSONArray planArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_querydirvingrouteresult);

		Intent intent = getIntent();
		startStation = (Station) intent.getSerializableExtra("startStation");
		endStation = (Station) intent.getSerializableExtra("endStation");

		initWidget();
		loadData();

	}

	private void initWidget() {
		titleTextView.setText("方案选择");
	}

	private void loadData() {

		if (startStation == null || endStation == null)
			return;

		startProgressDialog("查询中");
		AjaxParams params = new AjaxParams();
		// 1-最短路径，2-最少费用，3-最少耗时。默认type=0，表示查询所有类型
		params.put("type", "0");
		params.put("startRoad", startStation.getRoadid() + "");
		params.put("startStation", startStation.getStationid() + "");
		params.put("endRoad", endStation.getRoadid() + "");
		params.put("endStation", endStation.getStationid() + "");
		NetConnetion dao = new NetConnetion();
		dao.getRoadPlan(params, new AjaxCallBack<Object>() {

			@Override
			public void onSuccess(Object t) {
				stopProgressDialog();
				if (t != null) {
					int responseCode = -1;
					JSONObject jsonObject = null;
					try {
						jsonObject = new JSONObject((String) t);
						responseCode = jsonObject.getInt("responseCode");
						requestIdentifier = jsonObject
								.getString("requestIdentifier");
						planArray = jsonObject.getJSONArray("plan");

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					String errorMessage = "";
					if (responseCode == 0) {
						// 保存查询记录
						addQueryPathRecord();
						// 显示查询结果
						QueryDirvingRouteResultAdapter queryDirvingRouteResultAdapter = new QueryDirvingRouteResultAdapter(
								QueryDirvingRouteResultActivity.this, planArray);
						resultListView
								.setAdapter(queryDirvingRouteResultAdapter);
						tipTextView.setVisibility(View.VISIBLE);

						return;

					} else if (responseCode == 1) {
						errorMessage = "查询失败!由于高速路网无连通,需要绕行国道,导致无法规划！";
					} else if (responseCode == 5) {
						errorMessage = "无权限访问!";
					} else if (responseCode == -1) {
						errorMessage = "连接服务器失败!";
					}

					Toast toast = Toast.makeText(
							QueryDirvingRouteResultActivity.this, errorMessage,
							Toast.LENGTH_SHORT);
					toast.show();

				}

			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				stopProgressDialog();
				Toast toast = Toast.makeText(
						QueryDirvingRouteResultActivity.this, "网络请求失败",
						Toast.LENGTH_SHORT);
				toast.show();

				super.onFailure(t, errorNo, strMsg);
			}

		});

	}

	/**
	 * 保存查询记录
	 */
	private void addQueryPathRecord() {
		QueryPathRecord record = new QueryPathRecord();
		record.setStartRoadID(startStation.getRoadid() + "");
		record.setStartStationID(startStation.getStationid() + "");
		record.setStartStationName(startStation.getStationname());
		record.setEndRoadID(endStation.getRoadid() + "");
		record.setEndStationID(endStation.getStationid() + "");
		record.setEndStationName(endStation.getStationname());

		HighwayApp app = (HighwayApp) getApplication();
		FinalDb finalDb = app.getFinalDb();
		finalDb.save(record);

	}

	/**
	 * listClick 点击事件
	 * 
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public void listClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		if (planArray == null || planArray.length() == 0)
			return;

		int type = 1;
		String totalFee = "";
		String totalDistance = "";
		String totalTime = "";

		try {
			JSONObject jsonObject = planArray.getJSONObject(position);
			type = jsonObject.getInt("type");
			totalFee = jsonObject.getString("totalFee");
			totalDistance = jsonObject.getString("totalDistance");
			totalTime = jsonObject.getString("totalTime");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Intent intent = new Intent(this, DrivingRouteDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("type", type);
		bundle.putString("totalFee", totalFee);
		bundle.putString("totalDistance", totalDistance);
		bundle.putString("totalTime", totalTime);
		bundle.putString("requestIdentifier", requestIdentifier);
		intent.putExtras(bundle);
		startActivity(intent);

	}

}
