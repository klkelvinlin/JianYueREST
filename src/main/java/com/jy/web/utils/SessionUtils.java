package com.jy.web.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jy.domain.User;

public class SessionUtils {
	public static void resetUserSession(HttpServletRequest request, User user){
		HttpSession session = request.getSession();
		if(null!=session){
			if(null!=SessionAttribute.USERID)
			session.removeAttribute(SessionAttribute.USERID);
			if(null!=SessionAttribute.USER)
			session.removeAttribute(SessionAttribute.USER);
		}
		session.setAttribute(SessionAttribute.USERID, user.getId());
		session.setAttribute(SessionAttribute.USER, user);
	}
}
