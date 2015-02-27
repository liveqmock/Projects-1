package com.newsoft.sysmanager.po;

/**
 * 用户角色关联表（表名：FRAME_ORGSTRUC_USER_ROLE）
 * 
 * @author MyEclipse Persistence Tools
 */

public class UserRole implements java.io.Serializable {
	private static final long serialVersionUID = 4127718464021898792L;
	// Fields
	private String userId; // 用户Id
	private String roleId;// 角色Id

	// Constructors

	/** default constructor */
	public UserRole() {
	}

	/** full constructor */
	public UserRole(String userId, String roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}

	// Property accessors

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}