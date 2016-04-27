package com.jy.web.rest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jy.domain.User;
import com.jy.exception.InvalidAttributesException;
import com.jy.service.UserService;
import com.jy.utils.DateUtils;
import com.jy.utils.JsonResponse;
import com.jy.utils.JsonResponseACK;
import com.jy.utils.JsonResponseWithError;
import com.jy.utils.JsonResponseWithList;
import com.jy.utils.JsonResponseWithObj;
import com.jy.utils.ObjectsCacheUtils;
import com.jy.utils.StringUtils;
import com.jy.web.validator.UserFormValidator;


@RequestMapping("/r/user")
@Controller
public class UserController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserFormValidator userFormValidator;
	
	private ObjectsCacheUtils objectsCache = ObjectsCacheUtils.getInstance();
	
	private Logger logger = Logger.getLogger(UserController.class);
	
	@RequestMapping(value="/signin", method = RequestMethod.POST)
	public @ResponseBody JsonResponse signup(HttpServletRequest request) {
		try {
			//Register user
			String deviceToken = request.getParameter("deviceToken");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			userFormValidator.validateGender(gender);
			String interestIn = request.getParameter("interestIn");
			userFormValidator.validateGender(interestIn);
			String lat = request.getParameter("lat");
			String lng = request.getParameter("lng");
			String width = request.getParameter("width");
			String height = request.getParameter("height");
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Map<?, MultipartFile> files = multiRequest.getFileMap();
			MultipartFile file = null;
			if (null != files) {
				for (MultipartFile f : files.values()) {
					file = f;
					break;
				}
			}
			Integer w = null;
			Integer h = null;
			if (StringUtils.hasLength(width)) {
				w = Integer.parseInt(width);
			}
			if (StringUtils.hasLength(height)) {
				h = Integer.parseInt(height);
			}
			User user = userService.signin(name, deviceToken, gender, interestIn, file, w, h, lat, lng);
			//Read posts
			return new JsonResponseWithObj(JsonResponseACK.Success.name(), DateUtils.switchNowToString(),user);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Invalid paramer - width, height");
		} catch (InvalidAttributesException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Unknown error");
		}
	}
	
	@RequestMapping(value="/location/update", method = RequestMethod.POST)
	public @ResponseBody JsonResponse updateLocation(HttpServletRequest request) {
		try {
			String uuid = request.getParameter("uuid");
			String lat = request.getParameter("lat");
			String lng = request.getParameter("lng");
			User user = userFormValidator.validateSecurity(uuid);
			userService.updateLocation(user.getId(), lat, lng);
			return new JsonResponse(JsonResponseACK.Success.name(), DateUtils.switchNowToString());
		} catch (InvalidAttributesException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Unknown error");
		}
	}
	
	
	@RequestMapping(value="/profile", method = RequestMethod.GET)
	public @ResponseBody JsonResponse getProfile(HttpServletRequest request) {
		try {
			String uuid = request.getParameter("uuid");
			String interestIn = request.getParameter("interestIn");
			String lat = request.getParameter("lat");
			String lng = request.getParameter("lng");
			String pageNumber = request.getParameter("pageNumber");
			String size = request.getParameter("pageSize");
			String timestamp = request.getParameter("timestamp");
			if (timestamp.indexOf(".")!=-1) {
				timestamp = timestamp.substring(0, timestamp.indexOf("."));
			}
			User user = userFormValidator.validateSecurity(uuid);
			return new JsonResponseWithList(JsonResponseACK.Success.name(), DateUtils.switchNowToString(),userService.getUser(user.getId(), interestIn,Double.parseDouble(lat), Double.parseDouble(lng), Integer.parseInt(pageNumber), Integer.parseInt(size), Long.parseLong(timestamp)));
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "lat, lng, userId, pageSize, timestamp should be numbers.");
		} catch (InvalidAttributesException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Unknown error");
		}
	}

	@RequestMapping(value="/profile/edit", method = RequestMethod.POST)
	public @ResponseBody JsonResponse editProfile(HttpServletRequest request) {
		try {
			String uuid = request.getParameter("uuid");
			String gender = request.getParameter("gender");
			String interestIn = request.getParameter("interestIn");
			String lat = request.getParameter("lat");
			String lng = request.getParameter("lng");
			String width = request.getParameter("width");
			String height = request.getParameter("height");
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Map<?, MultipartFile> files = multiRequest.getFileMap();
			MultipartFile file = null;
			if (null != files) {
				for (MultipartFile f : files.values()) {
					file = f;
					break;
				}
			}
			Integer w = null;
			Integer h = null;
			if (StringUtils.hasLength(width)) {
				w = Integer.parseInt(width);
			}
			if (StringUtils.hasLength(height)) {
				h = Integer.parseInt(height);
			}
			User user = userFormValidator.validateSecurity(uuid);
			user = userService.updateUser(user, gender, interestIn, lat, lng, file, w, h);
			return new JsonResponseWithObj(JsonResponseACK.Success.name(), DateUtils.switchNowToString(), user);
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Invalid paramer - userId, width, height");
		} catch (InvalidAttributesException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Unknown error");
		}
	}
}
