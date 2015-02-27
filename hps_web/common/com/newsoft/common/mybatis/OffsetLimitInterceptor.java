package com.newsoft.common.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.newsoft.common.jdbc.dialect.Dialect;
import com.newsoft.common.jdbc.dialect.MySQLDialect;
import com.newsoft.common.mybatis.ObjectDaoImpl.CustomizedRowBounds;
import com.newsoft.common.page.FilterDataType;
import com.newsoft.common.page.PageParams;
import com.newsoft.common.spring.SpringBeanManager;
import com.newsoft.security.acegi.AclManager;

/**
 * 为mybatis3提供基于方言(Dialect)的分页查询的插件
 * 
 * 将拦截Executor.query()方法实现分页方言的插入.
 * 
 * 配置文件内容:
 * 
 * <pre>
 * 	&lt;plugins&gt;
 * 	&lt;plugin interceptor=&quot;cn.org.rapid_framework.ibatis3.plugin.OffsetLimitInterceptor&quot;&gt;
 * 		&lt;property name=&quot;dialectClass&quot; value=&quot;cn.org.rapid_framework.jdbc.dialect.MySQLDialect&quot;/&gt;
 * 	&lt;/plugin&gt;
 * &lt;/plugins&gt;
 * </pre>
 * 
 * @author 来源于开源项目rapid-framework并做修改
 * 
 */

@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }) })
public class OffsetLimitInterceptor implements Interceptor {
	static int MAPPED_STATEMENT_INDEX = 0;
	static int PARAMETER_INDEX = 1;
	static int ROWBOUNDS_INDEX = 2;
	static int RESULT_HANDLER_INDEX = 3;

	Dialect dialect;

	public Object intercept(Invocation invocation) throws Throwable {
		processIntercept(invocation.getArgs());
		return invocation.proceed();
	}

	void processIntercept(final Object[] queryArgs) {
		// queryArgs = query(MappedStatement ms, Object parameter, RowBounds
		// rowBounds, ResultHandler resultHandler)
		MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
		Object parameter = queryArgs[PARAMETER_INDEX];
		final RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX];
		int offset = rowBounds.getOffset();
		int limit = rowBounds.getLimit();

		// 解析返回的PO对象
		Table table = null;
		Class<?> poClass = null;

		List<ResultMap> ResultMaps = ms.getResultMaps();
		if (ResultMaps != null && ResultMaps.size() > 0) {
			ResultMap resultMap = ms.getResultMaps().get(0);
			poClass = resultMap.getType();
			table = (Table) poClass.getAnnotation(Table.class);
		}

		BoundSql boundSql = ms.getBoundSql(parameter);

		String sqlPre = boundSql.getSql().trim();
		String sql = sqlPre;
		if (rowBounds instanceof CustomizedRowBounds) {
			// 过滤参数
			Map<String, Object> filter = ((CustomizedRowBounds) rowBounds).getFilter();
			// BoundSql boundSql = ms.getBoundSql(parameter);
			// String sql = boundSql.getSql().trim();
			if (dialect.supportsLimit() && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
				// 新增排序部分sql
				sql = addOrderPart(sql, filter.get(PageParams.GRID_SORD).toString());
				// 新增过滤部分sql
				sql = addFilterPart(sql, filter);
				if (dialect.supportsLimitOffset()) {
					sql = dialect.getLimitString(sql, offset, limit, table);
					offset = RowBounds.NO_ROW_OFFSET;
				} else {
					sql = dialect.getLimitString(sql, 0, limit, table);
				}
				limit = RowBounds.NO_ROW_LIMIT;
				queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset, limit);
			} else {
				// 分页时，统计总记录数
				sql = addFilterPart(sql, filter);
			}

			if (sqlPre.toLowerCase().indexOf("%acl%") != -1) {
				AclManager aclManager = (AclManager) SpringBeanManager.getBean("aclManagerImpl");
				if (table == null) {
					ParameterMap paramMap = ms.getParameterMap();
					Class<?> poClass2 = paramMap.getType();
					table = (Table) poClass2.getAnnotation(Table.class);
				}
				String aclSql = aclManager.getAclSql(table.name());
				sql = sql.replace("%acl%", aclSql);
			}

			BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(),
					boundSql.getParameterObject());
			// add by fengmq 之前的版本有bug，丢失了参数，增加该for循环将参数加入newBoundSql中
			for (ParameterMapping mapping : boundSql.getParameterMappings()) {
				String prop = mapping.getProperty();
				if (boundSql.hasAdditionalParameter(prop)) {
					newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
				}
			}
			MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
			queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
		}
	}

	// see: MapperBuilderAssistant
	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource,
				ms.getSqlCommandType());

		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		// builder.keyProperty(ms.getKeyProperty());

		// setStatementTimeout()
		builder.timeout(ms.getTimeout());

		// setStatementResultMap()
		builder.parameterMap(ms.getParameterMap());

		// setStatementResultMap()
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());

		// setStatementCache()
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());

		return builder.build();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		String propertyFilePath = properties.getProperty("propertyFilePath", "/jdbc.properties");
		InputStream ins = null;
		try {
			Properties sysProp = new Properties();

			ins = OffsetLimitInterceptor.class.getResourceAsStream(propertyFilePath);
			sysProp.load(ins);

			String dialectKey = properties.getProperty("dialectKey", "jdbc.dialect");
			String dbType = sysProp.getProperty(dialectKey, "Oracle");

			this.dialect = getDialectInstance(dbType);
		} catch (Exception e) {
			throw new RuntimeException("Failed to get dialect instance by propertyFilePath:" + propertyFilePath, e);
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();

				}
			}
		}
	}

	private Dialect getDialectInstance(String dbType) {
		if (dbType.equalsIgnoreCase("mysql")) {
			return new MySQLDialect();
		}
		// Oracle dialect as default.
		return new MySQLDialect();
	}

	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

	/**
	 * 添加排序部分
	 * 
	 * @param sql
	 * @param orderBy
	 * @return
	 */
	static String addOrderPart(String sql, String orderBy) {
		if (!StringUtils.isEmpty(orderBy.toString())) {
			String loweredString = sql.toLowerCase();
			int orderByIndex = loweredString.indexOf("order by");
			if (orderByIndex != -1) {
				orderBy = orderBy + ", ";
				return sql.replace("order by", orderBy);
			} else {
				return sql += orderBy;
			}
		} else {
			return sql;
		}
	}

	/**
	 * 添加搜索过滤部分
	 * 
	 * @param sql
	 * @param filter
	 * @return
	 */
	static String addFilterPart(String sql, Map<String, Object> filter) {
		String tempSql = "";
		Iterator<Entry<String, Object>> iterator = filter.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			if (!entry.getKey().equals(PageParams.GRID_SORD)) {
				// String key = entry.getKey();
				Object value = entry.getValue();
				if (value instanceof FilterDataType) {
					FilterDataType filterData = (FilterDataType) value;
					String type = filterData.getType().toLowerCase();
					if (type.equals("<")) {
						tempSql += " and " + filterData.getKey() + " <'" + filterData.getValue()[0] + "'";
					} else if (type.equals(">")) {
						tempSql += " and " + filterData.getKey() + " >'" + filterData.getValue()[0] + "'";
					} else if (type.equals(">=")) {
						tempSql += " and " + filterData.getKey() + " >='" + filterData.getValue()[0] + "'";
					} else if (type.equals("<=")) {
						tempSql += " and " + filterData.getKey() + " <='" + filterData.getValue()[0] + "'";
					} else if (type.equals("like")) {
						tempSql += " and " + filterData.getKey() + " like '%" + filterData.getValue()[0] + "%'";
					} else {
						tempSql += " and " + filterData.getKey() + " ='" + filterData.getValue()[0] + "'";
					}
				}
			}
		}
		String loweredString = sql.toLowerCase();
		int orderByIndex = loweredString.indexOf("order by");
		int whereIndex = loweredString.indexOf("where");
		if (whereIndex != -1) {
			if (orderByIndex != -1) {
				sql = sql.replace("order by", tempSql + " order by");
			} else {
				sql += tempSql;
			}
		} else {
			if (orderByIndex != -1) {
				sql = sql.replace("order by", " where 1=1 " + tempSql + " order by");
			} else {
				sql += " where 1=1 " + tempSql;
			}
		}
		// System.out.println(tempSql);
		// System.out.println(sql);
		return sql;
	}
}
