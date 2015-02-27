/**
 * 
 */
package com.newsoft.sysmanager.service;

import java.util.List;

import com.newsoft.sysmanager.po.RoleOperator;

/**
 * @author fengmq
 * 
 */
public interface RoleOperatorService {
	/**
	 * 根据角色ID获取操作权限
	 * 
	 * @return
	 */
	public List<RoleOperator> getRoleOperatorByRoleId(String roleId);

	/**
	 * 根据用户ID获取操作权限
	 * 
	 * @return
	 */
	public List<RoleOperator> getRoleOperatorByUserId(String userId);

	/**
	 * 给角色分配资源
	 * 
	 * @param roleId
	 *            角色ID
	 * @param operatorRes
	 *            操作资源列表
	 * @throws Exception
	 */
	public void insertRoleOperator(String roleId, String[] operatorRes)
			throws Exception;

	/**
	 * 根据操作ID删除角色操作关联关系
	 * 
	 * @param operatorId
	 *            操作ID
	 */
	public void deleteByOperatorId(String operatorId) throws Exception;

	/**
	 * 根据角色ID删除角色操作关联关系
	 * 
	 * @param roleId
	 *            角色ID
	 */
	public void deleteRoleOperatorByRoleId(String roleId) throws Exception;

	/**
	 * 删除角色操作关联关系
	 * 
	 * @param roleOperator
	 */
	public void deleteRoleOperator(RoleOperator roleOperator) throws Exception;

}
