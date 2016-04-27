package com.jy.mail.dao;

import java.util.Date;
import java.util.List;

import com.jy.mail.domain.EmailMessage;

public interface EmailMessageDao {
	
	void insert(EmailMessage emailMessage);

    List<EmailMessage> getUnsent();

    void delete(EmailMessage emailMessage);

    void update(Long id, int sendAttemptCount, boolean success, Date date);
    
    public int getQueueSize();
}
