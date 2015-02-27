package com.newsoft.security.po;

/**
 * Acl权限控制实体对象（表名：FRAME_ACL_ENTRY）
 * 
 * @author MyEclipse Persistence Tools
 */

public class AclEntry implements java.io.Serializable {
	private static final long serialVersionUID = -5614612315636136649L;
	// Fields
	private String objectId; // 数据对象Id
	private String securityId;// 权限标识，可以是用户ID、角色Id或组织机构ID
	private Integer powerType; // 类型，1：查看权限 2：管理权限
	private Integer grantCount;// 授权次数
	
	private String moduleName;//权限控制的模块名称

	// Constructors

	/** default constructor */
	public AclEntry() {
	}

	/** full constructor */
	public AclEntry(String objectId, String securityId, Integer powerType,
			Integer grantCount,String moduleName) {
		this.objectId = objectId;
		this.securityId = securityId;
		this.powerType = powerType;
		this.grantCount = grantCount;
		this.moduleName = moduleName;
	}

	// Property accessors

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public Integer getPowerType() {
		return powerType;
	}

	public void setPowerType(Integer powerType) {
		this.powerType = powerType;
	}

	public Integer getGrantCount() {
		return this.grantCount;
	}

	public void setGrantCount(Integer grantCount) {
		this.grantCount = grantCount;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

}