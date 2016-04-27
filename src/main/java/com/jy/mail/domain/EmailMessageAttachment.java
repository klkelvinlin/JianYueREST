package com.jy.mail.domain;

import java.io.Serializable;

import org.springframework.util.StringUtils;

public class EmailMessageAttachment extends AbstractIdentityObject<Long>
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EmailMessage emailMessage;
	private String fileName;
	private String mimeType;
	private byte[] content;

	public EmailMessageAttachment() {

	}

	public EmailMessageAttachment(final String fileName, final String mimeType,
			final byte[] content) {
		this.fileName = fileName;
		this.mimeType = mimeType;
		this.content = content;
	}

	/**
	 * Checks that this attachment is valid
	 */
	public void validate() {
		if (!StringUtils.hasText(this.fileName))
			throw new IllegalArgumentException("File name is missing.");
		if (!StringUtils.hasText(this.mimeType))
			throw new IllegalArgumentException("Mime type is missing.");
		if (this.content == null || this.content.length == 0)
			throw new IllegalArgumentException("Content is missing.");
	}

	public EmailMessage getEmailMessage() {
		return emailMessage;
	}

	public void setEmailMessage(final EmailMessage emailMessage) {
		this.emailMessage = emailMessage;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(final String mimeType) {
		this.mimeType = mimeType;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(final byte[] content) {
		this.content = content;
	}
}
