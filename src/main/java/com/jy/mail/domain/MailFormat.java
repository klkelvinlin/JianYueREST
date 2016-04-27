package com.jy.mail.domain;

public enum MailFormat {
	html(1), plain(2);

	private int value;

	MailFormat(final int value) {
		this.value = value;
	}

	public final int toInt() {
		return value;
	}

	public static MailFormat fromInt(final int value) {
		switch (value) {
		case (1):
			return html;
		case (2):
			return plain;
		}
		throw new IllegalArgumentException("Illegal value " + value);
	}
}
