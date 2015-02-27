package com.ltkj.highway;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ltkj.highway.common.HighwayApp;
import com.ltkj.highway.config.AppConfig;
import com.ltkj.highway.journey.QueryDirvingRouteResultActivity;
import com.ltkj.highway.journey.QueryPathRecordListActivity;
import com.ltkj.highway.journey.SelStationActivity;
import com.ltkj.highway.po.QueryPathRecord;
import com.ltkj.highway.po.Station;
import com.ltkj.highway.po.WeatherInfo;
import com.ltkj.highway.utils.ChinaWeatherUtil;
import com.ltkj.highway.utils.FileUtil;
import com.ltkj.highway.utils.JsonUtil;
import com.ltkj.highway.widget.CustomToast;
import com.ltkj.highway.widget.IndexPopupWindow;
import com.ltkj.highway.widget.IndexPopupWindow.Callbacks;

/**
 * Created by wanggp on 14-9-19.
 */
public class JourneyPlanningFragment extends Fragment implements
		IndexPopupWindow.Callbacks {

	@ViewInject(id = R.id.weatherImageRelativeLayout)
	private RelativeLayout weatherImageRelativeLayout;
	@ViewInject(id = R.id.temperatureTextView)
	private TextView temperatureTextView;
	@ViewInject(id = R.id.weatherDetialTextView)
	private TextView weatherDetialTextView;
	@ViewInject(id = R.id.cityTextView)
	private TextView cityTextView;
	@ViewInject(id = R.id.refreshImageView, click = "loadWeather")
	private ImageView refreshImageView;
	@ViewInject(id = R.id.startEditText, click = "selectStation")
	private EditText startEditText;
	@ViewInject(id = R.id.endEditText, click = "selectStation")
	private EditText endEditText;
	@ViewInject(id = R.id.queryButton, click = "query")
	private Button queryButton;
	@ViewInject(id = R.id.queryPathRecordlistView, itemClick = "queryPathRecordClick")
	private ListView queryPathRecordlistView;

	/**
	 * 选择站标识位
	 */
	Boolean selStart;

	/**
	 * 选中起始站、结束站
	 */
	private Station startStation, endStation;

	/**
	 * 查询记录
	 */

	private List<QueryPathRecord> queryPathRecordList;
	private QueryPathRecordListAdapter queryPathRecordListAdapter;
	private IndexPopupWindow index = null;
	private WeatherInfo weatherInfo = new WeatherInfo();

	HighwayApp app;
	FinalDb finalDb;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_journeyplanning,
				container, false);
		FinalActivity.initInjectedView(this, view);
		app = (HighwayApp) getActivity().getApplication();
		finalDb = app.getFinalDb();
		initWidget();
		loadWeather(null);

		return view;
	}

	private void initWidget() {
		// 禁止弹出键盘
		startEditText.setInputType(InputType.TYPE_NULL);
		endEditText.setInputType(InputType.TYPE_NULL);

	}

	@Override
	public void onStart() {

		super.onStart();
		// 显示查询记录
		displayQueryPathRecord();

	}
	
	@Override  
   public void setUserVisibleHint(boolean isVisibleToUser) {  
       super.setUserVisibleHint(isVisibleToUser);  
       if (isVisibleToUser) {  
           //相当于Fragment的onResume  
    	   displayQueryPathRecord();
   			delayHandler.sendEmptyMessageDelayed(0, 100);
   			if(app.isCityChange()){ //城市改变
   				loadWeather(null);
   			}
       } else {  
           //相当于Fragment的onPause  
       }  
   } 

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		delayHandler.removeCallbacksAndMessages(null); //防止message保留handler
		if(index!=null){
			index.dismiss();
			index = null;
		}
		
	}
	

	// 延时加载引导页
	private MyHandler delayHandler = new MyHandler(JourneyPlanningFragment.this) {
		@Override
		public void handleMessage(Message msg) {
			JourneyPlanningFragment outer = mOuter.get();
			switch (msg.what) {
			case 0:
				// 引导页
				SharedPreferences mPerferences = PreferenceManager
						.getDefaultSharedPreferences(getActivity());
				String str = mPerferences.getString("JourneyPlanningFragment",
						null);
				if (str == null)
					return;
				if (str.equals("show")) {

					index = new IndexPopupWindow(
							getActivity(), "JourneyPlanningFragment");
					index.mCallbacks = (Callbacks) JourneyPlanningFragment.this;
					index.showPopupWindow(outer.temperatureTextView); //弱引用

					SharedPreferences.Editor mEditor = mPerferences.edit();
					mEditor.putString("JourneyPlanningFragment", "hide")
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
		selectStation(startEditText);
	}

	/**
	 * 选择站事件
	 * 
	 * @param view
	 */
	public void selectStation(View view) {
		if (view == startEditText) {
			selStart = true;
		} else {
			selStart = false;
		}

		Intent intent = new Intent(getActivity(), SelStationActivity.class);
		startActivityForResult(intent, Activity.RESULT_FIRST_USER);

	}

	/**
	 * 选择站后回调
	 * 
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null)
			return;
		Station station = (Station) data.getSerializableExtra("selStation");
		if (station != null) {
			if (selStart) {
				startStation = station;
				startEditText.setText(station.getStationname());
			} else {
				endStation = station;
				endEditText.setText(station.getStationname());
			}
		}

	}
	
	/**
	 * 加载天气天气预报(手动)
	 * 
	 * 
	 */
	public void loadWeather(View view) {

		 final CustomToast toast = new CustomToast(getActivity().getApplicationContext());
		 toast.show("查询天气中..", -1);

		String cityCode = "";
		String fileContent = FileUtil.getFromAssets(app,
				"data/ChinaWeatherCodeDic.json");
		try {
			JSONObject json = new org.json.JSONObject(fileContent);
			cityCode = JsonUtil.getJSONStringValue(json, app.getCity());

		} catch (Exception e) {

			e.printStackTrace();
		}

		FinalHttp finalHttp = new FinalHttp();
		String url = AppConfig.ChinaWeatherURL + cityCode + ".html";
		finalHttp.get(url, new AjaxCallBack<Object>() {
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				toast.hide();
				toast.show("加载天气失败！", Toast.LENGTH_SHORT);
			}

			@Override
			public void onSuccess(Object t) {
				try {
					JSONObject json = new org.json.JSONObject((String) t);
					JSONObject weatherinfoJson = json
							.getJSONObject("weatherinfo");

					String weather = JsonUtil.getJSONStringValue(
							weatherinfoJson, "weather");
					String img1 = JsonUtil.getJSONStringValue(weatherinfoJson,
							"img1");
					String img2 = JsonUtil.getJSONStringValue(weatherinfoJson,
							"img2");

					String temp1 = JsonUtil.getJSONStringValue(weatherinfoJson,
							"temp1");
					String temp2 = JsonUtil.getJSONStringValue(weatherinfoJson,
							"temp2");

					weatherInfo.setWeather(weather);
					weatherInfo.setImg1(img1);
					weatherInfo.setImg2(img2);
					weatherInfo.setTemp1(temp1);
					weatherInfo.setTemp2(temp2);
					// 刷新天气预报
					refreshWeather();
					
				} catch (Exception e) {
				}
				toast.hide();
			}

		});
	}

	/**
	 * 刷新天气预报UI
	 * 
	 * @param view
	 */
	public void refreshWeather() {
		if (StringUtils.isNotEmpty(weatherInfo.getWeather())) {
			temperatureTextView.setText(StringUtils.remove(
					weatherInfo.getTemp1(), "℃")
					+ "-" + weatherInfo.getTemp2());
			weatherDetialTextView.setText(weatherInfo.getWeather());
			cityTextView.setText(app.getCity());
			weatherImageRelativeLayout.setBackgroundResource(ChinaWeatherUtil
					.getWeatherImage(weatherInfo.getWeather()));
		}
	}
	

	/**
	 * 显示查询历史记录
	 */
	private void displayQueryPathRecord() {

		List<QueryPathRecord> list = finalDb.findAll(QueryPathRecord.class,
				"id desc");
		queryPathRecordList = new ArrayList<QueryPathRecord>();
		if (list.size() >= 10)
			queryPathRecordList.addAll(list.subList(0, 10));
		else
			queryPathRecordList.addAll(list);

		if (queryPathRecordListAdapter == null) {

			queryPathRecordListAdapter = new QueryPathRecordListAdapter(
					getActivity(), queryPathRecordList, true);
			queryPathRecordlistView.setAdapter(queryPathRecordListAdapter);
		} else {
			// 更新
			queryPathRecordListAdapter.setDataList(list);
			queryPathRecordListAdapter.notifyDataSetChanged();
		}

	}

	public void queryPathRecordClick(AdapterView<?> arg0, View arg1,
			int position, long arg3) {
		if (queryPathRecordList == null || queryPathRecordList.size() == 0)
			return;

		if (queryPathRecordList.size() - 1 < position) {
			// 显示更多
			Intent intent = new Intent(getActivity(),
					QueryPathRecordListActivity.class);
			getActivity().startActivity(intent);

		} else {
			// 获取点击查询记录
			QueryPathRecord record = (QueryPathRecord) queryPathRecordList
					.get(position);

			startStation = new Station();
			startStation.setRoadid(Integer.parseInt(record.getStartRoadID()));
			startStation.setStationid(Integer.parseInt(record
					.getStartStationID()));
			startStation.setStationname(record.getStartStationName());

			endStation = new Station();
			endStation.setRoadid(Integer.parseInt(record.getEndRoadID()));
			endStation.setStationid(Integer.parseInt(record.getEndStationID()));
			endStation.setStationname(record.getEndStationName());

			// 跳转到查询结果页面
			goResultActivity();
		}
	}

	/**
	 * 规划行程
	 * 
	 * @param view
	 */
	public void query(View view) {
		if (startStation == null) {
			Toast.makeText(getActivity(), "请选择起始站", Toast.LENGTH_SHORT).show();
			return;
		}

		if (endStation == null) {
			Toast.makeText(getActivity(), "请选择终点站！", Toast.LENGTH_SHORT).show();
			return;
		}

		// 跳转到查询结果页面
		goResultActivity();

	}

	private void goResultActivity() {
		Intent intent = new Intent(getActivity(),
				QueryDirvingRouteResultActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("startStation", startStation);
		bundle.putSerializable("endStation", endStation);
		intent.putExtras(bundle);
		getActivity().startActivity(intent);
	}
	
	/**
	 * 防止handler泄露的内部静态类
	 * 
	 * */
	static class MyHandler extends Handler{
		//弱引用
		public WeakReference<JourneyPlanningFragment> mOuter;
		
		public MyHandler(JourneyPlanningFragment fragment){
			mOuter = new WeakReference<JourneyPlanningFragment>(fragment);
		}
	}
	
}
