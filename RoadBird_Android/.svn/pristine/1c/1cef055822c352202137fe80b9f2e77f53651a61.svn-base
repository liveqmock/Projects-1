/**
 * 
 */
package com.ltkj.highway.news;

import java.util.HashMap;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.ltkj.highway.R;
import com.ltkj.highway.utils.FileUtil;
import com.ltkj.highway.widget.BaseActivity;

/**
 * @author chenzy
 * @version 创建时间：2014-10-9 下午2:08:59
 * 
 */

public class NewsInfoActivity extends BaseActivity {

	@ViewInject(id = R.id.common_header_bar_left_item, click = "goBack")
	private Button returnButton;
	@ViewInject(id = R.id.common_header_bar_title)
	private TextView titleTextView;
	@ViewInject(id = R.id.webView)
	private WebView webView;

	private HashMap<String, Object> data;
	private String HTMLString;

	@Override
	public void goBack(View view) {
		// TODO Auto-generated method stub
		super.goBack(view);
	}

	@Override
	protected void onDestroy() {
		if (webView != null) {
			webView.removeAllViews();
			webView.destroy();
			webView = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newsinfo);

		Intent intent = getIntent();
		data = (HashMap<String, Object>) intent
				.getSerializableExtra("newsData");
		if (data.get("type").equals("1")) {
			titleTextView.setText("交通事故");
		} else {
			titleTextView.setText("道路施工");
		}

		initHTMLString();
		webView.loadDataWithBaseURL(null, HTMLString, "text/html", "utf-8",
				null);
		// webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient());
	}

	/**
	 * 替换HTML文件的内容
	 * */
	private void initHTMLString() {
		HTMLString = FileUtil.getFromAssets(this, "Html/NewsInfo.html");
		String title = data.get("roadName") + "(" + data.get("occurPlace")
				+ ")";
		HTMLString = HTMLString.replace("!$title$!", title);
		HTMLString = HTMLString.replace("!$occurTime$!", data.get("occurTime")
				.toString());
		HTMLString = HTMLString.replace("!$remark$!", data.get("remark")
				.toString());
	}

}
