package com.jy.utils;


public class JsonResponse {
	private String ACK;
	private String TIMESTAMP;

	public String getACK() {
		return ACK;
	}

	public void setACK(String aCK) {
		ACK = aCK;
	}

	public String getTIMESTAMP() {
		return TIMESTAMP;
	}

	public void setTIMESTAMP(String tIMESTAMP) {
		TIMESTAMP = tIMESTAMP;
	}

	public JsonResponse(String aCK, String tIMESTAMP) {
		super();
		ACK = aCK;
		TIMESTAMP = tIMESTAMP;
	}

	public JsonResponse() {
		super();
	}

}
