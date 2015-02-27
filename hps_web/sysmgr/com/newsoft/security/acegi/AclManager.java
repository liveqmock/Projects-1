/**
 * 
 */
package com.newsoft.security.acegi;

/**
 * @author fengmq
 * 
 */
public interface AclManager {

	public void addPermission(String objectId, String securityId, int powerType,String moduleName);

	public void deletePermissionByObjectId(String objectId);

	public void updateReaders(String objectId, String securityIds, String moduleName);

	public void updateAdministrators(String objectId, String securityIds,String moduleName);

	public void updateReaders(AclFieldsObject aclFieldsObject);

	public void updateAdministrators(AclFieldsObject aclFieldsObject);

	public boolean judgeUserPower(String objectId);

	public boolean judgeUserPower(String objectId, int powerType);

	public boolean judgeUserViewPower(String objectId);

	public boolean judgeUserManagePower(String objectId);

	public boolean updateGrantCount(String objectId, String securityId,
			int powerType, int offsetCount);

	/**
	 * 获取用户权限ID，包括用户ID,用户所在组织机构Id，用户所拥有角色Id，供acl查询使用
	 * 
	 * @return
	 */
	public String getUserSecurityIds();
	
	public String getAclSql(String moduleName);
}
