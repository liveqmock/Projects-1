package com.newsoft.common.log;

import java.util.List;

import com.newsoft.common.mybatis.BaseDAO;

/**
 * 系统日志的DAO接口
 * 
 * @author mengxw
 * 
 */
public interface FrameLogSystemDAO extends BaseDAO {

	/**
	 * 添加系统日志
	 * @param logSystem
	 * @throws Exception
	 */
	public void addSystemLog(LogSystem logSystem) throws Exception;

	/**
	 * 删除系统日志
	 * @param logId
	 * @throws Exception
	 */
	public void deleteSystemLog(String logId) throws Exception;

	/**
	 * 检索系统日志
	 * @param logQueryParam
	 * @return
	 * @throws Exception
	 */
	public List<LogSystem> getSystemLogs(LogQueryParam logQueryParam) throws Exception;
	/**
	 * 检索系统日志
	 * @param logQueryParam
	 * @return
	 * @throws Exception
	 */
	public LogSystem getSystemLogByID(String logId) throws Exception;
}
