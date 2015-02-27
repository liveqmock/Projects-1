package com.ltkj.highway;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SplashActivity extends Activity {

	private static final int GO_HOME = 1000;
	private static final long SPLASH_DELAY_MILLIS = 1000;

	@SuppressLint("HandlerLeak")
	private  Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				goHome();
				break;
			default:
				break;
			}

			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		handler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);

	}

	/**
	 * 跳转到主界面
	 */
	private void goHome() {
		Intent intent = new Intent(this, MainActivity.class);
		this.startActivity(intent);
		this.finish();

	}

}
