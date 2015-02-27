package com.newsoft.sysmanager.bean;

/**
 * 功能权限：digester解析Operator节点
 * 
 * @author fengmq
 * 
 */
public class OperatorBean {

	private String operatorId;

	private String operatorName;
	
	private String urlmapping;

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getUrlmapping() {
		return urlmapping;
	}

	public void setUrlmapping(String urlmapping) {
		this.urlmapping = urlmapping;
	}

}
