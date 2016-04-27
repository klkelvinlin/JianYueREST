package com.jy.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jy.dao.UserDao;
import com.jy.dao.UserLocationDao;
import com.jy.domain.User;
import com.jy.domain.UserLocation;
import com.jy.domain.rest.UserVO;
import com.jy.exception.InvalidAttributesException;
import com.jy.utils.DateUtils;
import com.jy.utils.StringUtils;
import com.jy.utils.UploadUtils;
import com.jy.web.utils.ImageScale;
import com.jy.web.validator.UserFormValidator;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserLocationDao userLocationDao;
	
	@Autowired
	private UserFormValidator userFormValidator;

	@Override
	public User getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public User signin(String name, String deviceToken, String gender, String interestIn, MultipartFile file, Integer width, Integer height, 
			String lat, String lng) throws InvalidAttributesException {
		try {
			User user = new User();
			user.setName(name);
			user.setEnabled(true);
			user.setLastlogintime(DateUtils.timeConverter());
			user.setCreatetime(DateUtils.timeConverter());
			user.setDeviceToken(deviceToken);
			user.setUuid(UUID.randomUUID().toString());
			user.setEnabled(true);
			user.setGender(gender);
			user.setInterestIn(interestIn);
			user.setLikes(0);
			String fileName = "";
			if (null != file) {
				fileName = file.getOriginalFilename();
				if (StringUtils.hasLength(fileName)) {
					String finalFileName= UploadUtils.INSTANCE.getUploadFileName(user.getUuid(), fileName);
					ImageScale.scaleImage(file.getInputStream(), UploadUtils.INSTANCE.getUploadProfileFilePath() + finalFileName);
					String finalFileHttpPath=UploadUtils.INSTANCE.getUploadprofileHttpPath()+ finalFileName;
					user.setPic(finalFileHttpPath);
					user.setPicWidth(width);
					user.setPicHeight(height);
				}
			}
			userDao.create(user);
			if (StringUtils.hasLength(lat)&&StringUtils.hasLength(lng)) {
				Double latitude = null;
				Double longitude = null;
				if(StringUtils.hasLength(lat)&&StringUtils.hasLength(lng)){
					latitude = Double.parseDouble(lat);
					longitude = Double.parseDouble(lng);
				}
				UserLocation location = new UserLocation();
				location.setUserId(user.getId());
				location.setLat(latitude);
				location.setLng(longitude);
				location.setUpdatetime(DateUtils.timeConverter());
				userLocationDao.create(location);
			}
			return user;
		} catch (IOException e) {
			e.printStackTrace();
			throw new InvalidAttributesException(e.getMessage());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InvalidAttributesException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public void updateLocation(Long userId, String lat, String lng)
			throws InvalidAttributesException {
		try {
			UserLocation location = userLocationDao.getUserLocation(userId);
			if (null!=location) {
				Double latitude = null;
				Double longitude = null;
				if(StringUtils.hasLength(lat)&&StringUtils.hasLength(lng)){
					latitude = Double.parseDouble(lat);
					longitude = Double.parseDouble(lng);
				}
				location.setLat(latitude);
				location.setLng(longitude);
				location.setUpdatetime(DateUtils.timeConverter());
				userLocationDao.update(location);
			}else{
				location = new UserLocation();
				Double latitude = null;
				Double longitude = null;
				if(StringUtils.hasLength(lat)&&StringUtils.hasLength(lng)){
					latitude = Double.parseDouble(lat);
					longitude = Double.parseDouble(lng);
				}
				location.setLat(latitude);
				location.setLng(longitude);
				location.setUpdatetime(DateUtils.timeConverter());
				location.setUserId(userId);
				userLocationDao.create(location);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new InvalidAttributesException("Lat and lng should be numbers");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InvalidAttributesException(e.getMessage());
		}
	}

	@Override
	public User updateUser(User user, String gender, String interestIn,
			String lat, String lng, MultipartFile file, Integer width, Integer height)
			throws InvalidAttributesException {
		try {
			user.setLastlogintime(DateUtils.timeConverter());
			//userFormValidator.validateGender(gender);
			//user.setGender(gender);
			userFormValidator.validateGender(interestIn);
			user.setInterestIn(interestIn);
			String fileName = "";
			if (null != file) {
				fileName = file.getOriginalFilename();
				if (StringUtils.hasLength(fileName)) {
					String finalFileName= UploadUtils.INSTANCE.getUploadFileName(user.getUuid(), fileName);
					ImageScale.scaleImage(file.getInputStream(), UploadUtils.INSTANCE.getUploadProfileFilePath() + finalFileName);
					String finalFileHttpPath=UploadUtils.INSTANCE.getUploadprofileHttpPath()+ finalFileName;
					user.setPic(finalFileHttpPath);
					user.setPicWidth(width);
					user.setPicHeight(height);
				}
			}
			updateLocation(user.getId(), lat, lng);
			userDao.update(user);
			return user;
		} catch (IOException e) {
			e.printStackTrace();
			throw new InvalidAttributesException(e.getMessage());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InvalidAttributesException(e.getMessage());
		}
	}

	@Override
	public List<UserVO> getUser(Long userId, String interestIn, Double lat,
			Double lng, Integer pageNumber, Integer pageSize, Long timestamp)
			throws InvalidAttributesException {
		try {
			if (!interestIn.equals("M")&&!interestIn.equals("F")) {
				interestIn = null;
			}
			Date duetime = DateUtils.timeConverter(new Date(timestamp));
			return userDao.getUser(userId, interestIn, lat, lng, (pageNumber-1)*pageSize, pageSize, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(duetime));
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvalidAttributesException(e.getMessage());
		}
	}

	
}
