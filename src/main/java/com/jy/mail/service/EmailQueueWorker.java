package com.jy.mail.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.jy.mail.dao.EmailMessageDao;
import com.jy.mail.domain.EmailAddress;
import com.jy.mail.domain.EmailAddressType;
import com.jy.mail.domain.EmailMessage;
import com.jy.mail.domain.EmailMessageAttachment;

/**
 * JMX MBean to manage email send queue
 * 
 * @author wdong
 * 
 */
public class EmailQueueWorker {

	private EmailMessageDao emailMessageDao;

	private JavaMailSender javaMailSender;

	private ThreadPoolTaskExecutor taskExecutor;

	/**
	 * Send all the email once that are not be sent
	 * 
	 * @param to
	 *            decide if the
	 */
	public List<EmailMessage> send() {
		List<EmailMessage> messages = this.emailMessageDao.getUnsent();
		List<EmailMessage> sent = new ArrayList<EmailMessage>();
		for (EmailMessage message : messages) {
			sendOne(message, true);
			sent.add(message);
		}
		return sent;
	}

	/**
	 * Sent the email once
	 * 
	 * @param message
	 *            the email that will be sent
	 * @param removeSentMail
	 *            true: sender will remove the mail, false:update the sent info
	 */
	public void sendOne(final EmailMessage message, final boolean removeSentMail) {
		final MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		prepareMimeMessage(mimeMessage, message);
		taskExecutor.execute(new Runnable() {

			@Override
			public void run() {
				javaMailSender.send(mimeMessage);
				if (removeSentMail) {
					emailMessageDao.delete(message);
				} else {
					// update the queue
					emailMessageDao.update(message.getId(),
							message.getSendAttemptCount() + 1, true, new Date());
				}
			}

		});

	}

	private void prepareMimeMessage(MimeMessage mimeMessage,
			EmailMessage emailMessage) {
		try {

			mimeMessage.setFrom(getAddress(emailMessage.getFrom()));
			setRecipients(mimeMessage, emailMessage);
			mimeMessage.setSubject(emailMessage.getSubject());

			// create wrapper multipart/alternative part
			MimeMultipart ma = new MimeMultipart("alternative");
			mimeMessage.setContent(ma);

			// create the plain text
			/*
			 * BodyPart plainText = new MimeBodyPart();
			 * plainText.setText(emailMessage.getBody());
			 * ma.addBodyPart(plainText);
			 */

			// creat the html and images multipart wrapper
			BodyPart related = new MimeBodyPart();
			MimeMultipart mr = new MimeMultipart("related");
			related.setContent(mr);
			ma.addBodyPart(related);

			BodyPart html = new MimeBodyPart();
			html.setContent(emailMessage.getBody(), "text/html");
			mr.addBodyPart(html);

			for (final Map.Entry<String, String> entry : emailMessage
					.getInlines().entrySet()) {
				BodyPart element = new MimeBodyPart();
				element.setHeader("Content-ID", entry.getKey().substring(4));
				element.setDisposition("inline");
				element.setDataHandler(new DataHandler(new FileDataSource(entry
						.getValue())));
				mr.addBodyPart(element);
			}

			// attachments
			for (final EmailMessageAttachment attachment : emailMessage
					.getAttachments()) {
				BodyPart attachmentPart = new MimeBodyPart();
				DataSource dataSource = new DataSource() {
					public String getContentType() {
						return attachment.getMimeType();
					}

					public InputStream getInputStream() {
						return new ByteArrayInputStream(attachment.getContent());
					}

					public String getName() {
						return attachment.getFileName();
					}

					public OutputStream getOutputStream() {
						return new ByteArrayOutputStream();
					}
				};
				attachmentPart.setDataHandler(new DataHandler(dataSource));
				attachmentPart.setFileName(attachment.getFileName());
				ma.addBodyPart(attachmentPart);
			}

		} catch (MessagingException me) {
			// handle the exception
			throw new RuntimeException(me);
		}
	}

	private void setRecipients(MimeMessage mimeMessage,
			EmailMessage emailMessage) {
		try {
			for (EmailAddress emailAddress : emailMessage.getRecipients()) {
				mimeMessage.addRecipient(
						resolveType(emailAddress.getAddressType()),
						getAddress(emailAddress));
			}

		} catch (AddressException ae) {
			// handle exception
			throw new RuntimeException(ae);
		} catch (MessagingException me) {
			// handle exception
			throw new RuntimeException(me);
		}
	}

	private Message.RecipientType resolveType(EmailAddressType addressType) {
		switch (addressType) {
		case To:
			return Message.RecipientType.TO;
		case Cc:
			return Message.RecipientType.CC;
		case Bcc:
			return Message.RecipientType.BCC;
		}
		throw new RuntimeException("Unknown recipient type");
	}

	private InternetAddress getAddress(EmailAddress emailAddress) {
		try {
			return new InternetAddress(emailAddress.getEmailAddress());
		} catch (AddressException ae) {
			// handle exception
			throw new RuntimeException(ae);
		}
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void setEmailMessageDao(EmailMessageDao emailMessageDao) {
		this.emailMessageDao = emailMessageDao;
	}

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
}
