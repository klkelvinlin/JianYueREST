package com.jy.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.dao.FriendDao;
import com.jy.domain.Friend;
import com.jy.domain.rest.FriendVO;
import com.jy.exception.InvalidAttributesException;
import com.jy.utils.DateUtils;

@Service
public class FriendServiceImpl implements FriendService{

	@Autowired
	private FriendDao friendDao;

	@Override
	@Transactional
	public void unfriendUser(Long userId, Long friendId)
			throws InvalidAttributesException {
		try {
			Friend friend = friendDao.getFriendExist(userId, friendId);
			if (null == friend) {
				throw new InvalidAttributesException("The friend is not on your list");
			}
			friendDao.delete(friend);
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvalidAttributesException(e.getMessage());
		}
	}

	@Override
	public List<FriendVO> getUserFriends(Long userId, Integer pageNumber,
			Integer pageSize, Long timestamp) {
		try {
			Date duetime = DateUtils.timeConverter(new Date(timestamp));
			return friendDao.getFriend(userId, (pageNumber - 1) * pageSize, pageSize, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(duetime));
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvalidAttributesException(e.getMessage());
		}
		
	}

	@Override
	public Integer getUserFriendsCount(Long userId)
			throws InvalidAttributesException {
		return friendDao.getFriendCount(userId);
	}

}
