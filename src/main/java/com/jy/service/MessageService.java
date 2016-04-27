package com.jy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jy.domain.User;
import com.jy.domain.rest.MessageVO;
import com.jy.exception.InvalidAttributesException;


@Service
public interface MessageService {
	
	public void sendFriendRequestMsgToReceiver(User sender, Long receiver) throws InvalidAttributesException;
	public void acceptFriendRequest(User receiver, Long msgId) throws InvalidAttributesException;
	public void rejectFriendRequest(User receiver, Long msgId) throws InvalidAttributesException;
	
	public void sendCommonMsg(User sender, Long receiver, String content, MultipartFile file, Integer width, Integer height) throws InvalidAttributesException;
	public void replyMsg(User sender, String content, MultipartFile file, Integer width, Integer height, Long messageId) throws InvalidAttributesException;
	public void deleteMessage(User receiver, Long msgId) throws InvalidAttributesException;
	public Integer getUserUnreadMessageCount(Long userId) throws InvalidAttributesException;
	public List<MessageVO> getUserMessages(Long userId, Integer pageNumber, Integer pageSize, Long timestamp) throws InvalidAttributesException;
}
