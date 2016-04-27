package com.jy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jy.domain.rest.FriendVO;
import com.jy.exception.InvalidAttributesException;


@Service
public interface FriendService {
	
	public List<FriendVO> getUserFriends(Long userId, Integer pageNumber, Integer pageSize, Long timestamp) throws InvalidAttributesException;
	
	public Integer getUserFriendsCount(Long userId) throws InvalidAttributesException;
	
	public void unfriendUser(Long userId, Long friendId) throws InvalidAttributesException;
	
}
