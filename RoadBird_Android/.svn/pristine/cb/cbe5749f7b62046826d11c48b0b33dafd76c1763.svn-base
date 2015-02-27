package com.ltkj.highway.news;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.ltkj.highway.R;
import com.ltkj.highway.common.CommonFun;
import com.ltkj.highway.common.NetConnetion;
import com.ltkj.highway.utils.JsonUtil;

@SuppressLint("SimpleDateFormat")
public class FixFragment extends Fragment{
	

	@ViewInject(id = R.id.list, itemClick = "listClick")
	private ListView listView;
	private SimpleAdapter adapter;
	private List<Map<String, Object>> dataList; //原始数据
	private List<HashMap<String, Object>> showList; //显示数据
	private String pageSize = "90";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_news_accidentfix, container,false);
		FinalActivity.initInjectedView(this, view);
		loadData();
		initWidget();
		return view;
		
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		dataList = null;
		showList = null;
	}
	
	/**
	 * 初始化ListView
	 * */
	private void initWidget(){
		showList = new ArrayList<HashMap<String, Object>>();		
		adapter = new SimpleAdapter(getActivity().getApplicationContext(), showList,
				R.layout.item_news, new String[]{"main","sub","num"},
				new int[]{R.id.mainLabel,R.id.subLabel,R.id.numLabel});
		listView.setAdapter(adapter);	
	}
	
	/**
	 * 刷新ListView数据
	 * */
	private void refreshListView(){
		for(int i=0; i<dataList.size(); i++)
		{
			//去除前面的时间
			HashMap<String,Object> map = new HashMap<String,Object>();
			String str = (String) dataList.get(i).get("remark");
			String[] strArray = str.split("，",2);
			if(strArray.length == 1)
			{
				strArray = str.split(",",2);
			}
			String mainAllString = strArray[1];
			String roadEng = mainAllString.substring(0, CommonFun.ChineseWordLoc(mainAllString));
			String mainString = mainAllString.substring(CommonFun.ChineseWordLoc(mainAllString),mainAllString.length());
			map.put("main", mainString);
			map.put("num", roadEng);
			map.put("sub", dataList.get(i).get("createTime"));
			if(showList!=null)
				showList.add(map);
		}
		adapter.notifyDataSetChanged();
	}
	
	/**
	 * listView 点击事件
	 * */
	public void listClick(AdapterView<?> adapter, View v, int i, long l) {
		Map<String,Object> data = new HashMap<String,Object>();
		data = dataList.get(i);
		//跳转到详细页面
		Bundle bundle = new Bundle();
		bundle.putSerializable("newsData", (Serializable) data);
		Intent intent = new Intent(getActivity().getApplicationContext(),NewsInfoActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	/**
	 * 加载数据
	 * */
	private void loadData(){
		AjaxParams params = new AjaxParams();
		params.put("pageSize", pageSize);
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh.mm.ss");     
		String date = sDateFormat.format(new java.util.Date()); 
		params.put("queryTime", date);
		params.put("type", "2");
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
						dataList = new ArrayList<Map<String, Object>>();
						dataList = JsonUtil.getList(dataArray.toString());
						//刷新ListView数据
						refreshListView();

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
				Toast toast = Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_LONG);
				toast.show();
			}
			
		});
	}

}
