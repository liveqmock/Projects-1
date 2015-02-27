package com.newsoft.sysmanager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.newsoft.common.log.LogParam;
import com.newsoft.common.mybatis.BaseDAO;
import com.newsoft.sysmanager.po.User;
import com.newsoft.sysmanager.vo.UserVo;

/**
 * 用户管理DAO
 * 
 * @author fengmq
 * 
 */
public interface UserMgrDAO extends BaseDAO {

	/**
	 * 获取所有用户
	 * 
	 * @return
	 */
	public List<User> getAllUsers();

	/**
	 * 根据所属组织ID查询用户列表
	 * 
	 * @param orgId 组织机构Id
	 * @return 用户列表
	 */
	public List<UserVo> getUserListByOrgUnitId(String orgId);
	
	public int countUserBymobile(String mobile) throws Exception;
	
	public int countUserByEmail(String email) throws Exception;
	
	public int countUserByAccout(String accout) throws Exception;
	

	/**
	 * 根据角色Id查询用户列表
	 * 
	 * @param roleId 角色Id
	 * @return 用户列表
	 */
	public List<User> getUserListByRoleId(String roleId);

	/**
	 * 根据用户Id获取用户视图对象
	 * 
	 * @param userId 用户Id
	 * @return 用户视图对象
	 */
	public UserVo getUserByUserId(String userId);

	/**
	 * 根据用户账号获取用户视图对象
	 * 
	 * @param account 用户账号
	 * @return 用户视图对象
	 */
	public UserVo getUserByAccount(String account);
	
	public UserVo getUserByMobile(String mobile);
	public UserVo getUserByEmail(String email);
	public UserVo getUserByWeiXin(String weixin);
	

	/**
	 * 添加新用户
	 * 
	 * @param user 用户对象
	 * @return 返回操作影响的行数，1成功，0失败
	 */
	@LogParam(logDes = "添加用户", operateModule = "用户管理")
	public int addUser(User user);

	/**
	 * 更新用户信息
	 * 
	 * @param user 用户对象
	 * @return 返回操作影响的行数，1成功，0失败
	 */
	@LogParam(logDes = "更新用户信息", operateModule = "用户管理")
	public int updateUser(User user);
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public int updateUserPoint(User user) throws Exception;

	/**
	 * 更新用户默认所属的组织
	 * 
	 * @param user 用户对象
	 * @throws Exception
	 */
	@LogParam(logDes = "更新用户默认所在公司", operateModule = "用户管理")
	public int updateUserDefaultOrgId(User user);

	/**
	 * 根据用户Id删除用户
	 * 
	 * @param userId 用户Id
	 * @return 返回操作影响的行数，1成功，0失败
	 * @throws Exception
	 */
	@LogParam(logDes = "根据用户ID删除用户", operateModule = "用户管理")
	public int deleteUserById(String userId);

	/**
	 * 根据用户姓名关键字搜索
	 * @param userName
	 * @param queryCode
	 * @return
	 */
	public List<User> searchUserByUserNameKeyWord(@Param("userName") String userName);
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public int updateUserState(String userId) throws Exception;

}
