package com.jy.domain.rest;

import java.util.Date;

import com.jy.utils.DateUtils;

public class FriendVO {
	private Long id;
	private Long friendId;
	private String friendName;
	private String friendPic;
	private Integer friendPicWidth;
	private Integer friendPicHeight;
	
	private Date createtime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}

	public String getFriendPic() {
		return friendPic;
	}

	public void setFriendPic(String friendPic) {
		this.friendPic = friendPic;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	public Integer getFriendPicWidth() {
		return friendPicWidth;
	}

	public void setFriendPicWidth(Integer friendPicWidth) {
		this.friendPicWidth = friendPicWidth;
	}

	public Integer getFriendPicHeight() {
		return friendPicHeight;
	}

	public void setFriendPicHeight(Integer friendPicHeight) {
		this.friendPicHeight = friendPicHeight;
	}

	public String getCreatetimeForUI(){
		return DateUtils.switchToUsTime(this.createtime);
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	

}
