/**
 * 
 */
package com.newsoft.sysmanager.service;

import java.util.List;
import java.util.Set;

import com.newsoft.sysmanager.bean.SystemNameBean;
import com.newsoft.sysmanager.po.Operator;

/**
 * @author fengmq
 * 
 */
public interface OperatorService {
	/**
	 * 
	 * 从XML中读取所有操作类别
	 * 
	 * 
	 * @return
	 */
	public Set<Operator> getOperatorListFromXml();

	/**
	 * 从数据库清除多余的操作类
	 * 
	 * @param dbOperatorList
	 * @param xmlOperatorList
	 */
	public void removeDBOperatorNotInXml(List<Operator> dbOperatorList, Set<Operator> xmlOperatorList) throws Exception;

	/**
	 * 更新数据库内的操作类
	 * 
	 * @param dbOperatorList
	 * @param xmlOperatorList
	 */
	public void updateOperatorToDB(List<Operator> dbOperatorList, Set<Operator> xmlOperatorList) throws Exception;

	/**
	 * 获取所有操作类
	 * 
	 * @return
	 */
	public List<Operator> getAllOperator();

	/**
	 * 获取当前登录用户的操作权限Id
	 * 
	 * @return
	 */
	public String getOperationIdsBySessionUser();

	
	public List<SystemNameBean> getSystemNameBeanList();
}
