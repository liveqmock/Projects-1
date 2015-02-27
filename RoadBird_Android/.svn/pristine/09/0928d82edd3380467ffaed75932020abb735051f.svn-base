/**
 * 
 */
package com.ltkj.highway.common;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.ltkj.highway.config.AppConfig;

/**
 * @author chenzy
 * @version 创建时间：2014-10-8 下午3:47:02
 * 
 */

public class NetConnetion extends FinalHttp{
	
	
	//构造方法
	public NetConnetion(){
		this.addHeader("X-APP-Token", AppConfig.getXapptoken());
		this.addHeader("X-APP-DeviceId", "Test-01");
	}
	
	
	/**
	 * 查询道路交通事件信息
	 * */
	public void getTrafficinfo(AjaxParams params,AjaxCallBack<Object> ajaxCallBack){
		this.get(AppConfig.BaseURL+"/trafficinfo", params, ajaxCallBack);
	}
	
	/**
	 * 根据经纬度查询附近的高速路段信息
	 * */
	public void getNearroads(AjaxParams params,AjaxCallBack<Object> ajaxCallBack){
		this.get(AppConfig.BaseURL+"/nearroads", params, ajaxCallBack);
	}
	
	/**
	 * 查询规划路径方案
	 * */
	public void getRoadPlan(AjaxParams params,AjaxCallBack<Object> ajaxCallBack){
		this.get(AppConfig.BaseURL+"/roadplan", params, ajaxCallBack);
	}
	
	/**
	 * 查询规划路径方案
	 * */
	public void getRoadPlanDetail(AjaxParams params,AjaxCallBack<Object> ajaxCallBack){
		this.get(AppConfig.BaseURL+"/roadplandetail", params, ajaxCallBack);
	}
	
	/**
	 * 按路段查询附近的高速路段信息
	 * */
	public void getRoadSpeed(AjaxParams params,AjaxCallBack<Object> ajaxCallBack){
		this.get(AppConfig.BaseURL+"/roadspeed", params, ajaxCallBack);
	}
}
