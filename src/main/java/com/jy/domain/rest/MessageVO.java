package com.jy.domain.rest;

import com.jy.domain.Message;

public class MessageVO extends Message{
	private String senderName;
	
	private Boolean isFriend;

	public Boolean getIsFriend() {
		return isFriend;
	}

	public void setIsFriend(Boolean isFriend) {
		this.isFriend = isFriend;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	
	
	
}
