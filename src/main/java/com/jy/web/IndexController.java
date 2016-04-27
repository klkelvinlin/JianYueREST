package com.jy.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RequestMapping("/index")
@Controller
public class IndexController{
	
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model uiModel) {
		System.out.println("asdf");
		return "index";
	}
	
	@RequestMapping(value="/test", method = RequestMethod.GET)
	public String test() {
		return "test";
	}
	
	@RequestMapping(value="/terms", method = RequestMethod.GET)
	public String terms() {
		return "terms";
	}
	
}
