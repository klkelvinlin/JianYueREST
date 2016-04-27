package com.jy.utils;

import java.util.List;

public class JsonResponseWithList extends JsonResponse {
	private List list;

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public JsonResponseWithList(String aCK, String tIMESTAMP, List list) {
		super(aCK, tIMESTAMP);
		this.list = list;
	}

	public JsonResponseWithList(String aCK, String tIMESTAMP) {
		super(aCK, tIMESTAMP);
	}


}
