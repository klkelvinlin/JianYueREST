package com.jy.utils;

import java.util.List;

public class JsonResponseWithIdAndList extends JsonResponseWithId {
	private List list;

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public JsonResponseWithIdAndList(String aCK, String tIMESTAMP,
			String cLIENTID, List list) {
		super(aCK, tIMESTAMP, cLIENTID);
		this.list = list;
	}

	public JsonResponseWithIdAndList(String aCK, String tIMESTAMP,
			String cLIENTID) {
		super(aCK, tIMESTAMP, cLIENTID);
	}

}
