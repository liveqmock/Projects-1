package com.newsoft.common.spring;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.newsoft.security.acegi.SessionUtil;

/**
 * 基础Controller,主要处理日程格式的数据封装
 * 
 * @author fengmq
 * 
 */
public class BaseController {

	protected Log logger = LogFactory.getLog(getClass());

	/**
	 * 切入处理日期类型的值
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 获取公司层级码的公共类
	 * 
	 * @return
	 */
//	public String getOrgLevelCode() {
//		return SessionUtil.getSessionUserDefaultOrgLevelCode();
//	}

	/**
	 * 获取公司主键的公共类
	 * 
	 * @return
	 */
	public String getOrgId() {
		return SessionUtil.getSessionUser().getDefaultOrgId();
	}

	/**
	 * 获取公司名称的公共类
	 * 
	 * @return
	 */
	@Deprecated
	public String getOrgName() {
		return null;
	}
}
