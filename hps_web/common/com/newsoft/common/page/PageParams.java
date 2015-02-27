package com.newsoft.common.page;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

/**
 * 分页参数对象
 * 
 * @author fengmq
 */
public class PageParams implements Serializable {
	private static final long serialVersionUID = 1L;
	// grid查询前缀
	public static String GRID_FILTER_PREFIX = "grid_filter_";
	public static String FILTERTYPESEPARATOR = "filterTypeSeparator";
	public static String GRID_SORD = "grid_sord";

	// 当前页数
	private int page;
	// 每页显示数量
	private int rows;
	// grid排序字段
	private String sidx;
	// grid排序方式,asc or desc
	private String sord;
	/**
	 * 过滤参数
	 */
	private HashMap<String, Object> filter;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public HashMap<String, Object> getFilter() {
		if (this.filter == null) {
			this.filter = new HashMap<String, Object>();
		}
		if (!StringUtils.isEmpty(sidx)) {
			filter.put(GRID_SORD, " order by " + sidx + " " + sord);
		} else {
			filter.put(GRID_SORD, "");
		}
		return filter;
	}

	public void setFilter(HashMap<String, Object> filter) {
		this.filter = filter;
	}

}
