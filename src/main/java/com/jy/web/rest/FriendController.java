package com.jy.web.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.domain.User;
import com.jy.domain.rest.FriendVO;
import com.jy.domain.rest.FriendWithCountVO;
import com.jy.exception.InvalidAttributesException;
import com.jy.service.FriendService;
import com.jy.utils.DateUtils;
import com.jy.utils.JsonResponse;
import com.jy.utils.JsonResponseACK;
import com.jy.utils.JsonResponseWithError;
import com.jy.utils.JsonResponseWithObj;
import com.jy.utils.ObjectsCacheUtils;
import com.jy.web.validator.UserFormValidator;


@RequestMapping("/r/friend")
@Controller
public class FriendController{
	
	@Autowired
	private FriendService friendService;
	
	@Autowired
	private UserFormValidator userFormValidator;
	
	private ObjectsCacheUtils objectsCache = ObjectsCacheUtils.getInstance();
	
	private Logger logger = Logger.getLogger(FriendController.class);
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public @ResponseBody JsonResponse signup(HttpServletRequest request) {
		try {
			String uuid = request.getParameter("uuid");
			User user = userFormValidator.validateSecurity(uuid);
			String number = request.getParameter("pageNumber");
			String size = request.getParameter("pageSize");
			String timestamp = request.getParameter("timestamp");
			if (timestamp.indexOf(".")!=-1) {
				timestamp = timestamp.substring(0, timestamp.indexOf("."));
			}
			List<FriendVO> friends = friendService.getUserFriends(user.getId(), Integer.parseInt(number), Integer.parseInt(size), Long.parseLong(timestamp));
			FriendWithCountVO fcvo = new FriendWithCountVO();
			Integer count = friendService.getUserFriendsCount(user.getId());
			fcvo.setCount(count);
			fcvo.setFriends(friends);
			return new JsonResponseWithObj(JsonResponseACK.Success.name(), DateUtils.switchNowToString(),fcvo);
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "PageNumber, pageSize, timestamp should be numbers.");
		} catch (InvalidAttributesException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Unknown error");
		}
	}
	
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public @ResponseBody JsonResponse unreadCount(HttpServletRequest request) {
		try {
			String uuid = request.getParameter("uuid");
			User user = userFormValidator.validateSecurity(uuid);
			return new JsonResponseWithObj(JsonResponseACK.Success.name(), DateUtils.switchNowToString(),friendService.getUserFriendsCount(user.getId()));
		} catch (InvalidAttributesException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Unknown error");
		}
	}

	@RequestMapping(value="/unbind", method = RequestMethod.POST)
	public @ResponseBody JsonResponse editProfile(HttpServletRequest request) {
		try {
			String uuid = request.getParameter("uuid");
			String friendId = request.getParameter("friendId");
			User user = userFormValidator.validateSecurity(uuid);
			friendService.unfriendUser(user.getId(), Long.parseLong(friendId));
			return new JsonResponse(JsonResponseACK.Success.name(), DateUtils.switchNowToString());
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Invalid paramer - userId");
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
