package com.jy.domain.rest;

import java.util.List;

public class FriendWithCountVO {
	private Integer count;
	private List<FriendVO> friends;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<FriendVO> getFriends() {
		return friends;
	}

	public void setFriends(List<FriendVO> friends) {
		this.friends = friends;
	}

}
