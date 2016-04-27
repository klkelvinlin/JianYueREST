package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dataaccess.dao.GenericDao;
import com.jy.domain.Friend;
import com.jy.domain.rest.FriendVO;

public interface FriendDao extends GenericDao<Friend>{
	Friend getById(@Param("id") Long id);
	Integer getFriendCount(@Param("userId") Long userId);
	List<FriendVO> getFriend(@Param("userId") Long userId, @Param("firstResult") Integer firstResult,  @Param("pageSize") Integer pageSize, @Param("timestamp") String timestamp);	
	Friend getFriendExist(@Param("userId") Long userId, @Param("friendId") Long friendId);	
}