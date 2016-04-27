package com.jy.web.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

@Configurable
/**
 * A central place to register application converters and formatters.
 */
public class ApplicationConversionServiceFactoryBean extends
		FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	public Converter<String, Long> getStringToLongConverter() {
		return new org.springframework.core.convert.converter.Converter<String, Long>() {
			public Long convert(String str) {
				return Long.parseLong(str);
			}
		};
	}

	public Converter<Date, String> getDateToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<Date, String>() {
			public String convert(Date date) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				return formatter.format(date);
			}
		};
	}


	public void installLabelConverters(FormatterRegistry registry) {
		registry.addConverter(getStringToLongConverter());
		registry.addConverter(getDateToStringConverter());
	}

	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		installLabelConverters(getObject());
	}
}
