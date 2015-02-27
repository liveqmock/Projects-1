package com.newsoft.common.log;


import java.util.Date;

/**
 * 统一日志管理操作日志实体表（表名：FRAME_LOG_OPERATE）
 * 
 * @author MyEclipse Persistence Tools
 */

public class LogOperate implements java.io.Serializable {
	private static final long serialVersionUID = -6855444643281469899L;

	// Fields
	private String logId;
	private String userId;
	private String userName;
	private String operateModule;
	private String operateType;
	private Date logDate;
	private String logDes;

	// Constructors

	/** default constructor */
	public LogOperate() {
	}

	/** minimal constructor */
	public LogOperate(String logId) {
		this.logId = logId;
	}

	/** full constructor */
	public LogOperate(String logId, String userId, String userName,
			String operateModule, String operateType, Date logDate,
			String logDes) {
		this.logId = logId;
		this.userId = userId;
		this.userName = userName;
		this.operateModule = operateModule;
		this.operateType = operateType;
		this.logDate = logDate;
		this.logDes = logDes;
	}

	// Property accessors

	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOperateModule() {
		return this.operateModule;
	}

	public void setOperateModule(String operateModule) {
		this.operateModule = operateModule;
	}

	public String getOperateType() {
		return this.operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public Date getLogDate() {
		return this.logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getLogDes() {
		return this.logDes;
	}

	public void setLogDes(String logDes) {
		this.logDes = logDes;
	}

}