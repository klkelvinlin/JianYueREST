package com.jy.utils;


public class JsonResponseWithId extends JsonResponse {
	private String CLIENTID;

	public String getCLIENTID() {
		return CLIENTID;
	}

	public void setCLIENTID(String cLIENTID) {
		CLIENTID = cLIENTID;
	}

	public JsonResponseWithId(String aCK, String tIMESTAMP,
			String cLIENTID) {
		super(aCK, tIMESTAMP);
		CLIENTID = cLIENTID;
	}

	public JsonResponseWithId(String aCK, String tIMESTAMP) {
		super(aCK, tIMESTAMP);
	}

}
