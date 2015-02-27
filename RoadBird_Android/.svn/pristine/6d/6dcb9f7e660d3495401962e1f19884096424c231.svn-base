package com.ltkj.highway.journey;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.annotation.view.ViewInject;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.ltkj.highway.MoreFragment;
import com.ltkj.highway.R;
import com.ltkj.highway.po.Station;
import com.ltkj.highway.utils.FileUtil;
import com.ltkj.highway.utils.JsonUtil;
import com.ltkj.highway.widget.BaseActivity;
import com.ltkj.highway.widget.CustomToast;
import com.ltkj.highway.widget.IndexPopupWindow;
import com.ltkj.highway.widget.IndexPopupWindow.Callbacks;

public class SelStationActivity extends BaseActivity implements
		IndexPopupWindow.Callbacks {

	@ViewInject(id = R.id.common_header_bar_left_item, click = "goBack")
	private Button returnButton;
	@ViewInject(id = R.id.search)
	private EditText editText;
	@ViewInject(id = R.id.cityGridView, itemClick = "gridClick")
	private GridView cityGridView;
	private CityAdapter cityAdapter;
	@ViewInject(id = R.id.stationListView, itemClick = "listClick")
	private ListView stationListView;

	/**
	 * 
	 * 排序好的城市List
	 */
	private List<String> cityList;

	/**
	 * 显示的城市List
	 */
	List<String> displayCityList;

	/**
	 * 站JSON数据
	 */
	private JSONObject stationJsonObject;

	/**
	 * 站数据（包含城市、站）
	 */
	private List<Object> cityStationList = new ArrayList<Object>();
	private List<Map<String,Object>> stationList = new ArrayList<Map<String,Object>>();
	
	private SelStationAdapter selStationAdapter;
	private boolean isLoadStationFinish = false;
	private IndexPopupWindow index = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sel_station);
		//搜索栏监听
		editText.addTextChangedListener(watcher);
		startProgressDialog("获取收费站信息中..");
		initWidget();
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
		super.onDestroy();
		delayHandler.removeCallbacksAndMessages(null);
		if(index!=null){
			index.dismiss();
			index = null;
		}
		recycle();
	}


	// 延时加载引导页
	private boolean isShowFirstIndexFinish = false;
	private MyHandler delayHandler = new MyHandler(SelStationActivity.this) {
		@Override
		public void handleMessage(Message msg) {
			SelStationActivity outer = mOuter.get();
			switch (msg.what) {
			case 0:
				// 引导页
				SharedPreferences mPerferences = PreferenceManager
						.getDefaultSharedPreferences(SelStationActivity.this);
				String str = mPerferences.getString("SelStationActivity", null);
				if (str == null)
					return;
				if (str.equals("show")) {

					index = new IndexPopupWindow(
							SelStationActivity.this, "SelStationActivity_first");
					index.mCallbacks = (Callbacks) SelStationActivity.this;
					index.showPopupWindow(outer.returnButton); //弱引用

					SharedPreferences.Editor mEditor = mPerferences.edit();
					mEditor.putString("SelStationActivity", "hide").commit();
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
		if (!isShowFirstIndexFinish) {
			IndexPopupWindow index = new IndexPopupWindow(
					SelStationActivity.this, "SelStationActivity_second");
			index.mCallbacks = (Callbacks) SelStationActivity.this;
			index.showPopupWindow(returnButton);
			isShowFirstIndexFinish = true;
		}
	}
	
	//搜索栏监听
	private TextWatcher watcher = new TextWatcher() {
	    
	    @Override
	    public void onTextChanged(CharSequence s, int start, int before, int count) {
	        // TODO Auto-generated method stub

	    }
	    
	    @Override
	    public void beforeTextChanged(CharSequence s, int start, int count,
	            int after) {
	        // TODO Auto-generated method stub
	        
	    }
	    
	    @Override
	    public void afterTextChanged(Editable s) {
	        // TODO Auto-generated method stub
	    	if(s.length()!=0){
	    		//隐藏键盘
		    	InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);   
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); 
	    	}
			
			if(!isLoadStationFinish) //收费站信息未加载完成
			{
				Toast.makeText(SelStationActivity.this, "收费站数据未加载完成，请稍后再搜索..", Toast.LENGTH_SHORT).show();
			}
			else{
				if(s.length()==0){
					selStationAdapter.setSearch(false);
					selStationAdapter.setSearchResultList(null);
					selStationAdapter.notifyDataSetChanged();
				}
				else{ 
					String keyWord = editText.getText().toString();
					List<Map<String,Object>> searchResultList = new ArrayList<Map<String,Object>>();
					for(int i=0; i<stationList.size(); i++){
						if(stationList.get(i).get("STATIONNAME").toString().contains(keyWord)){
							searchResultList.add(stationList.get(i));
						}
					}
					selStationAdapter.setSearch(true);
					selStationAdapter.setSearchResultList(searchResultList);
					selStationAdapter.notifyDataSetChanged();
					
				}
			}
	    }
	};


	/**
	 * 初始化控件
	 * 
	 */
	private void initWidget() {
		/**
		 * 从json 文件中读取城市列表
		 */
		String cityString = FileUtil.getFromAssets(this, "data/City.json");
		JSONArray jsonArray = null;
		try {
			JSONObject json = new org.json.JSONObject(cityString);
			jsonArray = json.getJSONArray("citys");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 排序好的城市列表（当前位置城市排在最前）
		cityList = sortCity(jsonArray);
		// 显示的城市列表
		loadMoreCity(false);
		cityAdapter = new CityAdapter(this, displayCityList);
		cityGridView.setAdapter(cityAdapter);
		cityGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		cityAdapter.setSeclection(0);

		// 异步显示站列表
		StationListViewTask stationTask = new StationListViewTask();
		stationTask.execute("");

		cityString = null;
	}

	/**
	 * 异步显示站列表
	 * 
	 * 
	 */
	class StationListViewTask extends AsyncTask<Object, Object, Object> {

		@Override
		protected Object doInBackground(Object... params) {
			initStationData();
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			selStationAdapter = new SelStationAdapter(
					SelStationActivity.this,
					android.R.layout.simple_expandable_list_item_1,
					cityStationList);
			selStationAdapter.setOriginalList(cityStationList);
			stationListView.setAdapter(selStationAdapter);
			isLoadStationFinish = true;
			stopProgressDialog();
		}

	}

	/**
	 * 排序城市，当前城市排在最前
	 * 
	 */
	private List<String> sortCity(JSONArray jsonArray) {
		List<String> list = new ArrayList<String>();
		String curCity = app.getCity();
		list.add(curCity);
		if (jsonArray != null && jsonArray.length() > 0) {
			for (int i = 0; i < jsonArray.length(); i++) {
				String city = null;
				try {
					city = jsonArray.getString(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (!curCity.equals(city) && StringUtils.isNotEmpty(city))
					list.add(city);
			}
		}
		return list;

	}

	/**
	 * 初始化站数据
	 */
	private void initStationData() {
		String stationByCityString = FileUtil.getFromAssets(this,
				"data/StationByCity.json");
		try {
			stationJsonObject = new org.json.JSONObject(stationByCityString);
			for (String city : cityList) {
				cityStationList.add(city);
				String jsonStr = stationJsonObject.get(city).toString();
				JSONArray jsonArray = stationJsonObject.getJSONArray(city);
				stationList.addAll(JsonUtil.getList(jsonStr));
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject stationJsonObject = jsonArray.getJSONObject(i);
					cityStationList.add(stationJsonObject);
				}
			}
			stationJsonObject = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stationByCityString = null;

	}

	/**
	 * GridView 点击事件
	 * 
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public void gridClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {

		String cityString = displayCityList.get(position);
		if ((displayCityList.size() - 1) == position) {

			if (cityString.equals("更多")) {
				loadMoreCity(true);
			} else {
				loadMoreCity(false);
			}
			cityAdapter.setDataList(displayCityList);

		} else {
			editText.setText(""); //恢复为非搜索状态
			
			cityAdapter.setSeclection(position);
			int srcollPosition = getPositionScrollListView(cityString);
			stationListView.setSelection(srcollPosition);

		}
		cityAdapter.notifyDataSetChanged();
	}

	private void loadMoreCity(boolean more) {
		// 加载更多
		if (more) {
			displayCityList = new ArrayList<String>();
			displayCityList.addAll(cityList);
			displayCityList.add("隐藏");
		} else {
			displayCityList = new ArrayList<String>();
			displayCityList.addAll(cityList.subList(0, 8));
			displayCityList.add("更多");
		}
	}

	/**
	 * 根据城市获取ListView滚动位置
	 * 
	 * @param city
	 */
	private int getPositionScrollListView(String city) {
		int position = 0;
		for (int i = 0; i < cityStationList.size(); i++) {
			Object object = cityStationList.get(i);
			if (object instanceof String) {
				if (((String) object).equals(city)) {
					position = i;
					break;
				}
			}
		}

		return position;

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
		Intent intent = new Intent();
		Station station = new Station();
		if(!selStationAdapter.isSearch()){
			JSONObject selStation = (JSONObject) cityStationList.get(position);
			station.jsonToStation(selStation);
		}else{
			station.mapToStation(selStationAdapter.getSearchResultList().get(position));
		}
		intent.putExtra("selStation", station);
		setResult(RESULT_OK, intent);// 需要提前定义变量resultCode，初始值大于0
		finish();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null)
			return;

		setResult(RESULT_OK, data);
		finish();

	}

	private void recycle(){
		cityList.clear();
		displayCityList.clear();
		cityStationList.clear();
		stationList.clear(); 
		
		stationList = null;
		displayCityList=null;
		cityList = null;
		cityStationList=null;
		
		System.gc();
	}
	
	/**
	 * 防止handler泄露的内部静态类
	 * 
	 * */
	static class MyHandler extends Handler{
		//弱引用
		public WeakReference<SelStationActivity> mOuter;
		
		public MyHandler(SelStationActivity activity){
			mOuter = new WeakReference<SelStationActivity>(activity);
		}
	}
}
