/**
 * 
 */
package com.newsoft.sysmanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsoft.sysmanager.cache.CacheFacade;
import com.newsoft.sysmanager.dao.RoleMgrDAO;
import com.newsoft.sysmanager.dao.RoleOperatorDAO;
import com.newsoft.sysmanager.dao.UserMgrDAO;
import com.newsoft.sysmanager.dao.UserRoleDAO;
import com.newsoft.sysmanager.po.Role;
import com.newsoft.sysmanager.po.User;
import com.newsoft.sysmanager.po.UserRole;
import com.newsoft.sysmanager.service.RoleMgrService;
import com.newsoft.sysmanager.vo.RoleVo;
import com.newsoft.utils.StringTools;
import com.newsoft.utils.UUIDTool;

/**
 * 角色管理服务类
 * 
 * @author fengmq
 * 
 */
@Service
public class RoleMgrServiceImpl implements RoleMgrService {

	@Autowired
	private RoleMgrDAO roleMgrDAO;
	@Autowired
	private UserRoleDAO userRoleDAO; 
	@Autowired
	private UserMgrDAO userMgrDAO;
	@Autowired
	private RoleOperatorDAO roleOperatorDAO;
	@Autowired
	private CacheFacade cacheFacade;

	/**
	 * 根据用户Id获取角色列表
	 * 
	 * @param userId
	 *            用户Id
	 * @return 角色列表
	 */
	public List<Role> getRoleListByUserId(String userId) {
		return roleMgrDAO.getRoleListByUserId(userId);
	}
 
	/**
	 * 根据组织ID获取角色
	 * 
	 * @param orgId
	 *            组织Id
	 * @return 角色列表
	 */
	public List<Role> getRoleListByOrgId(String orgId) {
		return roleMgrDAO.getRoleListByOrgId();
	}

	/**
	 * 根据角色Id获取角色
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 角色对象
	 */
	public Role getRoleByRoleId(String roleId) {
		return roleMgrDAO.getRoleByRoleId(roleId);
	}

	/**
	 * 根据角色Id获取角色
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 角色视图对象
	 */
	public RoleVo getRoleVoByRoleId(String roleId) {
		RoleVo roleVo = roleMgrDAO.getRoleByRoleId(roleId);
		// 通过角色Id获取角色用户
		List<User> userList = userMgrDAO.getUserListByRoleId(roleId);
		String userIds = "";
		String userNames = "";
		for (User user : userList) {
			userIds = StringTools.uniteTwoStringBySemicolon(userIds, user
					.getUserId(), ";");
			userNames = StringTools.uniteTwoStringBySemicolon(userNames, user
					.getUserName(), ";");
		}
		roleVo.setRoleUserIds(userIds);
		roleVo.setRoleUserNames(userNames);
		return roleVo;
	}

	/**
	 * 添加角色
	 * 
	 * @param role
	 *            角色对象
	 * @param roleUserIds
	 *            角色关联的用户ID
	 */
	public boolean addRole(RoleVo role) throws Exception {
		role.setRoleId(UUIDTool.getUUID());
		int result = roleMgrDAO.addRole(role);
		if (result == 0) {
			return false;
		}
		String roleUserIds = role.getRoleUserIds();
		if (!StringTools.isEmpty(roleUserIds)) {
			String[] userIds = roleUserIds.split(";");
			for (String userId : userIds) {
				UserRole userRole = new UserRole(userId, role.getRoleId());
				userRoleDAO.insertUserRoleRelation(userRole);
			}
		}
		return true;
	}

	/**
	 * 更新角色
	 * 
	 * @param role
	 *            角色对象
	 * @param roleUserIds
	 *            角色关联的用户ID
	 */
	public boolean updateRole(RoleVo role) throws Exception {
		int result = roleMgrDAO.updateRole(role);
		if (result == 0) {
			return false;
		}
		String roleUserIds = role.getRoleUserIds();
		// 先删除原有的用户角色关联关系
		userRoleDAO.deleteUserRoleRelationByRoleId(role.getRoleId());
		if (!StringTools.isEmpty(roleUserIds)) {
			// 添加新的用户角色关联关系
			String[] userIds = roleUserIds.split(";");
			for (String userId : userIds) {
				UserRole userRole = new UserRole(userId, role.getRoleId());
				userRoleDAO.insertUserRoleRelation(userRole);
			}
		}
		return true;
	}

	/**
	 * 删除指定的角色集合
	 * 
	 * @param ids
	 *            这些角色都是平级的
	 */
	public boolean deleteRoles(String[] ids) throws Exception {
		List<Role> roleList = new ArrayList<Role>();
		for (String roleId : ids) {
			Role role = roleMgrDAO.getRoleByRoleId(roleId);
			if (role.getRoleType() == 1 || role.getRoleType() == 2) {
				return false;
			}
			roleList.add(role);
		}
		for (String roleId : ids) {
			// 先删除用户角色关联
			userRoleDAO.deleteUserRoleRelationByRoleId(roleId);
			// 删除角色与资源的关联
			roleOperatorDAO.deleteRoleOperatorByRoleId(roleId);
			// 删除角色信息
			roleMgrDAO.deleteRoleById(roleId);
		}
		return true;
	}

	/**
	 * 删除指定组织下的全部角色
	 * 
	 * @param orgId
	 *            角色所属组织
	 */
	public boolean deleteRoleByOrgId(String orgId) throws Exception {
		List<Role> roleList = roleMgrDAO.getRoleListByOrgId();
		for (Role role : roleList) {
			String roleId = role.getRoleId();
			// 先删除用户角色关联
			userRoleDAO.deleteUserRoleRelationByRoleId(roleId);
			// 删除角色与资源的关联
			roleOperatorDAO.deleteRoleOperatorByRoleId(roleId);
			// 删除角色信息
			roleMgrDAO.deleteRoleById(roleId);
		}
		return true;
	}

	/**
	 * 根据角色名称关键字搜索角色
	 * 
	 * @param orgId
	 *            组织ID
	 * @param nameKeyWord
	 *            名称关键字
	 * @return
	 */
	public List<Role> searchRoleByNameKeyWord(String orgId, String nameKeyWord) {
		Role role = new Role();
		role.setRoleName("%" + nameKeyWord + "%");
		role.setOrgId(orgId);
		return roleMgrDAO.searchRoleByNameKeyWord(role);
	}

	/**
	 * 获取组织下的全体人员角色（系统角色）
	 * 
	 * @param orgId
	 * @return
	 */
	public Role getSystemRoleAllUser(String orgId) {
		List<Role> roleList = roleMgrDAO.getRoleListByOrgId();
		for (Role role : roleList) {
			if (role.getRoleType() == 2) {
				return role;
			}
		}
		// 如果未找到，返回超级管理员
		return roleMgrDAO.getRoleByRoleId(Role.ROLE_ADMIN_ID);
	}

	/**
	 * 获取组织下的管理员角色（系统角色）
	 * 
	 * @param orgId
	 * @return
	 */
	public Role getSystemRoleAdmin(String orgId) {
		List<Role> roleList = roleMgrDAO.getRoleListByOrgId();
		for (Role role : roleList) {
			if (role.getRoleType() == 1) {
				return role;
			}
		}
		// 如果未找到，返回超级管理员
		return roleMgrDAO.getRoleByRoleId(Role.ROLE_ADMIN_ID);
	}
	/**
	 * 根据用户Id获取角色列表
	 * 
	 * @param userId
	 *            用户Id
	 * @return 角色列表
	 */
	public String getRoleIdsUserId(String userId) {
		List<Role> roleList = cacheFacade.getUserRoles(userId);
		
		if(roleList==null) {
			roleList = getRoleListByUserId(userId);
			cacheFacade.putUserRoles(userId, roleList);
		}
		
		// 用户所拥有的角色名称，多个以分号隔开
		String roleIds = "";
		for (Role r : roleList) {
			roleIds = StringTools.uniteTwoStringBySemicolon(roleIds,"ROLE_" + r.getRoleId(), ";");
		}
		return roleIds;
	}
}
