package com.newsoft.common.log;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 日志组件的控制类
 * 
 * @author mengxw
 * 
 */
@Controller
@RequestMapping("/log")
public class LogController {

	private static final Logger logger = Logger.getLogger(LogController.class);
	@Autowired
	private LogService logService;

	@RequestMapping
	public String logMainPage() {
		return "/frame/system/logMgr/main";
	}

	@RequestMapping("logTypeTreePage")
	public String logTypeTree() {
		return "/frame/system/logMgr/typeTree";
	}

	@RequestMapping("logList")
	public String logListPage(String logType) {
		if ("system".equals(logType)) {
			return "/frame/system/logMgr/systemList";
		} else {
			return "/frame/system/logMgr/operateList";
		}

	}

	@RequestMapping("loadLogList")
	public void loadLogList(PrintWriter writer, String logType,
			HttpServletRequest request) {
		String json = "";
		try {
			if ("system".equals(logType)) {
				json = logService.getSystemLogs(request);
			} else {
				json = logService.getOperateLogList(request);
			}
		} catch (Exception e) {
			logger.error("检索日志列表时出错！", e);
			e.printStackTrace();
		}

		writer.write(json);
	}

	@RequestMapping("deleteLog")
	public void deleteLog(PrintWriter writer, String logIds, String logType) {
		String[] ids = logIds.split(",");
		try {
			if ("system".equals(logType)) {
				for (String id : ids) {
					logService.deleteSystemLog(id);
				}
			} else {
				for (String id : ids) {
					logService.deleteOperateLog(id);
				}
			}
			writer.write("{\"success\":true}");
		} catch (Exception e) {
			logger.error("删除日志列表时出错！", e);
			writer.write("{\"success\":false}");
			e.printStackTrace();
		}
	}

	@RequestMapping("viewLog")
	public String viewLog(Map<String, Object> map, String logId, String logType) {
		try {
			if ("system".equals(logType)) {
				LogSystem logSystem = logService.getSystemLogByID(logId);
				map.put("log", logSystem);
			} else {
				LogOperate logOperate = logService.getOperateLogByID(logId);
				map.put("log", logOperate);
				map.put("operateLog", "1");
			}
		} catch (Exception e) {
			logger.error("察看日志详细信息时出错！", e);
			e.printStackTrace();
		}
		return "/frame/system/logMgr/viewLogInfo";
	}
	// @RequestMapping
	// public String logTest(HttpServletRequest request,
	// HttpServletResponse response) {
	// String uuid = UUIDTool.getUUID();
	// LogOperate logOperate = new LogOperate();
	// logOperate.setUserId("mengxw");
	// logOperate.setUserName("孟祥伟");
	// logOperate.setOperateType("测试");
	// logOperate.setOperateModule("测试方法");
	// logOperate.setLogDes("sjdflks阿卢萨2011-01-11腐林科所酱豆腐林科所减肥所担负三等分抗菌素代理费可加速度反馈中国画中华人民共和国哈哈哈哈哈哈黑恶黑孟祥为缩短开发绿色记录罚款三季度");
	// logOperate.setLogId(uuid);
	// //logOperate.setIndexTaskObjectId(uuid);
	// logService.insertLog(logOperate,111);
	// System.err.println("just for log test and lucene search test!");
	// return "index";
	// }
}
