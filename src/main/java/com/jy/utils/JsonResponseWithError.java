package com.jy.utils;


public class JsonResponseWithError extends JsonResponse {
	private String ERROR;

	public String getERROR() {
		return ERROR;
	}

	public void setERROR(String eRROR) {
		ERROR = eRROR;
	}

	public JsonResponseWithError(String aCK, String tIMESTAMP,
			String eRROR) {
		super(aCK, tIMESTAMP);
		ERROR = eRROR;
	}

	public JsonResponseWithError(String aCK, String tIMESTAMP) {
		super(aCK, tIMESTAMP);
	}

}
