package com.ltkj.highway.news;
/**
 * @author chenzy
 * @version 创建时间：2014-10-21 上午10:26:14
 */

import java.util.Map;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ltkj.highway.R;
import com.ltkj.highway.widget.BaseActivity;

public class ServiceActivity extends BaseActivity{
	
	@ViewInject(id = R.id.common_header_bar_left_item, click="goBack")
	private Button returnButton;
	@ViewInject(id = R.id.common_header_bar_title)
	private TextView titleTextView;
	@ViewInject(id = R.id.roadID)
	private TextView roadName;
	@ViewInject(id = R.id.serviceID)
	private TextView serviceName;
	@ViewInject(id = R.id.list)
	private ListView list;
	
	private Map<String,Object> serviceData = null;

	@Override
	public void goBack(View view) {
		// TODO Auto-generated method stub
		super.goBack(view);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service);
		
		Bundle bundle = getIntent().getExtras();
		serviceData = (Map<String, Object>) bundle.getSerializable("serviceData");
		
		titleTextView.setText(serviceData.get("RestName").toString());
		roadName.setText(bundle.getString("roadName"));
		serviceName.setText(titleTextView.getText());
		
		initWidge();
	}
	
	private void initWidge(){
		list.setVerticalScrollBarEnabled(false); //隐藏滚动条
		list.setAdapter(new ServiceListAdapter(ServiceActivity.this,serviceData));
	}
	
}
