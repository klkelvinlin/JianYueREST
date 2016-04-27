package com.jy.web.utils;

public class PageTabModel {

	private String currentTabId;
	private String tabId;
	private String tabPageId;
	private Integer recordCount;
	private Integer pageCount;
	private Integer pageSize;
	private Integer currentPage;
	private Integer firstResult = 0;
	private String url;

	public String getCurrentTabId() {
		return currentTabId;
	}

	public void setCurrentTabId(String currentTabId) {
		this.currentTabId = currentTabId;
	}

	public String getTabId() {
		return tabId;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}

	public String getTabPageId() {
		return tabPageId;
	}

	public void setTabPageId(String tabPageId) {
		this.tabPageId = tabPageId;
	}

	public Integer getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
