package com.newsoft.security.acegi;


/**
 * @author fengmq
 * 
 */
public abstract class AclFieldsObject {
	public static String ACL_Field_Values_Separator = ";";

	public String getReaders() {
		System.err.println("error:必须重写getReaders方法才能使用acl权限控制。");
		return null;
	}

	public String getAdministrators() {
		System.err.println("error:必须重写getAdministrators方法才能使用acl权限控制。");
		return null;
	}

	/**
	 * 获取查询时主表的别名和主键名称，例如一个查询select * from TableName A where a.id =
	 * '1'，该方法需要返回的是“A.id”，即acl查询是作关联主键查询时sql的一部分
	 * 
	 * @return
	 */
//	public String getTableAliasAndPkName() {
//		System.err.println("error:必须重写getTableAliasAndPkName方法才能使用acl权限控制。");
//		return null;
//	}

	/**
	 * 获取主键值
	 * 
	 * @return
	 */
	public String getObjectId() {
		System.err.println("error:必须重写getObjectId方法才能使用acl权限控制。");
		return null;
	}
	
	/**
	 * 获得模块名
	 * @return
	 */
	public String getModuleName() {
		System.err.println("error:必须重写getModuleName方法才能使用acl权限控制。");
		return null;
	}
	
	/*public String getAclSql() {
		AclManager aclManager = (AclManager) SpringBeanManager
		.getBean("aclManagerImpl");
		System.err.println("getAclSql");
//		String sql = " select  distinct objectId from FRAME_ACL_ENTRY aclEntry where aclEntry.OBJECTID="
//			+ getTableAliasAndPkName()
//			+ " and aclEntry.SECURITYID in("
//			+ aclManager.getUserSecurityIds() + ") ";
		String sql = " select  distinct objectId from FRAME_ACL_ENTRY aclEntry "
			+ " where aclEntry.SECURITYID in("
			+ aclManager.getUserSecurityIds() + ") ";
		return sql;
//		AclManager aclManager = (AclManager) SpringBeanManager
//		.getBean("aclManagerImpl");
//		String sql = " inner join FRAME_ACL_ENTRY aclEntry on aclEntry.OBJECTID="
//			+ getTableAliasAndPkName()
//			+ " and aclEntry.SECURITYID in("
//			+ aclManager.getUserSecurityIds() + ") ";
//		return sql;
	} */
}
