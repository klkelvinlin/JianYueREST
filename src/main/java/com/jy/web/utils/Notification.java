package com.jy.web.utils;

public class Notification {
	private String title;
	private String msg;
	private NotificationType notificationType;
	
	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Notification() {
		super();
	}

	public Notification(String title, String msg,
			NotificationType notificationType) {
		super();
		this.title = title;
		this.msg = msg;
		this.notificationType = notificationType;
	}
	

}
