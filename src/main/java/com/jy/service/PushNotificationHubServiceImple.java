package com.jy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.exception.InvalidAttributesException;

@Service
public class PushNotificationHubServiceImple implements PushNotificationHubService{
	
	@Autowired
	private ApplePushNotificationService appleService;
	
	@Transactional
	public void push(Long userId, String content, int badge) throws InvalidAttributesException{
		appleService.pushNotification(userId, content, badge);
	}
	
}
