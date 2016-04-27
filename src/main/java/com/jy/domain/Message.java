package com.jy.domain;

import java.util.Date;

import com.jy.dataaccess.dao.AbstractDomainObject;
import com.jy.utils.DateUtils;

public class Message extends AbstractDomainObject{
    private Long id;

    private Long senderId;

    private String senderPic;
    
    private Integer senderPicWidth;
    
    private Integer senderPicHeight;

    private Long receiverId;

    private String msg;

    private String msgType;

    private String msgStatus;

    private Boolean isRead;

    private Boolean isDeleted;

    private Boolean isReplied;

    private Date sendtime;

    private Date readtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderPic() {
        return senderPic;
    }

    public void setSenderPic(String senderPic) {
        this.senderPic = senderPic == null ? null : senderPic.trim();
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType == null ? null : msgType.trim();
    }

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus == null ? null : msgStatus.trim();
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsReplied() {
        return isReplied;
    }

    public void setIsReplied(Boolean isReplied) {
        this.isReplied = isReplied;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public Date getReadtime() {
        return readtime;
    }

    public void setReadtime(Date readtime) {
        this.readtime = readtime;
    }
    
    public Integer getSenderPicWidth() {
		return senderPicWidth;
	}

	public void setSenderPicWidth(Integer senderPicWidth) {
		this.senderPicWidth = senderPicWidth;
	}

	public Integer getSenderPicHeight() {
		return senderPicHeight;
	}

	public void setSenderPicHeight(Integer senderPicHeight) {
		this.senderPicHeight = senderPicHeight;
	}

	public String getSendtimeForUI(){
    	return DateUtils.switchToUsTime(this.sendtime); 
    }
}