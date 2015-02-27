package com.ltkj.highway.journey;

import java.util.List;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ltkj.highway.QueryPathRecordListAdapter;
import com.ltkj.highway.R;
import com.ltkj.highway.common.HighwayApp;
import com.ltkj.highway.po.QueryPathRecord;
import com.ltkj.highway.widget.BaseActivity;

public class QueryPathRecordListActivity extends BaseActivity {

	@ViewInject(id = R.id.common_header_bar_left_item, click = "goBack")
	private Button returnButton;
	@ViewInject(id = R.id.common_header_bar_title)
	private TextView titleTextView;
	@ViewInject(id = R.id.common_header_bar_right_item, click = "clearRecord")
	private Button clearButton;
	@ViewInject(id = R.id.queryRecordListView)
	private ListView queryPathRecordListView;

	private HighwayApp app;
	private FinalDb finalDb;
	QueryPathRecordListAdapter adapter;
	private List<QueryPathRecord> queryPathRecordList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_querypathrecordlist);
		app = (HighwayApp) getApplication();
		finalDb = app.getFinalDb();
		initWidget();

	}

	private void initWidget() {
		titleTextView.setText("查询记录");

		clearButton.setVisibility(View.VISIBLE);
		clearButton.setWidth(LayoutParams.WRAP_CONTENT);
		clearButton.setText("清空");

		queryPathRecordList = finalDb.findAll(QueryPathRecord.class, "id desc");
		adapter = new QueryPathRecordListAdapter(this, queryPathRecordList,
				false);
		queryPathRecordListView.setAdapter(adapter);
	}

	/**
	 * 清空事件
	 * 
	 * @param view
	 */
	public void clearRecord(View view) {

		finalDb.deleteAll(QueryPathRecord.class);
		adapter.setDataList(null);
		adapter.notifyDataSetChanged();
		Toast.makeText(this, "已清空查询记录!", Toast.LENGTH_SHORT).show();

	}

}
