package com.newsoft.common.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 分页信息 第一页从1开始
 * 
 * @author fengmq
 */
public class Page<T> implements Serializable, Iterable<T> {
	private static final long serialVersionUID = 1L;

	/**
	 * 查询结果集
	 */
	protected List<T> result;

	/**
	 * 分页每页数量
	 */
	protected int pageSize;

	/**
	 * 第几页
	 */
	protected int pageNumber;

	/**
	 * 记录总数
	 */
	protected int totalCount = 0;

	/**
	 * Page构造函数
	 * 
	 * @param pageNumber
	 *            当前页数
	 * @param pageSize
	 *            每页显示大小
	 * @param totalCount
	 *            总数
	 * @param sortColumns
	 *            排序字段
	 */
	@SuppressWarnings("unchecked")
	public Page(int pageNumber, int pageSize, int totalCount, String sortColumns) {
		this(pageNumber, pageSize, totalCount, (List<T>) new ArrayList<Object>(0));
	}

	/**
	 * Page构造函数
	 * 
	 * @param pageNumber
	 *            当前页数
	 * @param pageSize
	 *            每页显示大小
	 * @param totalCount
	 *            总数
	 * @param result
	 *            结果集
	 */
	public Page(int pageNumber, int pageSize, int totalCount, List<T> result) {
		if (pageSize <= 0)
			throw new IllegalArgumentException(
					"[pageSize] must great than zero");
		this.pageSize = pageSize;
		this.pageNumber = PageUtils.computePageNumber(pageNumber, pageSize,
				totalCount);
		this.totalCount = totalCount;
		setResult(result);
	}

	/**
	 * 设置结果集
	 * 
	 * @param elements
	 */
	public void setResult(List<T> elements) {
		if (elements == null)
			throw new IllegalArgumentException("'result' must be not null");
		this.result = elements;
	}

	/**
	 * 当前页包含的数据
	 * 
	 * @return 当前页数据源
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * 是否是首页（第一页），第一页页码为1
	 * 
	 * @return 首页标识
	 */
	public boolean isFirstPage() {
		return getThisPageNumber() == 1;
	}

	/**
	 * 是否是最后一页
	 * 
	 * @return 末页标识
	 */
	public boolean isLastPage() {
		return getThisPageNumber() >= getLastPageNumber();
	}

	/**
	 * 是否有下一页
	 * 
	 * @return 下一页标识
	 */
	public boolean isHasNextPage() {
		return getLastPageNumber() > getThisPageNumber();
	}

	/**
	 * 是否有上一页
	 * 
	 * @return 上一页标识
	 */
	public boolean isHasPreviousPage() {
		return getThisPageNumber() > 1;
	}

	/**
	 * 获取最后一页页码，也就是总页数
	 * 
	 * @return 最后一页页码
	 */
	public int getLastPageNumber() {
		return PageUtils.computeLastPageNumber(totalCount, pageSize);
	}

	/**
	 * 总的数据条目数量，0表示没有数据
	 * 
	 * @return 总数量
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 获取当前页的首条数据的行编码
	 * 
	 * @return 当前页的首条数据的行编码
	 */
	public int getThisPageFirstElementNumber() {
		return (getThisPageNumber() - 1) * getPageSize() + 1;
	}

	/**
	 * 获取当前页的末条数据的行编码
	 * 
	 * @return 当前页的末条数据的行编码
	 */
	public int getThisPageLastElementNumber() {
		int fullPage = getThisPageFirstElementNumber() + getPageSize() - 1;
		return getTotalCount() < fullPage ? getTotalCount() : fullPage;
	}

	/**
	 * 获取下一页编码
	 * 
	 * @return 下一页编码
	 */
	public int getNextPageNumber() {
		return getThisPageNumber() + 1;
	}

	/**
	 * 获取上一页编码
	 * 
	 * @return 上一页编码
	 */
	public int getPreviousPageNumber() {
		return getThisPageNumber() - 1;
	}

	/**
	 * 每一页显示的条目数
	 * 
	 * @return 每一页显示的条目数
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 当前页的页码
	 * 
	 * @return 当前页的页码
	 */
	public int getThisPageNumber() {
		return pageNumber;
	}

	/**
	 * 得到用于多页跳转的页码
	 * 
	 * @return
	 */
	public List<Integer> getLinkPageNumbers() {
		return PageUtils.generateLinkPageNumbers(getThisPageNumber(),
				getLastPageNumber(), 10);
	}

	/**
	 * 得到数据库的第一条记录号
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return PageUtils.getFirstResult(pageNumber, pageSize);
	}

	@SuppressWarnings("unchecked")
	public Iterator<T> iterator() {
		return (Iterator<T>) (result == null ? Collections.emptyList()
				.iterator() : result.iterator());
	}

	/**
	 * 设置总数
	 * 
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageNumber() {
		return pageNumber;
	}
}
