package com.jy.paypal.utils;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validates common paypal fields. The class is used directly by paypal classes
 * so you can use it for validation before setting certain values in paypal
 * requests or fields.
 * @author wdong
 *
 */
public final class Validator implements Serializable {
	
	private static final long serialVersionUID = -4747607796921252208L;

	/** pattern accepted by paypal */
    private static final Pattern amountPattern;

    /** email pattern for validation */
    private static final Pattern emailPattern;

    /** create instance of amountPattern only once */
    static {
        amountPattern   = Pattern.compile("^(\\d*\\.\\d{2})$");
        emailPattern    = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[_A-Za-z0-9-]+)$");
    }

    /**
     * Validates string if it is amount supported by paypal. Amount has to be
     * exactly two decimal places, decimal point is ".", or 0.
	 * You can set 0 for for example Payment when you set up recurring payment.
     *
     * @param   amount string representing the amount
     * @return  true if amount is valid, false otherwise
     */
    public static boolean isValidAmount(String amount) {

		if (amount == null) {
			return false;
		}

        /* amount is zero or number with exactly two decimal places */
        Matcher matcher = amountPattern.matcher(amount);
        
        return matcher.find();
    }

    /**
     * Validates email address. Does not validate length of the email, this is 
     * because it varies between paypal requests.
     * 
     * @param email
     * @return true if email is valid, otherwise false
     */
    public static boolean isValidEmail(String email) {

        if (email == null) {
            return false;
        }

        Matcher matcher = emailPattern.matcher(email);
        if (!matcher.matches()) {
            return false;
        }

        return true;
    }

    /**
     * Uses Luhn algorithm to validate numbers. Luhn algorithm is also known as
     * modulus 10 or mod 10 algorithm.
     *
     * In this case, this is used to validate credit card numbers.
     *
     * @param number    number to be validated (credit card number)
     * @return
     */
    public static boolean isValidLuhn(String number) {

        int sum = 0;

		boolean alternate = false;
		for (int i = number.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(number.substring(i, i + 1));
			if (alternate) {
				n *= 2;
				if (n > 9) {
					n = (n % 10) + 1;
				}
			}
			sum += n;
			alternate = !alternate;
		}

		return (sum % 10 == 0);
    }
}
