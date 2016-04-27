package com.jy.dataaccess.cache.annotations.key;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * hash code calculator
 * 
 * @author wdong
 * 
 */
public final class HashCodeCalculator {
	private static final int INITIAL_HASH = 17;

	private static final int MULTIPLIER = 37;

	private long checkSum;

	/**
	 * Counts the number of times <code>{@link #append(int)}</code> is executed.
	 * It is also used to build <code>{@link #checkSum}</code> and
	 * <code>{@link #hashCode}</code>.
	 */
	private AtomicInteger count;

	/**
	 * Hash code to build;
	 */
	private int hashCode;

	/**
	 * Constructor.
	 */
	public HashCodeCalculator() {
		super();
		hashCode = INITIAL_HASH;
	}

	/**
	 * Recalculates <code>{@link #checkSum}</code> and
	 * <code>{@link #hashCode}</code> using the specified value.
	 * 
	 * @param value
	 *            the specified value.
	 */
	public void append(int value) {
		//count++;
		count.incrementAndGet();
		int valueToAppend = count.intValue() * value;

		hashCode = MULTIPLIER * hashCode
				+ (valueToAppend ^ (valueToAppend >>> 16));
		checkSum += valueToAppend;
	}

	/**
	 * @return the number that ensures that the combination hashCode/checSum is
	 *         unique
	 */
	public long getCheckSum() {
		return checkSum;
	}

	/**
	 * @return the calculated hash code
	 */
	public int getHashCode() {
		return hashCode;
	}
}
