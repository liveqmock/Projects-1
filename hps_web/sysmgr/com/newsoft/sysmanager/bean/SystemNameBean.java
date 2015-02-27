package com.newsoft.sysmanager.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能权限：digester解析SystemName节点
 * 
 * @author fengmq
 * 
 */
public class SystemNameBean {

	private String systemName;

	private List<ModelNameBean> modelNameBeanList = new ArrayList<ModelNameBean>();

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public List<ModelNameBean> getModelNameBeanList() {
		return modelNameBeanList;
	}

	public void addModelNameBeanList(ModelNameBean po) {
		this.modelNameBeanList.add(po);
	}

}
