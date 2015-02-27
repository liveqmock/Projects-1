/**
 * 
 */
package com.ltkj.highway.widget;

import org.apache.commons.lang3.StringUtils;

import net.tsz.afinal.FinalActivity;
import android.os.Bundle;
import android.view.View;

import com.ltkj.highway.common.HighwayApp;

/**
 * @author wanggp
 * 
 *         2014年9月23日 下午1:44:54
 * 
 */
public class BaseActivity extends FinalActivity {

	protected HighwayApp app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (HighwayApp) getApplication();
	}

	/**
	 * 返回
	 */
	public void goBack(View view) {
		this.finish();
	}

	private CustomProgressDialog progressDialog = null;

	public void startProgressDialog(String Str) {
		if (StringUtils.isEmpty(Str))
			return;
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(this);
			progressDialog.setMessage(Str);
		}
		progressDialog.show();
	}

	public void stopProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
}
