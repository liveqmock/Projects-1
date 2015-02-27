package com.newsoft.common.log;
import javax.servlet.http.HttpServletRequest;


/**
 * 框架日志服务的接口
 * @author mengxw
 *
 */
public interface LogService {

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
	public String getSystemLogs(HttpServletRequest request) throws Exception;
	
	/**
	 * 检索用户操作日志
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public String getOperateLogList(HttpServletRequest request) throws Exception;
	
	/**
	 * 添加操作日志
	 * @param logOperate
	 */
	public void insertLogByPo(LogOperate logOperate);
	
	/**
	 * 添加操作日志的接口
	 * @param operateModule
	 * @param logDes
	 */
	public void addLog(String operateModule,String logDes);
	
	/**
	 * 删除操作日志
	 * @param logId
	 * @throws Exception
	 */
	public void deleteOperateLog(String logId) throws Exception;
	
	/**
	 * 获取日志
	 * @param logQueryParam
	 * @return
	 * @throws Exception
	 */
	public LogOperate getOperateLogByID(String logId) throws Exception;
	
	/**
	 * 检索系统日志
	 * @param logQueryParam
	 * @return
	 * @throws Exception
	 */
	public LogSystem getSystemLogByID(String logId) throws Exception;
	
}
