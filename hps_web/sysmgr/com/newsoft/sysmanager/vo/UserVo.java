package com.newsoft.sysmanager.vo;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.newsoft.common.mybatis.Table;
import com.newsoft.sysmanager.po.Role;
import com.newsoft.sysmanager.po.User;

/**
 * 用户视图对象，继承于User
 * 
 * @author fengmq
 * 
 */
@Table(name = "FRAME_ORGSTRUC_USER", pk = "UserID")
public class UserVo extends User {
	private static final long serialVersionUID = 2715072869676114803L;
	// 用户所属组织的id，多个以分号隔开
	private String userOrgIds;
	// 用户所属组织的名称，多个以分号隔开
	private String userOrgNames;
	// 用户所拥有角色的id，多个以分号隔开
	private String userRoleIds;
	// 用户所拥有角色名称，多个以分号隔开
	private String userRoleNames;
	// 用户角色
	private List<Role> userRoles;


	public List<Role> getUserRoles() {
		if (userRoles == null) {
			userRoles = new ArrayList<Role>();
		}
		return userRoles;
	}

	public void setUserRoles(List<Role> userRoles) {
		this.userRoles = userRoles;
	}

	public UserVo() {
	}

	public UserVo(User user) {
		super(user.getUserId(), user.getAccount(), user.getPwd(), user.getUserName(), user.getUserState(), user
				.getSex(), user.getPosition(), user.getMobile(), user.getTelephone(), user.getEmail(), user
				.getDefaultOrgId(), user.getMemo(),user.getFileId());
	}

	public String getUserOrgIds() {
		return userOrgIds;
	}

	public void setUserOrgIds(String userOrgIds) {
		this.userOrgIds = userOrgIds;
	}

	public String getUserRoleIds() {
		return userRoleIds;
	}

	public void setUserRoleIds(String userRoleIds) {
		this.userRoleIds = userRoleIds;
	}

	public String getUserOrgNames() {
		return userOrgNames;
	}

	public void setUserOrgNames(String userOrgNames) {
		this.userOrgNames = userOrgNames;
	}

	public String getUserRoleNames() {
		return userRoleNames;
	}

	public void setUserRoleNames(String userRoleNames) {
		this.userRoleNames = userRoleNames;
	}

	public String getExtendByKey(String key) {
		return (String) JSONObject.fromObject(this.getExtend()).get(key);
	}
}
