package com.ltkj.highway.widget;

import com.ltkj.highway.R;
import com.ltkj.highway.R.drawable;
import com.ltkj.highway.R.id;
import com.ltkj.highway.R.layout;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;

/**
 * @author chenzy
 * @version 创建时间：2014-10-30 下午2:24:07
 */

public class IndexPopupWindow extends PopupWindow{

	private View contentView;
	public Callbacks mCallbacks; //实现Callbacks接口的对象
	
	/**
	 * 构造方法
	 * */
	public IndexPopupWindow(final Activity context,String className){
		LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		contentView = inflater.inflate(R.layout.popupwindow_index, null);
		
		this.setContentView(contentView);
		//设置popupwindow的宽高，不能省
		this.setWidth(LayoutParams.MATCH_PARENT);
	    this.setHeight(LayoutParams.MATCH_PARENT);
		
		this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setTouchable(true);
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(dw);
        
        ImageView imageView = (ImageView) contentView.findViewById(R.id.image);
        imageView.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				IndexPopupWindow.this.dismiss();
				mCallbacks.indexGone(); //代理方法
			}
        	
        });
        
        //显示引导页的图片
        if(className.equals("RealTimeTrafficFragment")){
        	imageView.setImageResource(R.drawable.realtimetraffic_index);
        }else if(className.equals("MoreFragment")){
        	imageView.setImageResource(R.drawable.more_index);
        }else if(className.equals("JourneyPlanningFragment")){
        	imageView.setImageResource(R.drawable.journeyplanning_index);
        }else if(className.equals("SelStationActivity_first")){
        	imageView.setImageResource(R.drawable.selstation_index_one);
        }else if(className.equals("SelStationActivity_second")){
        	imageView.setImageResource(R.drawable.selstation_index_two);
        }else if(className.equals("DrivingRouteDetailActivity")){
        	imageView.setImageResource(R.drawable.dirvingroutedetail_index);
        }
        
	}
	
	/**
	 * 指定Index显示位置
	 * */
	public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
        	//相对于parent控件的位移
            this.showAsDropDown(parent,0, 0);
        } else {
            this.dismiss();
        }
    }
	
	/**
	 * 引导页消失时调用的接口
	 * 
	 * */
	public interface Callbacks
	{
		public void indexGone();
	}
}
