package com.newsoft.common.log;


import java.util.Date;

/**
 * 统一日志管理系统日志实体表（表名：FRAME_LOG_SYSTEM）
 * 
 * @author MyEclipse Persistence Tools
 */

public class LogSystem implements java.io.Serializable {
	private static final long serialVersionUID = 1945781459966491151L;

	// Fields 
	private String logId;
	private String logThread;
	private String logLevel;
	private String operateModule;
	private Date logDate;
	private String logDes;

	// Constructors

	/** default constructor */
	public LogSystem() {
	}

	/** minimal constructor */
	public LogSystem(String logId) {
		this.logId = logId;
	}

	/** full constructor */
	public LogSystem(String logId, String logThread, String operateModule,
			String logLevel, Date logDate, String logDes) {
		this.logId = logId;
		this.logThread = logThread;
		this.operateModule = operateModule;
		this.logLevel = logLevel;
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

	public String getLogThread() {
		return this.logThread;
	}

	public void setLogThread(String logThread) {
		this.logThread = logThread;
	}


	public String getLogLevel() {
		return this.logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public String getOperateModule() {
		return operateModule;
	}

	public void setOperateModule(String operateModule) {
		this.operateModule = operateModule;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getLogDes() {
		return logDes;
	}

	public void setLogDes(String logDes) {
		this.logDes = logDes;
	}

}