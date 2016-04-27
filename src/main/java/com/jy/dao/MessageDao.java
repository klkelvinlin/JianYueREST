package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dataaccess.dao.GenericDao;
import com.jy.domain.Message;
import com.jy.domain.rest.MessageVO;

public interface MessageDao extends GenericDao<Message>{
	
	List<MessageVO> getUserUnreadMessage(@Param("receiverId") Long receiverId, @Param("firstResult") Integer firstResult,  @Param("pageSize") Integer pageSize, @Param("timestamp") String timestamp);
	
	Integer getUserUnreadMessageCount(@Param("receiverId") Long receiverId);
	
	Message getById(@Param("id") Long id);
	
	Message getFriendRequestBySenderAndReceiver(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId, @Param("msgType") String msgType, @Param("msgStatus") String msgStatus);
}