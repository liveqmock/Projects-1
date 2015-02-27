package com.newsoft.sysmanager.dao;

import java.util.List;

import com.newsoft.common.mybatis.BaseDAO;
import com.newsoft.sysmanager.po.RoleOperator;

public interface RoleOperatorDAO extends BaseDAO {

	/**
	 * 获取所有的角色操作关联
	 * 
	 * @return
	 */
	public List<RoleOperator> getAllRoleOperatorRelation();

	/**
	 * 根据角色ID获取操作权限
	 * 
	 * @param roleId
	 *            角色Id
	 * @return 角色操作列表
	 */
	public List<RoleOperator> getRoleOperatorByRoleId(String roleId);

	/**
	 * 根据操作ID获取操作权限
	 * 
	 * @param operatorId
	 *            操作Id
	 * @return 角色操作列表
	 */
	public List<RoleOperator> getRoleOperatorByOperatorId(String operatorId);

	/**
	 * 根据角色删除资源
	 * 
	 * @param roleId
	 *            角色ID
	 * @throws Exception
	 */
	public void deleteRoleOperatorByRoleId(String roleId) throws Exception;

	/**
	 * 根据角色删除资源
	 * 
	 * @param operatorId
	 *            操作ID
	 * @throws Exception
	 */
	public void deleteRoleOperatorByOperatorId(String operatorId)
			throws Exception;

	/**
	 * 根据角色删除资源
	 * 
	 * @param roleOperator
	 * @throws Exception
	 */
	public void deleteRoleOperator(RoleOperator roleOperator) throws Exception;

	/**
	 * 添加新的授权资源
	 * 
	 * @param roleOperator
	 *            角色操作关联对象
	 * @throws Exception
	 */
	public void addRoleOperator(RoleOperator roleOperator) throws Exception;

	/**
	 * 根据用户ID获取操作权限
	 * 
	 * @return
	 */
	public List<RoleOperator> getRoleOperatorByUserId(String userId);

}
