/**
 * 
 */
package com.newsoft.sysmanager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Result;

import com.newsoft.common.mybatis.BaseDAO;
import com.newsoft.sysmanager.po.Operator;

/**
 * @author fengmq
 * 
 */
public interface OperatorDAO extends BaseDAO {
	/**
	 * 获取所有操作。
	 * 
	 * @return
	 */
	@Result(javaType = Operator.class)
	public List<Operator> getAllOperator();

	/**
	 * 新增一个操作
	 * 
	 * @param operator
	 *            操作对象
	 * @return
	 */
	public int addOperator(Operator operator) throws Exception;

	/**
	 * 更新一个操作，取名为sysUpdate的原因：不然DataCenterAspect拦截该方法
	 * 
	 * @param operator
	 *            操作对象
	 * @return
	 */
	public int sysUpdateOperator(Operator operator) throws Exception;

	/**
	 * 删除一个操作
	 * 
	 * @param operatorId
	 *            操作Id
	 * @return
	 */
	public int deleteOperator(String operatorId) throws Exception;

	/**
	 * Query operators according to role id.
	 * 
	 * @param roleId
	 * @return
	 */
	public List<Operator> getOperatorByRole(String roleId);

}
