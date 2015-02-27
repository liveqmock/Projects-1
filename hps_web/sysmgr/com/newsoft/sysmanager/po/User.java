package com.newsoft.sysmanager.po;

import java.util.Date;

import com.newsoft.common.mybatis.Table;


/**
 * 用户实体表（表名：FRAME_ORGSTRUC_USER）
 * 
 * @author MyEclipse Persistence Tools
 */
@Table(name="FRAME_ORGSTRUC_USER",pk="UserID")
public class User implements java.io.Serializable {
	private static final long serialVersionUID = 9096355030671204553L;
	public static transient final String ADMINISTRATOR_ID = "00000000111111111111111100000000";// 超级管理员用户ID
	public static transient final String ADMINISTRATOR_ACCOUNT = "administrator";// 超级管理员用户账户名
	public static transient final String LOGIN_MEMBER = "loginMember";
	public static transient final String DISPLAYPWD = "^default$";// 在页面显示的密码
	public static transient final String USER_GROUP_ID_NAME_SEPRATOR = "#*";
	
	
	// Fields
	private String userId;// 用户编号
	private String account;// 用户帐号
	private String pwd;// 用户密码
	private String userName;// 用户姓名
	private Boolean userState;// 用户状态,0(false)：停用 1(true)：启用
	private Boolean sex;// 性别
	private String position;// 职位
	private String mobile;// 用户手机
	private String telephone;// 办公电话
	private String email;// 邮箱
	private String defaultOrgId;// 默认机构ID，用于存储用户的隶属组织机构
	private String memo;// 备注
	
	/**
	 * 用户后缀表
	 */
	private String suffix = "";
	
	//最近一次修改时间
	private Date lastModifyTime;
	
	private Date createTime;
	
	//用户切换公司时，选择的组织机构
	private String currentOrgId; 
	//用户的扩展属性信息，json格式存储{"frontTheme":"default","backendTheme":"violet"}
	//frontTheme:前台皮肤；backendTheme:后台皮肤
	private String extend;
	
	private String fileId;
	
	private Integer point;
	
	private String weixin;

	private String qq;
	
	private Integer type;//用户类别
	private String weibo;
	private String contactPerson;
	private String contactOrgName;
	
	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactOrgName() {
		return contactOrgName;
	}

	public void setContactOrgName(String contactOrgName) {
		this.contactOrgName = contactOrgName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getWeibo() {
		return weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	// Constructors
	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String userId) {
		this.userId = userId;
	}

	/** full constructor */
	public User(String userId, String account, String pwd, String userName,
			Boolean userState, Boolean sex, String position, String mobile,
			String telephone, String email, String defaultOrgId, String memo,String fileId) {
		this.userId = userId;
		this.account = account;
		this.pwd = pwd;
		this.userName = userName;
		this.userState = userState;
		this.sex = sex;
		this.position = position;
		this.mobile = mobile;
		this.telephone = telephone;
		this.email = email;
		this.defaultOrgId = defaultOrgId;
		this.memo = memo;
		this.fileId = fileId;
	}

	// Property accessors

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getUserState() {
		return this.userState;
	}

	public void setUserState(Boolean userState) {
		this.userState = userState;
	}

	public Boolean getSex() {
		return this.sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDefaultOrgId() {
		return this.defaultOrgId;
	}

	public void setDefaultOrgId(String defaultOrgId) {
		this.defaultOrgId = defaultOrgId;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	} 

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getCurrentOrgId() {
		return currentOrgId;
	}

	public void setCurrentOrgId(String currentOrgId) {
		this.currentOrgId = currentOrgId;
	} 
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
}