package com.newsoft.common.log;

import java.util.List;

import org.apache.ibatis.annotations.Insert;

import com.newsoft.common.mybatis.BaseDAO;

/**
 * 操作类日志的DAO接口
 * 
 * @author mengxw
 * 
 */
public interface FrameLogOperateDAO extends BaseDAO {

	/**
	 * 插入操作日志
	 * @param logOperate
	 * @throws Exception
	 */
	@Insert("insert into FRAME_LOG_OPERATE(LogID,UserID,UserName,OperateModule,OperateType,LogDate,LogDes) values(#{logId},#{userId},#{userName},#{operateModule},#{operateType},#{logDate},#{logDes})")
	public void addOperateLog(LogOperate logOperate) throws Exception;

	/**
	 * 删除日志
	 * @param logId
	 * @throws Exception
	 */
	public void deleteOperateLog(String logId) throws Exception;

	/**
	 * 获取日志列表
	 * @param logQueryParam
	 * @return
	 * @throws Exception
	 */
	public List<LogOperate> getOperateLogs(LogQueryParam logQueryParam) throws Exception;
	
	/**
	 * 获取日志
	 * @param logQueryParam
	 * @return
	 * @throws Exception
	 */
	public LogOperate getOperateLogByID(String logId) throws Exception;
}
