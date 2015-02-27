/**
 * 
 */
package com.newsoft.sysmanager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsoft.sysmanager.dao.RoleOperatorDAO;
import com.newsoft.sysmanager.po.RoleOperator;
import com.newsoft.sysmanager.service.RoleOperatorService;

/**
 * @author fengmq
 * 
 */
@Service
public class RoleOperatorServiceImpl implements RoleOperatorService {
	@Autowired
	private RoleOperatorDAO roleOperatorDAO;

	/**
	 * 根据角色ID获取操作权限
	 * 
	 * @return
	 */
	public List<RoleOperator> getRoleOperatorByRoleId(String roleId) {
		return roleOperatorDAO.getRoleOperatorByRoleId(roleId);
	}
	/**
	 * 根据用户ID获取操作权限
	 * 
	 * @return
	 */
	public List<RoleOperator> getRoleOperatorByUserId(String userId){
		return roleOperatorDAO.getRoleOperatorByUserId(  userId);
	}
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
			throws Exception {
		for (String operatorId : operatorRes) {
			RoleOperator roleOperator = new RoleOperator();
			roleOperator.setRoleId(roleId);
			roleOperator.setOperatorId(operatorId);
			roleOperatorDAO.addRoleOperator(roleOperator);
		}
	}

	/**
	 * 根据操作ID删除角色操作关联关系
	 * 
	 * @param operatorId
	 *            操作ID
	 * @throws Exception
	 */
	public void deleteByOperatorId(String operatorId) throws Exception {
		roleOperatorDAO.deleteRoleOperatorByOperatorId(operatorId);
	}

	/**
	 * 根据角色ID删除角色操作关联关系
	 * 
	 * @param roleId
	 *            角色ID
	 */
	public void deleteRoleOperatorByRoleId(String roleId) throws Exception {
		roleOperatorDAO.deleteRoleOperatorByRoleId(roleId);
	}
	
	/**
	 * 删除角色操作关联关系
	 * 
	 * @param roleOperator
	 */
	public void deleteRoleOperator(RoleOperator roleOperator) throws Exception{
		roleOperatorDAO.deleteRoleOperator(roleOperator);
		
	}
}
