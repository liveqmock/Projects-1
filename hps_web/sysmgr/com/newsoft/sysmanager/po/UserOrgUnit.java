package com.newsoft.sysmanager.po;

/**
 * 用户组织机构关联表（表名：FRAME_ORGSTRUC_USER_ORGUNIT）
 * 
 * @author MyEclipse Persistence Tools
 */

public class UserOrgUnit implements java.io.Serializable {
	private static final long serialVersionUID = 299264350365958601L;

	// Fields
	private String userId;// 用户ID
	private String orgId;// 组织机构Id
	private Integer userOrder;// 用户在该组织(可能是公司，也可能是部门)下的排序号

	private Integer orderInCompany;// 用户在该公司下的排序号，为了业务需求而设计的冗余字段

	private String companyId;// 公司ID

	private String orderField;// 用于排序的字段列名,取值为：userOrder或order_in_company

	// Constructors

	/** default constructor */
	public UserOrgUnit() {
	}

	/** full constructor */
	public UserOrgUnit(String userId, String orgId) {
		this.userId = userId;
		this.orgId = orgId;
	}

	// Property accessors
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Integer getUserOrder() {
		return this.userOrder;
	}

	public void setUserOrder(Integer userOrder) {
		this.userOrder = userOrder;
	}

	public Integer getOrderInCompany() {
		return orderInCompany;
	}

	public void setOrderInCompany(Integer orderInCompany) {
		this.orderInCompany = orderInCompany;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

}