package com.ltkj.highway;

import java.util.ArrayList;
import java.util.List;

import com.ltkj.highway.common.HighwayApp;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	@ViewInject(id = R.id.rg_tab)
	private RadioGroup radioGroup;
	@ViewInject(id = R.id.realTimeTrafficRadioBtn)
	private RadioButton realTimeTrafficRadioBtn;
	@ViewInject(id = R.id.journeyPlanningRadioBtn)
	private RadioButton journeyPlanningRadioBtn;

	@ViewInject(id = R.id.id_viewpager)
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mFragments = new ArrayList<Fragment>();

	private RealTimeTrafficFragment realTimeTrafficFragment;
	private JourneyPlanningFragment journeyPlanningFragment;
	private MoreFragment moreFragment;

	private long mExitTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FinalActivity.initInjectedView(this);

		getWindow().setBackgroundDrawable(null);
		realTimeTrafficFragment = new RealTimeTrafficFragment();
		journeyPlanningFragment = new JourneyPlanningFragment();
		moreFragment = new MoreFragment();
		mFragments.add(realTimeTrafficFragment);
		mFragments.add(journeyPlanningFragment);
		mFragments.add(moreFragment);

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return mFragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return mFragments.get(arg0);
			}

		};
		// 缓存2个
		mViewPager.setOffscreenPageLimit(2);
		mViewPager.setAdapter(mAdapter);

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int sel = 0;
				if (checkedId == realTimeTrafficRadioBtn.getId())
					sel = 0;
				else if (checkedId == journeyPlanningRadioBtn.getId())
					sel = 1;
				else
					sel = 2;
				mViewPager.setCurrentItem(sel);
			}
		});

		// 默认选中“实时路况”
		radioGroup.check(realTimeTrafficRadioBtn.getId());

	}

	/**
	 * 监听返回按键
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {

//				HighwayApp highwayApp =(HighwayApp) getApplication();
				// 停止定位
				realTimeTrafficFragment.stopBDLocation();
				// 退出
				finish();
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
