package com.newsoft.sysmanager.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能权限：digester解析ModelName节点
 * 
 * @author fengmq
 * 
 */
public class ModelNameBean {

	private String modleName;

	private List<OperatorBean> operatorBeanList = new ArrayList<OperatorBean>();

	public String getModleName() {
		return modleName;
	}

	public void setModleName(String modleName) {
		this.modleName = modleName;
	}

	public void addOperatorBeanList(OperatorBean po) {
		this.operatorBeanList.add(po);
	}

	public List<OperatorBean> getOperatorBeanList() {
		return operatorBeanList;
	}

	public void setOperatorBeanList(List<OperatorBean> operatorBeanList) {
		this.operatorBeanList = operatorBeanList;
	}

}
