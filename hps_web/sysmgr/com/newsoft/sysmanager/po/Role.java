package com.newsoft.sysmanager.po;

import com.newsoft.common.mybatis.Table;

/**
 * 角色实体表（表名：FRAME_ORGSTRUC_ROLE）
 * 
 * @author MyEclipse Persistence Tools
 */
@Table(name="FRAME_ORGSTRUC_ROLE",pk="RoleID")
public class Role implements java.io.Serializable {
	private static final long serialVersionUID = 2624992869849892648L;
	public static transient final String ROLE_ADMIN_ID = "00000000000000001111111111111111";// 超级管理员角色ID
	public static transient final String ROLE_EXCE_MEMBER = "00000000000000002222222222222222";//普通会员角色
	public static transient final String ROLE_COM_MEMBER = "00000000000000003333333333333333";//企业会员角色
	// Fields
	private String roleId;// 角色ID
	private String roleName;// 角色名称
	private Integer roleType;// 角色类型 0：用户自定义角色 1：管理员角色 2：全体用户角色
	private String orgId;// 所属机构Id
	private String memo;// 备注

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(String roleId) {
		this.roleId = roleId;
	}

	/** full constructor */
	public Role(String roleId, String roleName, Integer roleType, String orgId,
			String memo) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleType = roleType;
		this.orgId = orgId;
		this.memo = memo;
	}

	// Property accessors

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getRoleType() {
		return this.roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}