package com.jy.web.authorization;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.jy.web.utils.SessionAttribute;

public class UserLogoutSuccessHandler extends SecurityContextLogoutHandler {
	
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
		HttpSession session = request.getSession();
		session.removeAttribute(SessionAttribute.USERID);
		session.removeAttribute(SessionAttribute.USER);
		final String url = "/signin";
		try {
			response.sendRedirect(request.getContextPath()  + url);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
