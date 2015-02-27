package com.newsoft.sysmanager.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newsoft.sysmanager.po.Operator;
import com.newsoft.sysmanager.po.Role;
import com.newsoft.sysmanager.po.User;
import com.newsoft.sysmanager.vo.RoleVo;
import com.newsoft.sysmanager.vo.UserVo;

/**
 * To delegate the concrete entity cache implementation.
 * 
 * @author guohb
 * 
 */
@Component
public class CacheFacadeImpl implements CacheFacade {

	@Autowired
	private RoleCache roleCache;

	@Autowired
	private UserCache userCache;

	@Autowired
	private OperationCache operationCache;

	@Override
	public List<RoleVo> getUrlMappingRoles(String url) {
		return roleCache.getRolesByUrl(url);
	}

	@Override
	public void putUrlMappingRoles(String url, List<RoleVo> roleList) {
		roleCache.putUrlMappingRoles(url, roleList);
	}

	@Override
	public void discardUrlMappingRoles() {
		roleCache.discardUrlMappingRoles();
	}

	@Override
	public UserVo getSessionUserInfo(String userId) {
		return userCache.getSessionUserInfo(userId);
	}

	@Override
	public void putSessionUserInfo(String userId, UserVo user) {
		userCache.putSessionUserInfo(userId, user);
	}

	@Override
	public List<Role> getUserRoles(String userId) {
		return roleCache.getUserRoles(userId);
	}

	@Override
	public void putUserRoles(String userId, List<Role> roleList) {
		roleCache.putUserRoles(userId, roleList);
	}

	@Override
	public List<Operator> getOperations(String roleId) {
		return operationCache.getOperations(roleId);
	}

	@Override
	public void putOperations(String roleId, List<Operator> operationList) {
		operationCache.putOperations(roleId, operationList);
	}

	

	@Override
	public void discardRoleOperations(String roleId) {
		operationCache.discardRoleOperations(roleId);
	}

	@Override
	public void discardUserRole(String userId) {
		roleCache.discardUserRole(userId);
	}

	@Override
	public List<User> getAllUsers() {
		return userCache.getAllUsers();
	}

	@Override
	public void putAllUsers(List<User> userlist) {
		userCache.putAllUsers(userlist);
	}

	@Override
	public UserVo getUserAccountInfo(String account) {
		return userCache.getSessionUserInfo(account);
	}

	@Override
	public void putUserAccountInfo(String account, UserVo user) {
		userCache.putSessionUserInfo(account, user);
	}

}
