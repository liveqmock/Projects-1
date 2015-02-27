package com.newsoft.sysmanager.po;

/**
 * 角色操作关联表（表名：FRAME_ORGSTRUC_ROLE_OPERATOR）
 * 
 * @author MyEclipse Persistence Tools
 */

public class RoleOperator implements java.io.Serializable {
	private static final long serialVersionUID = 3610313133498788755L;

	// Fields
	private String roleId; // 角色ID
	private String operatorId; // 操作ID

	// Constructors

	/** default constructor */
	public RoleOperator() {
	}

	/** full constructor */
	public RoleOperator(String roleId, String operatorId) {
		this.roleId = roleId;
		this.operatorId = operatorId;
	}

	// Property accessors

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
}