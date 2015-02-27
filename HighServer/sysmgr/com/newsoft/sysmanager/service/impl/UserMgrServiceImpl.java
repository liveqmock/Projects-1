/**
 * 
 */
package com.newsoft.sysmanager.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsoft.common.page.Page;
import com.newsoft.common.page.PageService;
import com.newsoft.sysmanager.cache.CacheFacade;
import com.newsoft.sysmanager.dao.RoleMgrDAO;
import com.newsoft.sysmanager.dao.UserMgrDAO;
import com.newsoft.sysmanager.dao.UserRoleDAO;
import com.newsoft.sysmanager.po.Role;
import com.newsoft.sysmanager.po.User;
import com.newsoft.sysmanager.po.UserRole;
import com.newsoft.sysmanager.service.UserMgrService;
import com.newsoft.sysmanager.vo.RoleVo;
import com.newsoft.sysmanager.vo.UserVo;
import com.newsoft.utils.PasswordEncoderUtil;
import com.newsoft.utils.StringTools;
import com.newsoft.utils.UUIDTool;

/**
 * 用户管理服务类
 * 
 * @author mengxw
 * 
 */
@Service
public class UserMgrServiceImpl implements UserMgrService {

	private static final Log logger = LogFactory.getLog(UserMgrServiceImpl.class);

	@Autowired
	private UserMgrDAO userMgrDAO;
	@Autowired
	private UserRoleDAO userRoleDAO;
	@Autowired
	private RoleMgrDAO roleMgrDAO;
	@Autowired
	private PageService pageService;

	@Autowired
	private CacheFacade cacheFacade;

	/**
	 * 根据组织ID查询用户列表
	 * 
	 * @param orgId 组织ID
	 * @return 用户列表
	 */
	public List<UserVo> getUserListByOrgId(String orgId) {
		List<UserVo> userList = userMgrDAO.getUserListByOrgUnitId(orgId);

		// 去重,当用户属于多个部门时,返回多条相同的用户记录
		List<UserVo> nList = new ArrayList<UserVo>();
		outer:
		for (UserVo userVo : userList) {
			for (UserVo user : nList) {
				if (user.getUserId().equals(userVo.getUserId())) {
					continue outer;
				}
			}
			nList.add(userVo);
		}

		return nList;
	}


	/**
	 * 根据id查询用户
	 * 
	 * @param userId 用户Id
	 * @return 返回用户对象
	 */
	public User getUserById(String userId) {
		return userMgrDAO.getUserByUserId(userId);
	}

	/**
	 * 根据id查询用户
	 * 
	 * @param userId 用户Id
	 * @return 返回用户视图对象
	 */
	public UserVo getUserVoById(String userId, String defaultOrgId) {
		UserVo user = userMgrDAO.getUserByUserId(userId);
		//假如用户所属角色
		settingUserOrgUnitsRoles(user);
		return user;
	}

	/**
	 * 分页获取用户
	 * 
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public String loadUserListData(HttpServletRequest request) {
		Page<UserVo> userData = null;
		String roleId = request.getParameter("roleId");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);

		if (!StringTools.isEmpty(roleId)) {
			// 这里还要对获取的分页数据进行处理
			userData = (Page<UserVo>) pageService.pageQuery(request, UserMgrDAO.class.getName(),
					"getUserListByRoleId", "countUserListByRoleId", params);
		} else {
			// 这里还要对获取的分页数据进行处理
			userData = (Page<UserVo>) pageService.pageQuery(request, UserMgrDAO.class.getName(),
					"getUserListByOrgUnitId4Grid", "countUser", params);
		}
		

		List<UserVo> list = getDistinctUserList(userData.getResult());
		userData.setResult(list);

		for (UserVo user : list) {
			settingUserOrgUnitsRoles(user);
		}
		return pageService.page2GridJson(userData);
	}

	/**
	 * 分页获取用户
	 * 
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public String loadApproveUserListData(HttpServletRequest request) {
		Page<UserVo> userData = null;

		Map<String, Object> params = new HashMap<String, Object>();
		userData = (Page<UserVo>) pageService.pageQuery(request, UserMgrDAO.class.getName(), "getApproveUserList", "countApproveUser", params);

		List<UserVo> list = getDistinctUserList(userData.getResult());
		userData.setResult(list);

		for (UserVo user : list) {
			settingUserOrgUnitsRoles(user);
		}
		return pageService.page2GridJson(userData);
	}

	
	private List<UserVo> getDistinctUserList(List<UserVo> userlist) {
		if (userlist == null || userlist.isEmpty()) {
			return userlist;
		}

		List<UserVo> distinctUserList = new ArrayList<UserVo>();
		Map<String, String> userIdMap = new HashMap<String, String>();
		for (UserVo userVo : userlist) {
			if (userIdMap.containsKey(userVo.getUserId())) {
				if (logger.isDebugEnabled()) {
					logger.debug("Found duplicated user with id: " + userVo.getUserId() + ", name: "
							+ userVo.getUserName());
				}
				continue;
			}
			distinctUserList.add(userVo);
			userIdMap.put(userVo.getUserId(), userVo.getAccount());
		}

		return distinctUserList;
	}

	/**
	 * 根据account获取用户
	 * 
	 * @param account 用户账户
	 * @return 用户视图对象
	 */
	public UserVo getUserByAccount(String account) {
		return userMgrDAO.getUserByAccount(account);
	}

	/**
	 * * 新增用户
	 * 
	 * @param userVo 用户视图对象
	 * @return 成功返回true，失败返回false
	 * @throws Exception
	 */
	public boolean addUser(UserVo user) throws Exception {
		if (StringTools.isEmpty(user.getUserId())) {
			user.setUserId(UUIDTool.getUUID());
		}
		// 处理默认用户密码为1
		if (null == user.getPwd() || User.DISPLAYPWD.equals(user.getPwd())) {
			user.setPwd("1");
		}
		user.setPwd(PasswordEncoderUtil.encode(user.getPwd()));
		user.setLastModifyTime(new Date());
		user.setDefaultOrgId("");
		user.setCreateTime(new Date());
		userMgrDAO.addUser(user);
		// 插入用户与角色的关联关系
		String[] roleIdArr = user.getUserRoleIds().split(";");
		for (String roleId : roleIdArr) {
			if (StringUtils.isEmpty(roleId)) {
				continue;
			}
			if (roleMgrDAO.getRoleByRoleId(roleId) == null) {
				if (logger.isWarnEnabled()) {
					logger.warn("新增用户时，分配的角色：" + roleId + "已被删除！");
				}
				continue;
			}

			UserRole userRole = new UserRole();
			userRole.setUserId(user.getUserId());
			userRole.setRoleId(roleId);
			userRoleDAO.insertUserRoleRelation(userRole);
		}
		return true;
	}

	/**
	 * 更新用户，包含关联信息
	 * 
	 * @param userVo 用户视图对象
	 * @return 成功返回true，失败返回false
	 * @throws Exception
	 */
	public boolean updateUser(UserVo user) throws Exception {
		user.setLastModifyTime(new Date());
		user.setDefaultOrgId("'");

		// 更新用户
		if (User.DISPLAYPWD.equals(user.getPwd())) {
			// 不更新密码
			user.setPwd(null);
		} else {
			// 加密用户密码
			user.setPwd(PasswordEncoderUtil.encode(user.getPwd()));
		}
		// 更新用户组织关联
		//updateOrgUserRelations(user);
		// 更新用户角色关联
		updateUserRoleRelations(user);
		userMgrDAO.updateUser(user);
		return true;
	}

	/**
	 * 更新用户角色关联
	 * 
	 * @param user
	 * @throws Exception
	 */
	private void updateUserRoleRelations(UserVo user) throws Exception {
		// 新的角色ID
		String[] roleIdArr = user.getUserRoleIds().split(";");
		// 获取用户原有的角色关联(旧的角色)
		List<UserRole> oldUserRoleRelationList = userRoleDAO.getUserRoleRelationByUserId(user.getUserId());

		// 获取用户有权限修改的公司的所有角色
		List<Role> roleList = roleMgrDAO.getRoleListByOrgId();

		// 当前登录用户有权限修改的那部分关联
		List<UserRole> canUpdateRole = new ArrayList<UserRole>();
		// 遍历获取有权限修改的关联
		for (UserRole userRoleRelation : oldUserRoleRelationList) {
			for (Role role : roleList) {
				if (role.getRoleId().equals(userRoleRelation.getRoleId())) {
					canUpdateRole.add(userRoleRelation);
				}
			}
		}
		// 删除所有有权限更新的角色关联关系
		for (UserRole userRoleRelation : canUpdateRole) {
			userRoleDAO.deleteUserRoleRelationByUserIdAndRoleId(userRoleRelation);
		}

		// 删除其所有与角色的关联关系
		userRoleDAO.deleteUserRoleRelationByUserId(user.getUserId());
		// 插入新的角色关联关系
		for (String roleId : roleIdArr) {
			if (StringUtils.isEmpty(roleId)) {
				continue;
			}
			if (roleMgrDAO.getRoleByRoleId(roleId) != null) {
				UserRole userRole = new UserRole();
				userRole.setUserId(user.getUserId());
				userRole.setRoleId(roleId);
				userRoleDAO.insertUserRoleRelation(userRole);
			} else {
				if (logger.isWarnEnabled()) {
					logger.warn("更新用户时，分配的角色：" + roleId + "已被删除！");
				}
			}
		}

		// 更新用户对应的角色缓存信息
		cacheFacade.discardUserRole(user.getUserId());
	}

	/**
	 * 更新用户基础数据
	 * 
	 * @param user 用户对象
	 * @return 成功返回true，失败返回false
	 * @throws Exception
	 */
	public boolean updateUser(User user) throws Exception {
		int result = userMgrDAO.updateUser(user);
		return result > 0;
	}

	/**
	 * 更新用户默认所属的组织
	 * 
	 * @param user 用户对象
	 * @return 成功返回true，失败返回false
	 * @throws Exception
	 */
	public boolean updateUserDefaultOrgId(User user) throws Exception {
		int result = userMgrDAO.updateUserDefaultOrgId(user);
		return result > 0;
	}

	/**
	 * 设置用户的所在组织机构和角色
	 * 
	 * @param user 用户视图对象
	 * @param defaultOrgId 登录用户当前所在的公司
	 * @return
	 */
	private void settingUserOrgUnitsRoles(UserVo user) {
		user.setUserOrgNames("");
		user.setUserOrgIds("");
		// 加入用户所属角色

		// cache the user's role list
		List<RoleVo> roleList = roleMgrDAO.getRoleAndOrgNameListByUserId(user.getUserId());
		// 用户所拥有的角色名称，多个以分号隔开
		String roleNames = "";
		String roleIds = "";
		for (RoleVo role : roleList) {
			String roleName = role.getRoleName();
			roleNames = StringTools.uniteTwoStringBySemicolon(roleNames, roleName, ";");
			roleIds = StringTools.uniteTwoStringBySemicolon(roleIds, role.getRoleId(), ";");
		}
		user.setUserRoleNames(roleNames);
		user.setUserRoleIds(roleIds);
	}

	/**
	 * 根据用户名称关键字搜索
	 * 
	 * @param orgId 所属组织Id
	 * @param keyWord 关键字
	 * @return
	 */
	public List<User> searchUserByUserNameKeyWord(String orgId, String keyWord) {
		return userMgrDAO.searchUserByUserNameKeyWord(keyWord);
	}
	
	/**
	 * 根据角色Id获取用户列表
	 * 
	 * @return 用户列表
	 */
	public List<User> getUserListByRoleId(String roleId) {
		return userMgrDAO.getUserListByRoleId(roleId);
	}
	
	
	public int updateUserPoint(User user) throws Exception {
		//更新缓存信息
		return userMgrDAO.updateUser(user);
	}
	
	/**
	 * 从给定组织中移除用户，如果用户不属于任何组织则删除此用户。
	 * 
	 * @param userIds 用户Id数组
	 * @param orgId 所需移除的组织
	 * @return 成功返回true，失败返回false
	 * @throws Exception
	 */
	public boolean removeUserFromOrgUnit(String[] userIds, String orgId) throws Exception {
		
		for (String userId : userIds) {
			// 获取用户在该公司下的所有角色
			List<Role> userRoleList = roleMgrDAO.getRoleListByUserId(userId);
			for (Role role : userRoleList) {
				UserRole userRole = new UserRole(userId, role.getRoleId());
				userRoleDAO.deleteUserRoleRelationByUserIdAndRoleId(userRole);
			}
		}
		// 最后把这些用户中，不属于任何组织的用户移除
		for (String id : userIds) {
			User user = userMgrDAO.getUserByUserId(id);
			if (user != null) {
				// 备份用户信息到历史表
				user.setLastModifyTime(new Date());
				user.setSuffix("_BAK");
				userMgrDAO.addUser(user);
				// 删除用户
				userMgrDAO.deleteUserById(id);
			}
		}
		return true;
	}
	
	public int updateUserState(String userId) throws Exception{
		return 0;
	}
}
