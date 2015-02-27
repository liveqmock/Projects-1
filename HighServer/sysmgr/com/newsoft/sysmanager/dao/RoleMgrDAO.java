package com.newsoft.sysmanager.dao;

import java.util.List;

import com.newsoft.common.log.LogParam;
import com.newsoft.common.mybatis.BaseDAO;
import com.newsoft.sysmanager.po.Role;
import com.newsoft.sysmanager.vo.RoleVo;

/**
 * 角色管理DAO
 * 
 * @author fengmq
 * 
 */
public interface RoleMgrDAO extends BaseDAO {

	/**
	 * 获取所有角色
	 * 
	 * @return
	 */
	public List<Role> getAllRoles();

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
	public List<Role> getRoleListByOrgId();

	/**
	 * 根据角色Id获取角色
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 角色对象
	 */
	public RoleVo getRoleByRoleId(String roleId);

	/**
	 * 添加角色
	 * 
	 * @param role
	 *            角色对象
	 */
	@LogParam(logDes = "添加角色", operateModule = "角色管理")
	public int addRole(RoleVo role) throws Exception;

	/**
	 * 更新角色信息
	 * 
	 * @param role
	 *            角色对象
	 */
	@LogParam(logDes = "更新角色", operateModule = "角色管理")
	public int updateRole(RoleVo role) throws Exception;

	/**
	 * 根据角色ID删除角色
	 * 
	 * @param roleId
	 *            角色ID
	 */
	@LogParam(logDes = "删除角色", operateModule = "角色管理")
	public int deleteRoleById(String roleId) throws Exception;

	/**
	 * 根据角色名称关键字搜索角色
	 * 
	 * @param role
	 *            角色名称关键字和组织ID
	 * @return 角色列表
	 */
	public List<Role> searchRoleByNameKeyWord(Role role);

	/**
	 * Query the corresponding roles by URL
	 * 
	 * @param url
	 * @return
	 */
	public List<RoleVo> getRolesByUrl(String url);
	
	/**
	 * 根据用户Id获取包含组织机构名称的角色列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<RoleVo> getRoleAndOrgNameListByUserId(String userId);
}
