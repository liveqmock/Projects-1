/**
 * 
 */
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
import android.widget.PopupWindow;

/**
 * @author chenzy
 * @version 创建时间：2014-10-10 上午11:13:34
 * 
 */

public class FunMorePopupWindow extends PopupWindow{
	
	private View contentView;
	
	final int accidentNum = 0;
	final int fixNum = 1;
	final int tollNum = 2;
	final int serviceNum = 3;
	final int allRoadNum = 4;
	
	boolean accidentFlag = true;
	boolean fixFlag = true;
	boolean tollFlag = true;
	boolean serviceFlag = true;
	boolean allRoadFlag = true;
	
	public Callbacks mCallbacks; //实现Callbacks接口的对象

	/**
	 * 构造函数
	 * */
	public FunMorePopupWindow(final Activity context){
		LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		contentView = inflater.inflate(R.layout.popupwindow_funmore, null);
		
        this.setContentView(contentView);
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setTouchable(true);
        this.update();
        this.setAnimationStyle(R.style.funMoreAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(dw);
        
        contentView.findViewById(R.id.accidentBtn).setOnClickListener(new OnClickFunItem()); 
        contentView.findViewById(R.id.fixBtn).setOnClickListener(new OnClickFunItem()); 
        contentView.findViewById(R.id.tollBtn).setOnClickListener(new OnClickFunItem()); 
        contentView.findViewById(R.id.serviceBtn).setOnClickListener(new OnClickFunItem()); 
        contentView.findViewById(R.id.allRoadBtn).setOnClickListener(new OnClickFunItem()); 
	}
	

	/**
	 * 指定funMoreView显示位置
	 * */
	public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
        	//相对于parent控件的位移
            this.showAsDropDown(parent,0, -100);
            
        } else {
            this.dismiss();
        }
    }
	
	/**
	 * 处理funMore点击事件的接口
	 * 
	 * 代理模式
	 * */
	public interface Callbacks
	{
		public void onItemsSelected(int i);
	}
	
	
	/**
	 * 事件点击监听器
	 * */
	class OnClickFunItem implements View.OnClickListener{

		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			int viewId = view.getId();	
			switch(viewId){
			case R.id.accidentBtn:
				if(accidentFlag)
				{
					contentView.findViewById(R.id.accidentBtn).setBackgroundResource(R.drawable.accident_down);
					accidentFlag = false;
				}else
				{
					contentView.findViewById(R.id.accidentBtn).setBackgroundResource(R.drawable.accident);
					accidentFlag = true;
				}
				mCallbacks.onItemsSelected(accidentNum);
				break;
			case R.id.fixBtn:
				if(fixFlag)
				{
					contentView.findViewById(R.id.fixBtn).setBackgroundResource(R.drawable.fix_down);
					fixFlag = false;
				}else
				{
					contentView.findViewById(R.id.fixBtn).setBackgroundResource(R.drawable.fix);
					fixFlag = true;
				}
				mCallbacks.onItemsSelected(fixNum);
				break;
			case R.id.allRoadBtn:
				if(allRoadFlag)
				{
					contentView.findViewById(R.id.allRoadBtn).setBackgroundResource(R.drawable.allroad_down);
					allRoadFlag = false;
				}else
				{
					contentView.findViewById(R.id.allRoadBtn).setBackgroundResource(R.drawable.allroad);
					allRoadFlag = true;
				}
				mCallbacks.onItemsSelected(allRoadNum);
				break;
			case R.id.serviceBtn:
				if(serviceFlag)
				{
					contentView.findViewById(R.id.serviceBtn).setBackgroundResource(R.drawable.service_down);
					serviceFlag = false;
				}else
				{
					contentView.findViewById(R.id.serviceBtn).setBackgroundResource(R.drawable.service);
					serviceFlag = true;
				}
				mCallbacks.onItemsSelected(serviceNum);
				break;
			case R.id.tollBtn:
				if(tollFlag)
				{
					contentView.findViewById(R.id.tollBtn).setBackgroundResource(R.drawable.toll_down);
					tollFlag = false;
				}else
				{
					contentView.findViewById(R.id.tollBtn).setBackgroundResource(R.drawable.toll);
					tollFlag = true;
				}
				mCallbacks.onItemsSelected(tollNum);
				break;
			}
		}
		
	}
}
