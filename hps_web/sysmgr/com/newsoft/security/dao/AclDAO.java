package com.newsoft.security.dao;

import java.util.List;

import com.newsoft.common.mybatis.BaseDAO;
import com.newsoft.security.po.AclEntry;

/**
 * acl实体操作DAO
 * 
 * @author fengmq
 * 
 */
public interface AclDAO extends BaseDAO {

	/**
	 * 新增一个acl实体
	 * 
	 * @param aclEntry
	 *            acl实体对象
	 * @return
	 */
	public int addAclEntry(AclEntry aclEntry) throws Exception;

	/**
	 * 更新一个acl实体
	 * 
	 * @param aclEntry
	 *            acl实体对象
	 * @return
	 */
	public int updateAclEntry(AclEntry aclEntry) throws Exception;

	/**
	 * 根据数据对象删除acl权限信息
	 * 
	 * @param
	 * aclEntry：包含objectId和powerType。powerType为null时删除查看权限和管理权限。securityId不为空的话，值删除指定的记录
	 * @return
	 */
	public int deleteAclEntry(AclEntry aclEntry) throws Exception;

	/**
	 * 判断用户是否有数据权限
	 * 
	 * @param aclEntry
	 * @return
	 */
	public List<AclEntry> judgeUserPower(AclEntry aclEntry);

	/**
	 * 获取acl权限信息
	 * 
	 * @param aclEntry
	 * @return
	 */
	public List<AclEntry> getAclEntry(AclEntry aclEntry);
}
