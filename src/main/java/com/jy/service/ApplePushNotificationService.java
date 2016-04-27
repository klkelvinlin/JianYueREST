package com.jy.service;

import org.springframework.stereotype.Service;

import com.jy.exception.InvalidAttributesException;

@Service
public interface ApplePushNotificationService {
	public void pushNotification(Long receiver, String content, int badge)
			throws InvalidAttributesException;
}
