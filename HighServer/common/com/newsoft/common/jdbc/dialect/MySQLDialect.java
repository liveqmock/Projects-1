package com.newsoft.common.jdbc.dialect;

import com.newsoft.common.mybatis.Table;

/**
 * MySQL 数据库分页（参照Hibernate分页方言）
 * 
 * @author 来源于开源项目rapid-framework（http://code.google.com/p/rapid-framework）
 */
public class MySQLDialect extends Dialect {
	public boolean supportsLimitOffset() {
		return true;
	}

	public boolean supportsLimit() {
		return true;
	}

	@Override
	public String getLimitString(String sql, int offset,
			String offsetPlaceholder, int limit, String limitPlaceholder,Table table) {
		if (offset > 0) {
			return sql + " limit " + offsetPlaceholder + "," + limitPlaceholder;
		} else {
			return sql + " limit " + limitPlaceholder;
		}
	}

}
