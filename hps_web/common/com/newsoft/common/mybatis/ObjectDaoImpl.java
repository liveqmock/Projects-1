package com.newsoft.common.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.newsoft.common.page.Page;
import com.newsoft.common.page.PageParams;
import com.newsoft.common.page.PageUtils;

/**
 * 
 * @author fengmq
 * 
 */
@Component
public class ObjectDaoImpl extends DaoSupport implements ObjectDao {
	private static final long serialVersionUID = 1L;

	protected final Log log = LogFactory.getLog(getClass());

	private SqlSessionFactory sqlSessionFactory;
	private SqlSessionTemplate sqlSessionTemplate;

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
	public Object getById(String daoClassName, String methodName,
			Object primaryKey) {
		Object object = getSqlSessionTemplate().selectOne(
				daoClassName + "." + methodName, primaryKey);
		return object;
	}

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
	public void deleteById(String daoClassName, String methodName, Object id) {
		getSqlSessionTemplate().delete(daoClassName + "." + methodName, id);
	}

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
	public void save(String daoClassName, String methodName, Object entity) {
		prepareObjectForSaveOrUpdate(entity);
		getSqlSessionTemplate().insert(daoClassName + "." + methodName, entity);
	}

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
			String methodName, Object entity) {
		prepareObjectForSaveOrUpdate(entity);
		Object primaryKey = getSqlSessionTemplate().insert(
				daoClassName + "." + methodName, entity);
		return primaryKey;
	}

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
	public void update(String daoClassName, String methodName, Object entity) {
		prepareObjectForSaveOrUpdate(entity);
		getSqlSessionTemplate().update(daoClassName + "." + methodName, entity);
	}

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
			Object entity) {
		// TODO
	}

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
			String countMethodName, PageParams pageParams, Object params) {
		// 分页参数
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		// 当前页数
		int pageNumber = pageParams.getPage();
		// 每页显示数量
		int pageSize = pageParams.getRows();

		// 设置分页过滤条件
		int offset = PageUtils.getFirstResult(pageNumber, pageSize);
		CustomizedRowBounds rowBounds = new CustomizedRowBounds(offset, pageSize);
		rowBounds.setFilter(pageParams.getFilter());

		// 查询获取记录总数
		// Number totalCount = (Number) this.getSqlSessionTemplate().selectOne(
		// daoClassName + "." + countMethodName, params);

		// 获取分页记录数
		List<?> list = getSqlSessionTemplate().selectList(
				daoClassName + "." + queryMethodName, params, rowBounds);
		// 统计条数也得加上过滤条件
		CustomizedRowBounds countRowBounds = new CustomizedRowBounds();
		countRowBounds.setFilter(rowBounds.getFilter());
		List<?> totalCount = this.getSqlSessionTemplate().selectList(
				daoClassName + "." + countMethodName, params, countRowBounds);
		// 返回对象
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Page page = new Page(pageNumber, pageSize, totalCount.get(0) == null ? 0
				: (Integer) totalCount.get(0), list);
		return page;
	}

	/**
	 * 扩展Mybatis类
	 * 
	 * @author zhouzq
	 *
	 */
	public static class CustomizedRowBounds extends RowBounds {
		public Map<String, Object> filter;

		public CustomizedRowBounds() {
			super();
		}

		public CustomizedRowBounds(int offset, int pageSize) {
			super(offset, pageSize);
		}

		public Map<String, Object> getFilter() {
			return filter;
		}

		public void setFilter(Map<String, Object> filter) {
			this.filter = filter;
		}
	}
	
	
	/**
	 * 检测sessionFaction是否已经初始化
	 */
	protected void checkDaoConfig() throws IllegalArgumentException {
		Assert.notNull("sqlSessionFactory must be not null");
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
		this.sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	/**
	 * 用于子类覆盖,在insert,update之前调用
	 * 
	 * @param o
	 */
	protected void prepareObjectForSaveOrUpdate(Object o) {
	}


	/**
	 * 判断是否唯一
	 * 
	 * @param entity
	 * @param uniquePropertyNames
	 * @return
	 */
	public boolean isUnique(Object entity, String uniquePropertyNames) {
		throw new UnsupportedOperationException();
	}

	public void flush() {
		// ignore
	}

	public static class SqlSessionTemplate {
		SqlSessionFactory sqlSessionFactory;

		public SqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
			this.sqlSessionFactory = sqlSessionFactory;
		}

		public Object execute(SqlSessionCallback action) {
			SqlSession session = null;
			try {
				session = sqlSessionFactory.openSession();
				Object result = action.doInSession(session);
				return result;
			} finally {
				if (session != null)
					session.close();
			}
		}

		public Object selectOne(final String statement, final Object parameter) {
			return execute(new SqlSessionCallback() {
				public Object doInSession(SqlSession session) {
					return session.selectOne(statement, parameter);
				}
			});
		}

		@SuppressWarnings({ "rawtypes" })
		public List selectList(final String statement, final Object parameter,
				final RowBounds rowBounds) {
			return (List) execute(new SqlSessionCallback() {
				public Object doInSession(SqlSession session) {
					return session.selectList(statement, parameter, rowBounds);
				}
			});
		}

		public int delete(final String statement, final Object parameter) {
			return (Integer) execute(new SqlSessionCallback() {
				public Object doInSession(SqlSession session) {
					return session.delete(statement, parameter);
				}
			});
		}

		public int update(final String statement, final Object parameter) {
			return (Integer) execute(new SqlSessionCallback() {
				public Object doInSession(SqlSession session) {
					return session.update(statement, parameter);
				}
			});
		}

		public int insert(final String statement, final Object parameter) {
			return (Integer) execute(new SqlSessionCallback() {
				public Object doInSession(SqlSession session) {
					return session.insert(statement, parameter);
				}
			});
		}
	}

	public static interface SqlSessionCallback {

		public Object doInSession(SqlSession session);

	}

}
