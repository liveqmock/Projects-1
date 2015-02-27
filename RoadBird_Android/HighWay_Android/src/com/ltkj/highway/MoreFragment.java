package com.ltkj.highway;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.ltkj.highway.more.AboutActivity;
import com.ltkj.highway.more.MoreListAdapter;
import com.ltkj.highway.more.PhoneListActivity;
import com.ltkj.highway.news.NewsActivity;
import com.ltkj.highway.utils.PhoneCallUtil;
import com.ltkj.highway.widget.IndexPopupWindow;
import com.ltkj.highway.widget.IndexPopupWindow.Callbacks;

/**
 * Created by wanggp on 13-9-23.
 */
public class MoreFragment extends Fragment implements IndexPopupWindow.Callbacks{

	private List<HashMap<String, Object>> dataList;
	@ViewInject(id = R.id.morelistView, itemClick = "listClick")
	private ListView listView;
	@ViewInject(id = R.id.rescueButton, click = "rescue")
	private Button rescueButton;
	private MoreListAdapter moreListAdapter;
	private IndexPopupWindow index;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_more, container, false);
		FinalActivity.initInjectedView(this, view);
		initWidget();
		return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		delayHandler.sendEmptyMessageDelayed(0, 100);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		delayHandler.removeCallbacksAndMessages(null); //防止message对handler的引用
		if(index!=null){
			index.dismiss();
			index = null;
		}
	}
	
	private void initWidget() {
		dataList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("img", R.drawable.more_message);
		map.put("title", "高速资讯");
		dataList.add(map);

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.tel);
		map.put("title", "常用电话");
		dataList.add(map);

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.more_about);
		map.put("title", "关于");
		dataList.add(map);

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.more_help);
		map.put("title", "帮助");
		dataList.add(map);

		moreListAdapter = new MoreListAdapter(getActivity(), dataList);
		listView.setAdapter(moreListAdapter);

	}

	/**
	 * 一键救援点击响应事件
	 * 
	 * @param view
	 */
	public void rescue(View view) {

		String rescueMobile = "020-96998";
		PhoneCallUtil
				.makePhoneCall(getActivity(), "广东省交通集团的监控中心", rescueMobile);

	}

	/**
	 * listView 点击事件
	 * 
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public void listClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch ((int) arg3) {
		case 0:
			// 高速咨询
			Intent newsIntent = new Intent(this.getActivity(),
					NewsActivity.class);
			getActivity().startActivity(newsIntent);
			break;
		case 1:// 常用电话
			Intent intent = new Intent(this.getActivity(),
					PhoneListActivity.class);
			getActivity().startActivity(intent);
			break;
		case 2:
			// 关于
			Intent aboutIntent = new Intent(this.getActivity(),
					AboutActivity.class);
			getActivity().startActivity(aboutIntent);

			break;
		case 3:
			//帮助
			showDialog();
			break;

		default:
			break;
		}

	}
	
	/**
	 * 显示帮助会话框
	 * */
	private void showDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(MoreFragment.this.getActivity())
		 .setMessage("确定开启操作指南？")
		 .setTitle("温馨提示")
		 .setPositiveButton("确认", new OnClickListener() {

		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			showIndex();
			
			//马上显示"更多"引导页
			IndexPopupWindow index = new IndexPopupWindow(getActivity(),"MoreFragment");
			index.mCallbacks = (Callbacks) MoreFragment.this;
    		index.showPopupWindow(listView);
    		SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(getActivity());  
    		SharedPreferences.Editor mEditor = mPerferences.edit();
   		 	mEditor.putString("MoreFragment", "hide").commit();
		}
		})
		 .setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				hideIndex();
			}
		
		 });
		 builder.create().show();
	}
	
	/**
	 * 显示引导页
	 * */
	public void showIndex(){
		SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(getActivity()); 
	        //修改数据  
		 SharedPreferences.Editor mEditor = mPerferences.edit();
		 mEditor.putString("RealTimeTrafficFragment", "show").commit();
		 mEditor.putString("MoreFragment", "show").commit();
		 mEditor.putString("JourneyPlanningFragment", "show").commit();
		 mEditor.putString("DrivingRouteDetailActivity", "show").commit();
		 mEditor.putString("SelStationActivity", "show").commit();
	          
	}
	
	/**
	 * 关闭引导页
	 * */
	public void hideIndex(){
		SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(getActivity()); 
        //修改数据  
		 SharedPreferences.Editor mEditor = mPerferences.edit();
		 mEditor.putString("RealTimeTrafficFragment", "hide").commit();
		 mEditor.putString("MoreFragment", "hide").commit();
		 mEditor.putString("JourneyPlanningFragment", "hide").commit();
		 mEditor.putString("DrivingRouteDetailActivity", "hide").commit();
		 mEditor.putString("SelStationActivity", "hide").commit();
	}


	//延时加载引导页
	private MyHandler delayHandler = new MyHandler(MoreFragment.this){  
	    @Override  
	    public void handleMessage(Message msg) { 
	    	MoreFragment outer = mOuter.get();
	        switch (msg.what) {  
	        case 0:  
	    		//引导页
	        	SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(getActivity());  
	        	String str = mPerferences.getString("MoreFragment", null);
	        	if(str==null) return;
	        	if(str.equals("show")){
	    			
	    			index = new IndexPopupWindow(getActivity(),"MoreFragment");
	    			index.mCallbacks = (Callbacks) MoreFragment.this;
		    		index.showPopupWindow(outer.listView); //弱引用
		    		
		    		SharedPreferences.Editor mEditor = mPerferences.edit();
		   		 	mEditor.putString("MoreFragment", "hide").commit();
	    		}
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
		rescue(null);
	}
	
	/**
	 * 防止handler泄露的内部静态类
	 * 
	 * */
	static class MyHandler extends Handler{
		//弱引用
		public WeakReference<MoreFragment> mOuter;
		
		public MyHandler(MoreFragment fragment){
			mOuter = new WeakReference<MoreFragment>(fragment);
		}
	}
}
