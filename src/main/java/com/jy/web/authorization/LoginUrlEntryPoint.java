package com.jy.web.authorization;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class LoginUrlEntryPoint implements AuthenticationEntryPoint {

    public void commence(HttpServletRequest request, HttpServletResponse response,
              AuthenticationException authException) throws IOException, ServletException {
        String targetUrl = null;
        String url = request.getRequestURI();
   
        if(url.indexOf("manager") != -1){
            targetUrl = "/master/signin"; //redirect to manager panel
        }else{
            targetUrl = "/signin";
        }
   
        targetUrl = request.getContextPath() + targetUrl;
        response.sendRedirect(targetUrl);
    }

}