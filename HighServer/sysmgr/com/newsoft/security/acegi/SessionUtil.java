package com.newsoft.security.acegi;

import com.newsoft.common.spring.SpringBeanManager;
import com.newsoft.sysmanager.vo.UserVo;

/**
 * 
 * Session工具类
 * 
 * 
 * @author fengmq
 * 
 */
public class SessionUtil {

	public static AcegiHelper getAcegiHelper() {
		return (AcegiHelper) SpringBeanManager.getBean("acegiHelper");
	}

	/**
	 * 获取当前session的用户信息
	 * 
	 * @return
	 */
	public static UserVo getSessionUser() {
		return getAcegiHelper().getSessionUser();
	}

	/**
	 * 获取当前登录用户Id
	 * 
	 * @return
	 */
	public static String getSessionUserId() {
		return getSessionUser().getUserId();
	}

	/**
	 * 获取当前登录用户账号
	 * 
	 * @return
	 */
	public static String getUserUserAccount() {
		return getSessionUser().getAccount();
	}

	/**
	 * 根据sessionId获取session用户
	 * 
	 * @param sessionId
	 * @return
	 */
	public static UserVo getSessionUserById(String sessionId) {
		return getAcegiHelper().getSessionUserBySessionId(sessionId);
	}

}
