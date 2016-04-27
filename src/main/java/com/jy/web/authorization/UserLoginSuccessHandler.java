package com.jy.web.authorization;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

public class UserLoginSuccessHandler extends
		SavedRequestAwareAuthenticationSuccessHandler {

	private Logger logger = Logger.getLogger(UserLoginSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		/*org.springframework.security.core.userdetails.User se_user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		String username = null;
		username = se_user.getUsername();
		User user = userDao.findByUsername(username);
		HttpSession session = request.getSession();
		if(null!=session){
			if(null!=SessionAttribute.USERID)
			session.removeAttribute(SessionAttribute.USERID);
			if(null!=SessionAttribute.USER)
			session.removeAttribute(SessionAttribute.USER);
		}
		session.setAttribute(SessionAttribute.USERID, user.getPk().getId());
		session.setAttribute(SessionAttribute.USER, user);*/
		if (authentication != null) {
			DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY");
			if( defaultSavedRequest != null){
				String requestUrl = defaultSavedRequest.getRequestURL();
				logger.debug("redirect to "+requestUrl);				
	            getRedirectStrategy().sendRedirect(request, response, requestUrl);
	       
			}else{
				logger.debug("redirect to home page...");
				response.sendRedirect(request.getContextPath()+"/restaurant/user/home");
			}			
		}
	}

}
