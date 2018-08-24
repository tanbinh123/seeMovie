package com.seeMovie.common.utils;

import java.io.Serializable;
/**
 * 
 * @author mym
 * @date   2018年7月8日下午1:53:06
 * 分页函数工具类
 */
public class PagingUtil implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 当前页
	 */
	private int currentPage = 1;
	/**
	 * 每页显示记录数  默认条数
	 */
	private int pageSize = 24;
	/**
	 * 总页数
	 */
	private int totalPageSize;
	/**
	 * 总数
	 */
	private Integer total;
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPageSize() {
		return totalPageSize;
	}
	public void setTotalPageSize(int totalPageSize) {
		this.totalPageSize = totalPageSize;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
}
