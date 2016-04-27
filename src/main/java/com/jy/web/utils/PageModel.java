package com.jy.web.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class PageModel {
	
	private Integer recordCount;
	private Integer pageCount;
	private Integer pageSize;
	private Integer currentPage;
	private Integer firstResult=0;
	private String url;
	private String paraStr;
	private Map<String, String > paraMap;

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

	public String getParaStr() {
		return paraStr;
	}

	public void setParaStr(String paraStr) {
		this.paraStr = paraStr;
	}

	public Map<String, String> getParaMap() {
		return paraMap;
	}

	public void setParaMap(Map<String, String> paraMap) {
		if(null!=paraMap && !paraMap.isEmpty()){
	       	 Set<String> keySet = paraMap.keySet();
	            Iterator<String> it = keySet.iterator();  
	            StringBuilder sb=new StringBuilder();
	            while(it.hasNext()){
	                String key = it.next();  
	                String value = paraMap.get(key);
	                sb.append(key+"="+value+"&");
	            } 
	            setParaStr(sb.toString());
	        }
		this.paraMap = paraMap;
	}
	
	

}
