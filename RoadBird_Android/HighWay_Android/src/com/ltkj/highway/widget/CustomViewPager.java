/**
 * 
 */
package com.ltkj.highway.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author wanggp
 * 
 *         2014年11月4日 上午11:56:07
 * 
 */
public class CustomViewPager extends ViewPager {

	private boolean isCanScroll = false;

	public CustomViewPager(Context context) {
		super(context);
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setScanScroll(boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}

//	@Override
//	public void scrollTo(int x, int y) {
//		if (isCanScroll) {
//			super.scrollTo(x, y);
//		}
//	}
	
	
	//触摸没有反应就可以了
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.isCanScroll) {
            return super.onTouchEvent(event);
        }
  
        return false;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.isCanScroll) {
            return super.onInterceptTouchEvent(event);
        }
 
        return false;
    }
 
    public void setPagingEnabled(boolean enabled) {
        this.isCanScroll = enabled;
    }
}
