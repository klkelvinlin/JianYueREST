package com.jy.mail.service;

import com.jy.mail.domain.EmailMessage;

public interface EmailService {
	void enqueue(final EmailMessage message);
	void dequeue(final EmailMessage message);
}
