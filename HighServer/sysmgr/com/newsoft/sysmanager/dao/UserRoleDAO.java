package com.newsoft.sysmanager.dao;

import java.util.List;

import com.newsoft.common.mybatis.BaseDAO;
import com.newsoft.sysmanager.po.UserRole;

/**
 * 用户角色关联DAO
 * 
 * @author fengmq
 * 
 */
public interface UserRoleDAO extends BaseDAO {
	/**
	 * 获取所有的用户角色关联关系
	 * 
	 * @return
	 */
	public List<UserRole> getAllUserRoleRelations();

	/**
	 * 插入用户角色关联
	 * 
	 * @param userRole
	 *            用户角色关联对象
	 */
	public void insertUserRoleRelation(UserRole userRole) throws Exception;

	/**
	 * 根据用户ID删除用户角色关联关系
	 * 
	 * @param userId
	 *            用户id
	 */
	public void deleteUserRoleRelationByUserId(String userId) throws Exception;

	/**
	 * 根据根据角色id删除用户角色关联关系
	 * 
	 * @param roleId
	 *            角色Id
	 */
	public void deleteUserRoleRelationByRoleId(String roleId) throws Exception;

	/**
	 * 根据用户id及角色id删除关联关系
	 * 
	 * @param userRole
	 *            用户角色关联关系
	 */
	public void deleteUserRoleRelationByUserIdAndRoleId(UserRole userRole)
			throws Exception;

	/**
	 * 根据用户的ID获得用户角色关联的列表
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 * @return List<UserRole> 用户角色关联列表
	 */
	public List<UserRole> getUserRoleRelationByUserId(String userId);

	/**
	 * 根据角色ID获得用户角色关联的列表
	 * 
	 * @param roleId
	 *            角色ID
	 * @return
	 * @return List<UserRole> 用户角色关联列表
	 */
	public List<UserRole> getUserRoleRelationByRoleId(String roleId);

}
