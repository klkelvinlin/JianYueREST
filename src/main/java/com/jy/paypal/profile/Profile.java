package com.jy.paypal.profile;

import java.io.Serializable;
import java.util.Map;

/**
 * Represents paypal user - password, user name etc.
 * @author wdong
 *
 */
public interface Profile extends Serializable {
	Map<String, String> getNVPMap();
}
