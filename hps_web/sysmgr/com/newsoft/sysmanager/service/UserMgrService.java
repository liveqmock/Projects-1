package com.newsoft.sysmanager.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.newsoft.sysmanager.po.User;
import com.newsoft.sysmanager.vo.UserVo;

/**
 * 用户管理服务接口
 * 
 * @author fengmq
 * 
 */
public interface UserMgrService {
	

	/**
	 * 从给定组织中移除用户，如果用户不属于任何组织则删除此用户。
	 * 
	 * @param userIds 用户Id数组
	 * @param orgId 所需移除的组织
	 * @return 成功返回true，失败返回false
	 * @throws Exception
	 */
	public boolean removeUserFromOrgUnit(String[] userIds, String orgId) throws Exception;
	
	/**
	 * 根据组织ID查询用户列表
	 * 
	 * @param orgId 组织ID
	 * @return 用户列表
	 */
	public List<UserVo> getUserListByOrgId(String orgId);

	/**
	 * 分页获取用户
	 * 
	 * @param request
	 */
	public String loadUserListData(HttpServletRequest request);

	public String loadApproveUserListData(HttpServletRequest request);
	/**
	 * 根据id查询用户
	 * 
	 * @param userId 用户Id
	 * @return 用户对象
	 */
	public User getUserById(String userId);

	/**
	 * 根据id查询用户
	 * 
	 * @param userId 用户Id
	 * @return 返回用户视图对象
	 */
	public UserVo getUserVoById(String userId, String defaultOrgId);

	/**
	 * 根据account获取用户
	 * 
	 * @param account 用户账户
	 * @return 用户视图对象
	 */
	public UserVo getUserByAccount(String account);
	/**
	 * * 新增用户
	 * 
	 * @param userVo 用户视图对象
	 * @return 成功返回true，失败返回false
	 * @throws Exception
	 */
	public boolean addUser(UserVo userVo) throws Exception;

	/**
	 * 更新用户，包含关联信息
	 * 
	 * @param userVo 用户视图对象
	 * @return 成功返回true，失败返回false
	 * @throws Exception
	 */
	public boolean updateUser(UserVo userVo) throws Exception;

	/**
	 * 更新用户基础数据
	 * 
	 * @param user 用户对象
	 * @return 成功返回true，失败返回false
	 * @throws Exception
	 */
	public boolean updateUser(User user) throws Exception;

	/**
	 * 更新用户默认所属的组织
	 * 
	 * @param user 用户对象
	 * @return 成功返回true，失败返回false
	 * @throws Exception
	 */
	public boolean updateUserDefaultOrgId(User user) throws Exception;

	/**
	 * 根据用户名称关键字搜索
	 * 
	 * @param orgId 所属组织Id
	 * @param keyWord 关键字
	 * @return 用户列表
	 */
	public List<User> searchUserByUserNameKeyWord(String orgId, String keyWord);

	/**
	 * 根据角色Id获取用户列表
	 * 
	 * @return 用户列表
	 */
	public List<User> getUserListByRoleId(String roleId);

	public int updateUserPoint(User user) throws Exception;
	
	public int updateUserState(String userId) throws Exception;

}
