package com.jy.utils;


public class JsonResponseWithObj extends JsonResponse {
	private Object object;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public JsonResponseWithObj(String aCK, String tIMESTAMP, Object object) {
		super(aCK, tIMESTAMP);
		this.object = object;
	}

	public JsonResponseWithObj(String aCK, String tIMESTAMP) {
		super(aCK, tIMESTAMP);
	}
	
	

}
