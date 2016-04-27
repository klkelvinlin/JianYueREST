package com.jy.web.validator;

import com.jy.exception.InvalidAttributesException;


public class PostFormValidator{

	
	
	private void validateLength(String str, int length, String field) throws InvalidAttributesException{
		if (str.length()>length) {
			throw new InvalidAttributesException("The field -"+field+" is too long");
		}
	}
	
	public void validateTitle(String title) throws InvalidAttributesException{
		validateLength(title,50,"Title");
	}
	
	public void validateDetails(String details) throws InvalidAttributesException{
		validateLength(details, 800, "Details");
	}
	
	
}
