package com.newsoft.common.log;

import java.util.Date;

/**
 * 日志查询的参数
 * 
 * @author mengxw
 * 
 */
public class LogQueryParam {
	private Date logStartTime;
	private Date logEndTime;

	public Date getLogStartTime() {
		return logStartTime;
	}

	public void setLogStartTime(Date logStartTime) {
		this.logStartTime = logStartTime;
	}

	public Date getLogEndTime() {
		return logEndTime;
	}

	public void setLogEndTime(Date logEndTime) {
		this.logEndTime = logEndTime;
	}
}
