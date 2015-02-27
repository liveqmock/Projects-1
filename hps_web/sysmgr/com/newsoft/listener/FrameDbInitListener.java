package com.newsoft.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.newsoft.common.spring.SpringBeanManager;
import com.newsoft.sysmanager.service.impl.InitializeDataServiceImpl;

/**
 * 初始化框架数据库
 * 
 * @author fengmq
 * 
 */
public class FrameDbInitListener implements ServletContextListener {

	private Logger logger = Logger.getLogger(FrameDbInitListener.class);

	public void contextDestroyed(ServletContextEvent servletCtxEvent) {
	}

	/**
	 * 框架数据库表初始化
	 * 
	 * @param servletCtxEvent
	 * @see
	 */
	public void contextInitialized(ServletContextEvent servletCtxEvent) {
		logger.info("---init base data...");
		initBasicData();
		logger.info("---init base data finished--。");

	}

	/*
	 * 更新系统运行需要的基础数据
	 */
	private void initBasicData() {
		InitializeDataServiceImpl initializeDataService = (InitializeDataServiceImpl) SpringBeanManager
				.getBean("initializeDataServiceImpl");
		initializeDataService.initData();
	}
}
