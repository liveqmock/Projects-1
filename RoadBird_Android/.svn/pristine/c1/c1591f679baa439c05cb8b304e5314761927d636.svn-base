package com.ltkj.highway.more;

import net.tsz.afinal.annotation.view.ViewInject;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ltkj.highway.R;
import com.ltkj.highway.utils.FileUtil;
import com.ltkj.highway.widget.BaseActivity;

public class PhoneListActivity extends BaseActivity {

	@ViewInject(id = R.id.common_header_bar_left_item,click="goBack")
	private Button returnButton;
	@ViewInject(id = R.id.common_header_bar_title)
	private TextView titleTextView;
	@ViewInject(id = R.id.phone_listView)
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_list);
		titleTextView.setText("常用电话");
		// 加载电话数据
		loadData();
	}

	private void loadData() {
		String fileContent = FileUtil.getFromAssets(this, "data/Phone.json");
		JSONArray jsonArray = null;
		try {
			JSONObject json = new org.json.JSONObject(fileContent);
			jsonArray = json.getJSONArray("data");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PhoneListAdapter phoneListAdapter = new PhoneListAdapter(this,
				jsonArray);
		listView.setAdapter(phoneListAdapter);
		jsonArray = null;
		fileContent = null;
	}
	


}
