package com.ltkj.highway.more;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.annotation.view.ViewInject;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ltkj.highway.R;
import com.ltkj.highway.widget.BaseActivity;

public class AboutActivity extends BaseActivity {

	@ViewInject(id = R.id.common_header_bar_left_item, click = "goBack")
	private Button returnButton;
	@ViewInject(id = R.id.common_header_bar_title)
	private TextView titleTextView;
	@ViewInject(id = R.id.infoList)
	private ListView infoList;
	@ViewInject(id = R.id.checkList)
	private ListView checkList;
	
	final String[] info = {"广东利通信息科技投资有限公司出品","地址：广东省广州市萝岗区","客服热线：020-32137576"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		titleTextView.setText("关于");
		
		List<Map<String,Object>> firstList = new ArrayList<Map<String,Object>>();
		for(int i=0; i<3; i++){
			Map<String,Object>Map = new HashMap<String,Object>();
			Map.put("main", info[i]);
			Map.put("sub", "");
			firstList.add(Map);
		}
		SimpleAdapter infoAdapter = new SimpleAdapter(this, firstList, R.layout.item_list_about, new String[]{"main","sub"}, new int[]{R.id.mainTxt,R.id.subTxt});
		infoList.setAdapter(infoAdapter);
		
		List<Map<String,Object>> secList = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("main", "检查更新");
		map.put("sub", "已经是最新版本");
		secList.add(map);
		SimpleAdapter checkAdapter = new SimpleAdapter(this, secList, R.layout.item_list_about, new String[]{"main","sub"}, new int[]{R.id.mainTxt,R.id.subTxt});
		checkList.setAdapter(checkAdapter);
		
		
	}
	
	


}
