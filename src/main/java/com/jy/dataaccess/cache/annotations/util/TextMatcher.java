package com.jy.dataaccess.cache.annotations.util;

/**
 * 
 * @author wdong
 *
 */
public abstract class TextMatcher {
	
	/**
	 * <p>
	 * Returns <code>true</code> if the given text matches the base text. The
	 * default implementation checks for "xxx*" and "*xxx" matches.
	 * </p>
	 * <p>
	 * For example "getName" should match "getName", "getN*" and "get*".
	 * </p>
	 * 
	 * @param text
	 *            the text to match.
	 * @param baseText
	 *            the base text.
	 * @return <code>true</code> if the text matches.
	 */
	public static boolean isMatch(String text, String baseText) {
		boolean match = (baseText.endsWith("*") && text.startsWith(baseText.substring(0, baseText.length() - 1)))
				|| (baseText.startsWith("*") && text.endsWith(baseText.substring(1, baseText.length())));
		return match;
	}
}
