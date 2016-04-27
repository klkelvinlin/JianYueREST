package com.jy.mail.service;

import org.springframework.stereotype.Service;

import com.jy.mail.dao.EmailMessageDao;
import com.jy.mail.domain.EmailMessage;

@Service
public class DefaultEmailService implements EmailService {

	private EmailMessageDao emailMessageDao;
	
	public void setEmailMessageDao(final EmailMessageDao emailMessageDao) {
        this.emailMessageDao = emailMessageDao;
    }
	
	public void enqueue(EmailMessage message) {
		try {
            message.validate();
        } catch (IllegalArgumentException e) {
            return;
        }

        // the message is valid; insert it to the queue
        this.emailMessageDao.insert(message);
	}

	public void dequeue(final EmailMessage message) {
		if(message.getId() == null) {
			throw new EmailException("Can't find the message.");
		}
		this.emailMessageDao.delete(message);
	}
}
