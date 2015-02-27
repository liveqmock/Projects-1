package com.newsoft.common.log;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsoft.common.page.PageService;
import com.newsoft.security.acegi.SessionUtil;
import com.newsoft.sysmanager.vo.UserVo;
import com.newsoft.utils.UUIDTool;

/**
 * 框架日之服务接口的实现类
 * 
 * @author mengxw
 * 
 */
@Service
public class LogServiceImpl implements LogService {

	private static final Logger logger = Logger.getLogger(LogController.class);

	@Autowired
	private FrameLogOperateDAO frameLogOperateDAO;
	@Autowired
	private FrameLogSystemDAO frameLogSystemDAO;
	@Autowired
	private PageService pageService;

	/**
	 * 添加系统日志
	 * 
	 * @param logSystem
	 * @throws Exception
	 */
	public void addSystemLog(LogSystem logSystem) throws Exception {
		frameLogSystemDAO.addSystemLog(logSystem);
	}

	/**
	 * 删除系统日志
	 * 
	 * @param logId
	 * @throws Exception
	 */
	public void deleteSystemLog(String logId) throws Exception {
		frameLogSystemDAO.deleteSystemLog(logId);
	}

	/**
	 * 检索系统日志
	 * 
	 * @param logQueryParam
	 * @return
	 * @throws Exception
	 */
	public String getSystemLogs(HttpServletRequest request) throws Exception {
		return pageService.gridPageQuery(request, FrameLogSystemDAO.class
				.getName(), "getSystemLogs", "countSystemLogs",
				new LogQueryParam());
	}

	/**
	 * 检索用户日志
	 */
	public String getOperateLogList(HttpServletRequest request)
			throws Exception {
		return pageService.gridPageQuery(request, FrameLogOperateDAO.class
				.getName(), "getOperateLogs", "countOperateLogs",
				new LogQueryParam());
	}

	public void insertLogByPo(LogOperate logOperate) {
		try {
			frameLogOperateDAO.addOperateLog(logOperate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加操作日志的接口
	 * 
	 * @param operateModule
	 * @param logDes
	 */
	public void addLog(String operateModule, String logDes) {
		UserVo user = SessionUtil.getSessionUser();
		String userId = user.getUserId();
		String userName = user.getUserName();
		LogOperate logOperate = new LogOperate(UUIDTool.getUUID(), userId,
				userName, operateModule, "", new Date(), logDes);
		try {
			frameLogOperateDAO.addOperateLog(logOperate);
		} catch (Exception e) {
			logger.error("调用接口添加用户操作日志时出错", e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除操作日志
	 * @param logId
	 * @throws Exception
	 */
	public void deleteOperateLog(String logId) throws Exception {
		frameLogOperateDAO.deleteOperateLog(logId);
	}
	
	/**
	 * 获取日志
	 * @param logQueryParam
	 * @return
	 * @throws Exception
	 */
	public LogOperate getOperateLogByID(String logId) throws Exception {
		return frameLogOperateDAO.getOperateLogByID(logId);
	}
	
	/**
	 * 检索系统日志
	 * @param logQueryParam
	 * @return
	 * @throws Exception
	 */
	public LogSystem getSystemLogByID(String logId) throws Exception {
		return frameLogSystemDAO.getSystemLogByID(logId);
	}

}
