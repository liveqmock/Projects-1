package com.newsoft.common.jdbc.dialect;

import com.newsoft.common.mybatis.Table;

/**
 * 类似hibernate的Dialect,但只精简出分页部分（参照Hibernate分页方言）
 * 
 * @author 来源于开源项目rapid-framework（http://code.google.com/p/rapid-framework）
 */
public class Dialect {

	public boolean supportsLimit() {
		return false;
	}

	public boolean supportsLimitOffset() {
		return supportsLimit();
	}

	/**
	 * 将sql变成分页sql语句,直接使用offset,limit的值作为占位符.</br> 源代码为:
	 * getLimitString(sql,offset,String.valueOf(offset),limit,String.valueOf(limit))
	 */
	public String getLimitString(String sql, int offset, int limit,Table table) {
		return getLimitString(sql, offset, Integer.toString(offset), limit,
				Integer.toString(limit),table);
	}

	/**
	 * 将sql变成分页sql语句,提供将offset及limit使用占位符(placeholder)替换.
	 * 
	 * <pre>
	 * 如mysql
	 * dialect.getLimitString(&quot;select * from user&quot;, 12, &quot;:offset&quot;,0,&quot;:limit&quot;) 将返回
	 * select * from user limit :offset,:limit
	 * </pre>
	 * 
	 * @return 包含占位符的分页sql
	 */
	public String getLimitString(String sql, int offset,
			String offsetPlaceholder, int limit, String limitPlaceholder,Table table) {
		throw new UnsupportedOperationException("paged queries not supported");
	}

}
