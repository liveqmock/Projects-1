package com.hps.rest.common;

import com.newsoft.common.spring.SpringBeanManager;
import com.newsoft.security.acegi.AcegiHelper;
import com.newsoft.sysmanager.vo.UserVo;

public class RestBase {

	public UserVo checklogin() {
		AcegiHelper aceHelper = (AcegiHelper) SpringBeanManager.getBean("acegiHelper");
		UserVo user = aceHelper.getSessionUser();
		return user;
	}
}
