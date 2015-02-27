package com.hps.rest.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.newsoft.common.mybatis.OffsetLimitInterceptor;
import com.newsoft.common.spring.SpringBeanManager;
import com.newsoft.security.acegi.AcegiHelper;
import com.newsoft.sysmanager.vo.UserVo;

public class RestBase {

	public UserVo checklogin() {
		AcegiHelper aceHelper = (AcegiHelper) SpringBeanManager.getBean("acegiHelper");
		UserVo user = aceHelper.getSessionUser();
		return user;
	}
	
	public boolean validate(String token){
		try {
			Properties props = new Properties();
			String propertyFilePath = props.getProperty("propertyFilePath", "/system.properties");
			InputStream ins = OffsetLimitInterceptor.class.getResourceAsStream(propertyFilePath);
			props.load(ins);
			String tk=props.getProperty( "X-APP-Token");
			if(token.equals(tk)){
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
