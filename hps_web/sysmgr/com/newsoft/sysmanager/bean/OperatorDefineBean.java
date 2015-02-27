package com.newsoft.sysmanager.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能权限：digester解析OperatorDefine节点
 * 
 * @author fengmq
 * 
 */
public class OperatorDefineBean {

	private List<SystemNameBean> systemNameBeanList = new ArrayList<SystemNameBean>();

	public List<SystemNameBean> getSystemNameBeanList() {
		return systemNameBeanList;
	}

	public void addSystemNameBeanList(SystemNameBean po) {
		this.systemNameBeanList.add(po);
	}

}
