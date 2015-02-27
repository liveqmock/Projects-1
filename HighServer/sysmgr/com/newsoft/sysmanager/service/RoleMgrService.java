package com.newsoft.sysmanager.service;

import java.util.List;

import com.newsoft.sysmanager.po.Role;
import com.newsoft.sysmanager.vo.RoleVo;

/**
 * 角色管理服务接口
 * 
 * @author fengmq
 * 
 */
public interface RoleMgrService {
	/**
	 * 根据用户Id获取角色列表
	 * 
	 * @param userId
	 *            用户Id
	 * @return 角色列表
	 */
	public List<Role> getRoleListByUserId(String userId);


	/**
	 * 根据组织ID获取角色
	 * 
	 * @param orgId
	 *            组织Id
	 * @return 角色列表
	 */
	public List<Role> getRoleListByOrgId(String orgId);

	/**
	 * 根据角色Id获取角色
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 角色视图对象
	 */
	public RoleVo getRoleVoByRoleId(String roleId);

	/**
	 * 根据角色Id获取角色
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 角色对象
	 */
	public Role getRoleByRoleId(String roleId);

	/**
	 * 添加角色
	 * 
	 * @param role
	 *            角色对象
	 * @param roleUserIds
	 *            角色关联的用户ID
	 */
	public boolean addRole(RoleVo role) throws Exception;

	/**
	 * 更新角色
	 * 
	 * @param role
	 *            角色对象
	 * @param roleUserIds
	 *            角色关联的用户ID
	 */
	public boolean updateRole(RoleVo role) throws Exception;

	/**
	 * 删除指定的角色集合
	 * 
	 * @param ids
	 *            这些角色都是平级的
	 */
	public boolean deleteRoles(String[] ids) throws Exception;

	/**
	 * 删除指定组织下的全部角色
	 * 
	 * @param orgId
	 *            角色所属组织
	 */
	public boolean deleteRoleByOrgId(String orgId) throws Exception;

	/**
	 * 根据角色名称关键字搜索角色
	 * 
	 * @param orgId
	 *            组织ID
	 * @param nameKeyWord
	 *            名称关键字
	 * @return
	 */
	public List<Role> searchRoleByNameKeyWord(String orgId, String nameKeyWord);

	/**
	 * 获取组织下的全体人员角色（系统角色）
	 * 
	 * @param orgId
	 * @return
	 */
	public Role getSystemRoleAllUser(String orgId);

	/**
	 * 获取组织下的管理员角色（系统角色）
	 * 
	 * @param orgId
	 * @return
	 */
	public Role getSystemRoleAdmin(String orgId);
	
	/**
	 * 根据用户Id获取角色列表
	 * 
	 * @param userId
	 *            用户Id
	 * @return 角色列表
	 */
	public String getRoleIdsUserId(String userId);
}
