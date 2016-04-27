package com.jy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jy.domain.User;
import com.jy.domain.rest.UserVO;
import com.jy.exception.InvalidAttributesException;


@Service
public interface UserService {
	
	public User getById(Long id);

	public User signin(String name, String deviceToken, String gender, String interestIn, MultipartFile file, Integer width, Integer height, String lat, String lng) throws InvalidAttributesException;
	
	public User updateUser(User user, String gender, String interestIn, String lat, String lng, MultipartFile file, Integer width, Integer height) throws InvalidAttributesException;
	
	public List<UserVO> getUser(Long userId, String interestIn, Double lat, Double lng, Integer pageNumber, Integer pageSize, Long timestamp) throws InvalidAttributesException;
	
	public void updateLocation(Long userId, String lat, String lng) throws InvalidAttributesException;
}
