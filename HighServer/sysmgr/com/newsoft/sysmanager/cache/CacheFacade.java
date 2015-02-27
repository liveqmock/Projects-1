package com.newsoft.sysmanager.cache;

import java.util.List;

import com.newsoft.sysmanager.po.Operator;
import com.newsoft.sysmanager.po.Role;
import com.newsoft.sysmanager.po.User;
import com.newsoft.sysmanager.vo.RoleVo;
import com.newsoft.sysmanager.vo.UserVo;

/**
 * The global cache entrance in system management module.
 * 
 * @author guohb
 * 
 */
public interface CacheFacade {

	public List<RoleVo> getUrlMappingRoles(String url);

	public void putUrlMappingRoles(String url, List<RoleVo> roleList);

	public void discardUrlMappingRoles();

	public UserVo getSessionUserInfo(String userId);

	public void putSessionUserInfo(String userId, UserVo user);
	
	public UserVo getUserAccountInfo(String account);

	public void putUserAccountInfo(String account, UserVo user);
	
	public List<User> getAllUsers();

	public void putAllUsers(List<User> userlist);

	public List<Role> getUserRoles(String userId);

	public void discardUserRole(String userId);

	public void putUserRoles(String userId, List<Role> roleList);

	public List<Operator> getOperations(String roleId);

	public void putOperations(String roleId, List<Operator> operationList);

	public void discardRoleOperations(String roleId);

}
