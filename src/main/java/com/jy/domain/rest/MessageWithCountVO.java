package com.jy.domain.rest;

import java.util.List;

public class MessageWithCountVO {
	private Integer count;
	private List<MessageVO> messages;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<MessageVO> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageVO> messages) {
		this.messages = messages;
	}

}
