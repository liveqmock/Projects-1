package com.newsoft.common.mybatis;

import java.io.Serializable;

import com.newsoft.common.page.Page;
import com.newsoft.common.page.PageParams;

/**
 * 通用的查询类
 * 
 * @author fengmq
 * 
 */
public interface ObjectDao extends Serializable {

	/**
	 * 根据Id获取实体对象
	 * 
	 * @param daoClassName
	 *            mapper namespace，也就是xxxDAO.xml的命名空间
	 * @param methodName
	 *            查询方法名称（或者称select标签的id）
	 * @param id
	 *            id对象
	 * @return
	 */
	public Object getById(String daoClassName, String methodName, Object id);

	/**
	 * 根据Id删除实体对象
	 * 
	 * @param daoClassName
	 *            mapper namespace，也就是xxxDAO.xml的命名空间
	 * @param methodName
	 *            查询方法名称（或者称delete标签的id）
	 * @param id
	 *            id对象
	 */
	public void deleteById(String daoClassName, String methodName, Object id);

	/**
	 * 保存实体对象
	 * 
	 * @param daoClassName
	 *            mapper namespace，也就是xxxDAO.xml的命名空间
	 * @param methodName
	 *            查询方法名称（或者称insert标签的id）
	 * @param entity
	 *            实体对象
	 */
	public void save(String daoClassName, String methodName, Object entity);

	/**
	 * 保存实体对象并返回对象主键
	 * 
	 * @param daoClassName
	 *            mapper namespace，也就是xxxDAO.xml的命名空间
	 * @param methodName
	 *            查询方法名称（或者称insert标签的id）
	 * @param entity
	 *            实体对象
	 * @return 主键
	 */
	public Object saveAndReturnPrimaryKey(String daoClassName,
			String methodName, Object entity);

	/**
	 * 更新实体对象
	 * 
	 * @param daoClassName
	 *            mapper namespace，也就是xxxDAO.xml的命名空间
	 * @param methodName
	 *            查询方法名称（或者称update标签的id）
	 * @param entity
	 *            实体对象
	 */
	public void update(String daoClassName, String methodName, Object entity);

	/**
	 * 保存或更新
	 * 
	 * @param daoClassName
	 *            mapper namespace，也就是xxxDAO.xml的命名空间
	 * @param methodName
	 *            查询方法名称（或者称insert/update标签的id）
	 * @param entity
	 */
	public void saveOrUpdate(String daoClassName, String methodName,
			Object entity);

	/**
	 * 判断是否唯一
	 * 
	 * @param entity
	 * @param uniquePropertyNames
	 * @return
	 */
	public boolean isUnique(Object entity, String uniquePropertyNames);

	/**
	 * 分页查询记录
	 * 
	 * @param daoClassName
	 *            mapper namespace，也就是xxxDAO.xml的命名空间
	 * @param queryMethodName
	 *            查询方法名称（或者称select标签的id）
	 * @param countMethodName
	 *            统计条目（或者称select标签的id）
	 * @param pageNumber
	 *            页码
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	public Page<?> pageQuery(String daoClassName, String queryMethodName,
			String countMethodName, PageParams pageParams , Object params);

	public void flush();

}
