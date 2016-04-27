package com.jy.paypal.profile;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Represents paypal user - password, user name etc.
 * BaseProfile builder for instatiating this class. Example:
 * BaseProfile usr = new BaseProfile.Builder(userName, pass).signature(sign).build();
 * @author wdong
 *
 */
public final class BaseProfile implements Profile {

	private static final long serialVersionUID = -7845605956235174192L;
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * user name obtained from paypal
	 */
	private final String userName;
	/**
	 * password obtained from paypal
	 */
	
	private final String password;
	/**
	 * If you use an API certificate, do not include this parameter
	 */
	
	private final String signature;
	 /** 
     * Email address of a PayPal account that has granted you permission to 
     * make this call.
     * Set this parameter only if you are calling an API on a different user��s 
     * behalf
     */
    private final String subject;
    
    /**
     * Builder class that constructs instance of BaseProfile class.
     * Example: 
     * BaseProfile user = new BaseProfile.Builder(userName, pass).signature(sign).build();
     * @author wdong
     */
    public static class Builder {
    	/* required parameters */
    	private final String userName;
    	private final String password;
    	
    	/* optional paramters */
        private String signature    = null;
        private String subject      = null;
        
        /**
         * Required parameters
         * 
         * @param userName user name obtained from paypal
         * @param password password obtained from paypal
         */
        public Builder(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }
        
        /**
         * Optional parameter 
         * If you use an API certificate, do not include this parameter
         * 
         * @param val signature
         * @return
         */
        public Builder signature(String val) {
            signature = val;
            return this;
        }

        /**
         * Optional parameter 
         * Email address of a PayPal account that has granted you permission to 
         * make this call.
         * Set this parameter only if you are calling an API on a different 
         * user��s behalf
         * 
         * @param val subject
         * @return
         */
        public Builder subject(String val) {
            subject = val;
            return this;
        }

        /**
         * Returns instance of BaseProfile class
         * 
         * @return 
         */
        public BaseProfile build() {
            return new BaseProfile(this);
        }
    }
    
    
    /**
     * Private constructor invoked by builder static class
     * 
     * @param builder
     */
    private BaseProfile(Builder builder) {

        userName    = builder.userName;
        password    = builder.password;
        signature   = builder.signature;
        subject     = builder.subject;
    }


	@Override
	public Map<String, String> getNVPMap() {
		/* create and return map */
        Map<String, String> nvpMap = new HashMap<String, String>();
        nvpMap.put("USER", userName);
        nvpMap.put("PWD", password);
        if (signature != null) {
            nvpMap.put("SIGNATURE", signature);
        }
        if (subject != null) {
            nvpMap.put("SUBJECT", subject);
        }

        return nvpMap;
	}
	
	@Override
    public String toString() {

		return "Instance of User class with values: userName - "
				+ userName + ", password: " + password + ", signature: " 
                + signature + ", subject: " + subject;
    }

}
