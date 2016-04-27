package com.jy.mail.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import com.jy.mail.domain.EmailMessage;
@Component
public class EmailMessageDaoImpl implements EmailMessageDao {

	private AtomicLong lastId = new AtomicLong(0);
	private Map<Long, EmailMessage> mailQueue = new ConcurrentHashMap<Long, EmailMessage>();

	public void insert(EmailMessage emailMessage) {
		if (emailMessage.getId() == null) {
			this.lastId.incrementAndGet();
			emailMessage.setId(this.lastId.get());
		}
		this.mailQueue.put(emailMessage.getId(), emailMessage);
	}

	public List<EmailMessage> getUnsent() {
		List<EmailMessage> messages = new ArrayList<EmailMessage>();
		for (Long id : this.mailQueue.keySet()) {
			if (!mailQueue.get(id).isSucceeded()) {
				messages.add(mailQueue.get(id));
			}
		}
		if (!messages.isEmpty())
			return messages;
		return null;
	}

	public void delete(EmailMessage emailMessage) {
		this.mailQueue.remove(emailMessage.getId());
	}

	public void update(Long id, int sendAttemptCount, boolean success, Date date) {
		EmailMessage message = this.mailQueue.get(id);
		message.setSentDate(date);
		message.setSendAttemptCount(sendAttemptCount);
		if (success)
			message.setSucceeded(true);
		this.mailQueue.put(id, message);
	}

	public int getQueueSize() {
		return this.mailQueue.size();
	}

}
