package com.jy.web.validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.jy.dao.UserDao;
import com.jy.domain.User;
import com.jy.exception.InvalidAttributesException;
import com.jy.utils.NumberUtils;
import com.jy.utils.StringUtils;


public class UserFormValidator{

	@Autowired
	private UserDao userDao;
	
	public User validateSecurity(String uuid) throws InvalidAttributesException{
		User user= userDao.getByUUID(uuid);
		if (null==user) {
			throw new InvalidAttributesException("Invalid user");
		}
		return user;
	}
	
	public void validateGender(String gender) throws InvalidAttributesException{
		if (!"M".equalsIgnoreCase(gender)&&!"F".equalsIgnoreCase(gender)&&!"".equalsIgnoreCase(gender)) {
			throw new InvalidAttributesException("Invalid parameter - gender");
		}
	}
	
	public void validateAge(String age) throws InvalidAttributesException{
		if (StringUtils.hasLength(age)) {
			if (!NumberUtils.isInteger(age)) {
				throw new InvalidAttributesException("Invalid parameter - age");
			}
		}
	}
	
	private void validateLength(String str, int length, String field) throws InvalidAttributesException{
		if (str.length()>length) {
			throw new InvalidAttributesException("The field -"+field+" is too long");
		}
	}
	
	
}
