package com.ltkj.highway.news;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.ltkj.highway.R;
import com.ltkj.highway.widget.CustomToast;



public class NewsActivity extends FragmentActivity{

	@ViewInject(id = R.id.common_header_bar_left_item,click="goBack")
	private Button returnButton;
	@ViewInject(id = R.id.common_header_bar_title)
	private TextView titleTextView;
	
	@ViewInject(id = R.id.rg_tab)
	private RadioGroup radioGroup;
	@ViewInject(id = R.id.accidentRadioBtn)
	private RadioButton accidentRadioBtn;
	@ViewInject(id = R.id.fixRadioBtn)
	private RadioButton fixRadioBtn;
	
	@ViewInject(id = R.id.id_viewpager)
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mFragments = new ArrayList<Fragment>();
	
	private AccidentFragment accidentFragment;
	private FixFragment fixFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		FinalActivity.initInjectedView(this);
		
		titleTextView.setText("高速资讯");
		
		
		
		accidentFragment = new AccidentFragment();
		fixFragment = new FixFragment();
		mFragments.add(accidentFragment);
		mFragments.add(fixFragment);
		
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return mFragments.size();
			}


			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return mFragments.get(arg0);
			}

		};
		// 缓存2个
			mViewPager.setOffscreenPageLimit(2);
			mViewPager.setAdapter(mAdapter);

			radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					changeButtonStyle(checkedId);
					int sel = 0;
					if (checkedId == accidentRadioBtn.getId())
						sel = 0;
					else 
						sel = 1;
					mViewPager.setCurrentItem(sel);
				}
			});
		
		
		// 默认选中“交通事故”
		radioGroup.check(accidentRadioBtn.getId());


	}
	
	
		
	/**
	 * 改变RadioButton的背景色
	 * */
	private void changeButtonStyle(int checkedId){
		
		if (checkedId == accidentRadioBtn.getId()) {
			accidentRadioBtn.setBackgroundColor(Color.parseColor("#11B264"));
			fixRadioBtn.setBackgroundColor(Color.parseColor("#ffffff"));
		} else if (checkedId == fixRadioBtn.getId()) {
			fixRadioBtn.setBackgroundColor(Color.parseColor("#11B264"));
			accidentRadioBtn.setBackgroundColor(Color.parseColor("#ffffff"));
		} 
		
	}
	
	/**
	 * 返回
	 */
	public void goBack(View view) {
		this.finish();
		System.gc();
	}
	

}
