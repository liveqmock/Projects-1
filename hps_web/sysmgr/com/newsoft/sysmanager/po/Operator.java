package com.newsoft.sysmanager.po;

import com.newsoft.common.mybatis.Table;


/**
 * 操作（资源）表（表名：FRAME_ORGSTRUC_OPERATOR）
 * 
 * @author MyEclipse Persistence Tools
 */
@Table(name="FRAME_ORGSTRUC_OPERATOR",pk="operatorId")
public class Operator implements java.io.Serializable {
	private static final long serialVersionUID = 3349806572638125305L;
	
	// Fields
	private String operatorId;
	private String moduleName;
	private String operatorName;
	private String urlmapping;

	// Constructors

	/** default constructor */
	public Operator() {
	}

	/** minimal constructor */
	public Operator(String operatorId) {
		this.operatorId = operatorId;
	}

	/** full constructor */
	public Operator(String operatorId, String moduleName,
			String operatorName, String urlmapping) {
		this.operatorId = operatorId;
		this.moduleName = moduleName;
		this.operatorName = operatorName;
		this.urlmapping = urlmapping;
	}

	// Property accessors

	public String getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getUrlmapping() {
		return this.urlmapping;
	}

	public void setUrlmapping(String urlmapping) {
		this.urlmapping = urlmapping;
	}

}