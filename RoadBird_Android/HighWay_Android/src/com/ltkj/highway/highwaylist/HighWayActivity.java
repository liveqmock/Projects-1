package com.ltkj.highway.highwaylist;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.annotation.view.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ltkj.highway.R;
import com.ltkj.highway.utils.FileUtil;
import com.ltkj.highway.utils.JsonUtil;
import com.ltkj.highway.widget.BaseActivity;

public class HighWayActivity extends BaseActivity {

	@ViewInject(id = R.id.common_header_bar_left_item, click = "goBack")
	private Button returnButton;
	@ViewInject(id = R.id.common_header_bar_title)
	private TextView titleTextView;
	@ViewInject(id = R.id.list, itemClick ="listClick")
	private ListView listView;
	@ViewInject(id = R.id.editText)
	private EditText editText;
	
	private List<Map<String,Object>> highWayNameList; //高速路名列表
	private List<Map<String,Object>> resultNameList;
	private Map<String, Object> allStationsMap;  //所有高速路上的全部站集合
	private HighWaySearchAdapter myAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_highway);
		titleTextView.setText("高速公路列表");
		//搜索栏监听
		editText.addTextChangedListener(watcher);
		
		loadData();
		initWidget();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		allStationsMap = null;
		resultNameList = null;
		highWayNameList = null;
		System.gc();
	}
	
	/**
	 * 加载基础数据
	 * */
	public void loadData(){
		highWayNameList = JsonUtil.getList(FileUtil.getFromAssets(this, "data/Road.json"));
		allStationsMap = JsonUtil.getMap(FileUtil.getFromAssets(this, "data/StationByRoadID.json"));
	}
	
	/**
	 * 初始化ListView
	 * */
	private void initWidget(){
		myAdapter = new HighWaySearchAdapter(HighWayActivity.this,highWayNameList);
		 listView.setAdapter(myAdapter);
	}
	
	@Override
	public void goBack(View view) {
		// TODO Auto-generated method stub
		super.goBack(view);
		
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
	    	
			
			if(s.length()==0){
				myAdapter.setSearch(false);
				myAdapter.notifyDataSetChanged();
			} 
			else{
				//隐藏键盘
		    	InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);   
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); 
				
				String keyWord = editText.getText().toString();
				resultNameList = new ArrayList<Map<String,Object>>();
				for(int i=0; i<highWayNameList.size();i++){
					String str = highWayNameList.get(i).get("ROADNAME").toString();
					if(str.contains(keyWord)){
						resultNameList.add(highWayNameList.get(i));
					}
				}
				myAdapter.setResultList(resultNameList);
				myAdapter.setSearch(true);
				myAdapter.notifyDataSetChanged();
			}
	    }
	};

	/**
	 * 点击ListViewItem
	 * */
	public void listClick(AdapterView<?> adapter, View v, int i, long l){
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		if(myAdapter.isSearch()){
			dataList = myAdapter.getResultList();
		}else{
			dataList = highWayNameList;
		}
		String roadID = dataList.get(i).get("ROADID").toString();
		List<Map<String,Object>> stationList = new ArrayList<Map<String,Object>>();
		stationList = combineStation(roadID);
		
		//传递选中的高速路名，以及包含的收费站
		Intent intent = new Intent(this, HighWayInfoActivity.class);
		Bundle data = new Bundle();
		data.putSerializable("station", (Serializable) stationList);
		data.putString("roadName", dataList.get(i).get("ROADNAME").toString());
		data.putString("roadID",roadID);
		intent.putExtras(data);
		startActivity(intent);
		
	}
	
	/**
	 * 合并stationIndex相同的站
	 * */
	public List<Map<String,Object>> combineStation(String roadID){
		boolean shouldCombine = false;
		List<Map<String,Object>> stationList = JsonUtil.getList(allStationsMap.get(roadID).toString());
		try {
			//把相同StationIndex的站改名
			JSONArray stationChangeList = new JSONArray(FileUtil.getFromAssets(HighWayActivity.this, "data/StationChangeList.json"));
			for(int i=0; i<stationChangeList.length(); i++){ //查找该高速路是否需要合并站
				JSONObject eachRoad =  (JSONObject) stationChangeList.get(i);
				
				//需要合并
				if(eachRoad.get("ROADID").toString().equals(roadID)){ 
					shouldCombine = true;
					for(int j=0; j<stationList.size(); j++){ //每个站
						JSONArray changStation = (JSONArray) eachRoad.get("Stations");
						for(int k=0; k<changStation.length(); k++){ //查表
							JSONObject obj =  (JSONObject) changStation.get(k);
							String newName = obj.get("name").toString();
							JSONArray ary = (JSONArray) obj.get("ID");
							for(int id=0; id<ary.length(); id++){ //每个ID
								if(stationList.get(j).get("stationid").equals(ary.get(id))){
									stationList.get(j).put("stationname", newName);
								}
							}
							
						}
					}
					
					break;
				}
				
			}
			if(shouldCombine){
				//把同名的站改为一个
				List<Map<String,Object>> newStationList = new ArrayList<Map<String,Object>>();
				String lastName = null;
				for(int i=0; i<stationList.size(); i++){
					if(!stationList.get(i).get("stationname").toString().equals(lastName)){
						lastName = stationList.get(i).get("stationname").toString();
						newStationList.add(stationList.get(i));
					}
				}
				stationList = newStationList;
			}
			stationChangeList = null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stationList;
		
	}

}
