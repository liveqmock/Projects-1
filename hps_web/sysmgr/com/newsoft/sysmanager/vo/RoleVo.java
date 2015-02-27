/**
 * 
 */
package com.newsoft.sysmanager.vo;

import java.util.ArrayList;
import java.util.List;

import com.newsoft.common.mybatis.Table;
import com.newsoft.sysmanager.po.Operator;
import com.newsoft.sysmanager.po.Role;
import com.newsoft.sysmanager.po.User;

/**
 * 角色视图对象
 * 
 * @author fengmq
 * 
 */
@Table(name="FRAME_ORGSTRUC_ROLE",pk="RoleID")
public class RoleVo extends Role {
	private static final long serialVersionUID = 8766101892204999163L;

	private String orgName;
	private String roleUserIds;
	private String roleUserNames;

	// 角色用户
	private List<User> roleUsers;
	// 角色权限
	private List<Operator> operators;

	public RoleVo() {
	}

	public RoleVo(Role role) {
		super(role.getRoleId(), role.getRoleName(), role.getRoleType(), role
				.getOrgId(), role.getMemo());
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getRoleUserIds() {
		return roleUserIds;
	}

	public void setRoleUserIds(String roleUserIds) {
		this.roleUserIds = roleUserIds;
	}

	public String getRoleUserNames() {
		return roleUserNames;
	}

	public void setRoleUserNames(String roleUserNames) {
		this.roleUserNames = roleUserNames;
	}

	public List<User> getRoleUsers() {
		if (roleUsers == null) {
			roleUsers = new ArrayList<User>();
		}
		return roleUsers;
	}

	public void setRoleUsers(List<User> roleUsers) {
		this.roleUsers = roleUsers;
	}

	public List<Operator> getOperators() {
		if (operators == null) {
			operators = new ArrayList<Operator>();
		}
		return operators;
	}

	public void setOperators(List<Operator> operators) {
		this.operators = operators;
	}

}
