package com.jy.web.utils;

import javax.servlet.http.HttpServletRequest;

public class PageUtils {
	
	public final static int PAGE_SIZE = 20;
	
	public static PageModel getPageModel(Integer currentPage, Integer recordCount, Integer pageSize, HttpServletRequest request) {
		if (currentPage == null) {
			currentPage = 1;
		}
		if (pageSize == null) {
			pageSize = PAGE_SIZE;
		}
		PageModel pageModel = new PageModel();
		pageModel.setPageSize(pageSize);
		pageModel.setRecordCount(recordCount);
		String url=request.getRequestURL().toString();
		pageModel.setUrl(url.replaceAll(":8080", ""));//replace the port
		Integer pageCount = 0;
		if (recordCount % pageSize != 0) {
			pageCount = recordCount / pageSize + 1;
		} else {
			pageCount = recordCount / pageSize;
		}
		pageModel.setPageCount(pageCount);

		if (currentPage > pageCount) {
			currentPage = pageCount;
		}
		if (currentPage < 1) {
			currentPage = 1;
		}
		pageModel.setCurrentPage(currentPage);

		int start = (currentPage - 1) * pageSize;
		pageModel.setFirstResult(start);
		return pageModel;
	}


	public static PageTabModel getPageTabModel(String tabPageId, Integer currentPage, Integer recordCount, Integer pageSize, HttpServletRequest request) {
		if (currentPage == null) {
			currentPage = 1;
		}
		if (pageSize == null) {
			pageSize = PAGE_SIZE;
		}
		PageTabModel tabModel = new PageTabModel();
		tabModel.setTabPageId(tabPageId);
		tabModel.setCurrentPage(currentPage);
		tabModel.setPageSize(pageSize);
		tabModel.setRecordCount(recordCount);
		String url=request.getRequestURL().toString();
		tabModel.setUrl(url.replaceAll(":8080", ""));//replace the port
		Integer pageCount = 0;
		if (recordCount % pageSize != 0) {
			pageCount = recordCount / pageSize + 1;
		} else {
			pageCount = recordCount / pageSize;
		}
		tabModel.setPageCount(pageCount);

		if (currentPage > pageCount) {
			currentPage = pageCount;
		}
		if (currentPage < 1) {
			currentPage = 1;
		}
		tabModel.setCurrentPage(currentPage);

		int start = (currentPage - 1) * pageSize;
		tabModel.setFirstResult(start);
		return tabModel;
	}
}
