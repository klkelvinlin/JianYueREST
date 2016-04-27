package com.jy.service;

import org.springframework.stereotype.Service;

import com.jy.exception.InvalidAttributesException;

@Service
public interface PushNotificationHubService {
	
	public void push(Long userId, String content, int badge) throws InvalidAttributesException;
	
}
