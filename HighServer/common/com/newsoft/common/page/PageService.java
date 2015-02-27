/**
 * 通用的分页服务类
 */
package com.newsoft.common.page;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fengmq
 * 
 */
public interface PageService {

	/**
	 * 分页查询返回Page对象
	 * 
	 * @param daoClassName
	 *            查询获取数据的DAO类名（包含包名全路径）
	 * @param queryMethodName
	 *            查询获取数据的DAO接口的方法名称（也是DAO.xml配置文件中查询数据的sql的ID）
	 * @param countMethodName
	 *            统计总记录数的DAO接口的方法名称（也是DAO.xml配置文件中查询数据的sql的ID）
	 * @param pageNumber
	 *            当前页数
	 * @param pageSize
	 *            每页显示数量
	 * @param params
	 *            查询条件参数，可以是任意类型的对象
	 * @return Page对象
	 */
	public Page<?> pageQuery(HttpServletRequest request, String daoClassName,
			String queryMethodName, String countMethodName, Object params);

	/**
	 * grid分页查询
	 * 
	 * @param daoClassName
	 *            查询获取数据的DAO类名（包含包名全路径）
	 * @param queryMethodName
	 *            查询获取数据的DAO接口的方法名称（也是DAO.xml配置文件中查询数据的sql的ID）
	 * @param countMethodName
	 *            统计总记录数的DAO接口的方法名称（也是DAO.xml配置文件中查询数据的sql的ID）
	 * @param pageNumber
	 *            当前页数
	 * @param pageSize
	 *            每页显示数量
	 * @param params
	 *            查询条件参数，可以是任意类型的对象
	 * @return 返回jqgrid所需的json格式字符串
	 */
	public String gridPageQuery(HttpServletRequest request,
			String daoClassName, String queryMethodName,
			String countMethodName, Object params);

	/**
	 * 将page对象转换成grid所需的JSON格式数据
	 * 
	 * @param page
	 *            Page对象
	 * @return
	 */
	public String page2GridJson(Page<?> page);
}
