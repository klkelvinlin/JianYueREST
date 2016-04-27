package com.jy.mail.service;

import java.util.Locale;
import java.util.Map;

import com.jy.mail.domain.EmailMessage;
import com.jy.mail.domain.MailFormat;

public interface EmailMessageProducer {
	EmailMessage produce(final String emailTemplateCode, final Locale locale,
			MailFormat format, final Map<String, Object> arguments);
}
