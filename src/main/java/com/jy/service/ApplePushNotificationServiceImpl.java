package com.jy.service;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushedNotification;
import javapns.notification.PushedNotifications;
import javapns.notification.ResponsePacket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.dao.UserDao;
import com.jy.domain.User;
import com.jy.exception.InvalidAttributesException;
import com.jy.utils.StringUtils;
import com.jy.utils.UploadUtils;

@Service
public class ApplePushNotificationServiceImpl implements
		ApplePushNotificationService {

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public void pushNotification(Long receiver, String content, int badge)
			throws InvalidAttributesException {
		if (null == receiver) {
			throw new InvalidAttributesException("Invalid parameter - userId");
		}
		if (!StringUtils.hasLength(content)) {
			throw new InvalidAttributesException("Invalid parameter - content");
		}

		try {
			User user = userDao.getById(receiver);
			if (StringUtils.hasLength(user.getDeviceToken())) {
				String p12FilePath = UploadUtils.INSTANCE.getAppleP12Path();
				String p12Password = UploadUtils.INSTANCE.getAppleP12Password();
				PushedNotifications pns = Push.combined(content, badge, "default", p12FilePath, p12Password, true, user.getDeviceToken());
				for (PushedNotification notification : pns) {
					if (notification.isSuccessful()) {
						System.out .println("Push notification sent successfully to: " + notification.getDevice().getToken());
					} else {
						Exception theProblem = notification.getException();
						theProblem.printStackTrace();
						ResponsePacket theErrorResponse = notification.getResponse();
						if (theErrorResponse != null) {
							System.out.println(theErrorResponse.getMessage());
						}
					}
				}
			}
		} catch (CommunicationException e) {
			throw new InvalidAttributesException("Invalid parameter - "
					+ e.getMessage());
		} catch (KeystoreException e) {
			throw new InvalidAttributesException("Invalid parameter - "
					+ e.getMessage());
		} catch (Exception e) {
			throw new InvalidAttributesException("Invalid parameter - "
					+ e.getMessage());
		}
	}

	/*
	public static void main(String[] args) {
		String[] devices = {
				"efa4bfafcc1663a5ea1b8ee9455faae6c920b7c3fd425152e36fd483498a25db" };

		try {

			List<PushedNotification> notifications = Push.alert("Hello World!",
					"E:/career/ties/ties/src/main/resources/Certificates.p12", "ties", true, devices);

			for (PushedNotification notification : notifications) {
				if (notification.isSuccessful()) {
					System.out
							.println("Push notification sent successfully to: "
									+ notification.getDevice().getToken());
				} else {
					String invalidToken = notification.getDevice().getToken();

					Exception theProblem = notification.getException();
					theProblem.printStackTrace();

					ResponsePacket theErrorResponse = notification
							.getResponse();
					if (theErrorResponse != null) {
						System.out.println(theErrorResponse.getMessage());
					}
				}
			}

		} catch (KeystoreException e) {
			e.printStackTrace();

		} catch (CommunicationException e) {
			e.printStackTrace();
		}
	}*/

}
