package com.jy.web.rest;

import java.util.List;
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
import com.jy.domain.rest.MessageVO;
import com.jy.domain.rest.MessageWithCountVO;
import com.jy.exception.InvalidAttributesException;
import com.jy.service.MessageService;
import com.jy.utils.DateUtils;
import com.jy.utils.JsonResponse;
import com.jy.utils.JsonResponseACK;
import com.jy.utils.JsonResponseWithError;
import com.jy.utils.JsonResponseWithObj;
import com.jy.utils.StringUtils;
import com.jy.web.validator.UserFormValidator;


@RequestMapping("/r/message")
@Controller
public class MessageController{
	@Autowired
	private UserFormValidator userFormValidator;
	
	@Autowired
	private MessageService messageService;
	
	private Logger logger = Logger.getLogger(MessageController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody JsonResponse list(HttpServletRequest request) {
		try {
			String uuid = request.getParameter("uuid");
			String number = request.getParameter("pageNumber");
			String size = request.getParameter("pageSize");
			String timestamp = request.getParameter("timestamp");
			if (timestamp.indexOf(".")!=-1) {
				timestamp = timestamp.substring(0, timestamp.indexOf("."));
			}
			User user = userFormValidator.validateSecurity(uuid);
			Integer count = messageService.getUserUnreadMessageCount(user.getId());
			List<MessageVO> messages = messageService.getUserMessages(user.getId(), Integer.parseInt(number), Integer.parseInt(size), Long.parseLong(timestamp));
			MessageWithCountVO mcvo = new MessageWithCountVO();
			mcvo.setCount(count);
			mcvo.setMessages(messages);
			return new JsonResponseWithObj(JsonResponseACK.Success.name(), DateUtils.switchNowToString(),mcvo);
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Invalid paramer - pageNumber, pageSize, timestamp");
		} catch (InvalidAttributesException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Unknown error");
		}
	}
	
	@RequestMapping(value = "/unreadCount", method = RequestMethod.GET)
	public @ResponseBody JsonResponse unreadCount(HttpServletRequest request) {
		try {
			String uuid = request.getParameter("uuid");
			User user = userFormValidator.validateSecurity(uuid);
			return new JsonResponseWithObj(JsonResponseACK.Success.name(), DateUtils.switchNowToString(),messageService.getUserUnreadMessageCount(user.getId()));
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Invalid paramer - userId, postId, bidId, projectId");
		} catch (InvalidAttributesException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Unknown error");
		}
	}
	
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public @ResponseBody JsonResponse send(HttpServletRequest request) {
		try {
			String uuid = request.getParameter("uuid");
			String receiver = request.getParameter("receiver");
			String content = request.getParameter("content");
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
			messageService.sendCommonMsg(user, Long.parseLong(receiver), content, file, w, h);
			return new JsonResponse(JsonResponseACK.Success.name(), DateUtils.switchNowToString());
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Invalid paramer - receiver, width, height");
		} catch (InvalidAttributesException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Unknown error");
		}
	}
	
	@RequestMapping(value = "/sendFriendRequest", method = RequestMethod.POST)
	public @ResponseBody JsonResponse sendFriendRequest(HttpServletRequest request) {
		try {
			String uuid = request.getParameter("uuid");
			String receiver = request.getParameter("receiver");
			User user = userFormValidator.validateSecurity(uuid);
			messageService.sendFriendRequestMsgToReceiver(user, Long.parseLong(receiver));
			return new JsonResponse(JsonResponseACK.Success.name(), DateUtils.switchNowToString());
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Invalid paramer - receiver");
		} catch (InvalidAttributesException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Unknown error");
		}
	}
	
	@RequestMapping(value = "/acceptFriendRequest", method = RequestMethod.POST)
	public @ResponseBody JsonResponse acceptFriendRequest(HttpServletRequest request) {
		try {
			String uuid = request.getParameter("uuid");
			String msgId = request.getParameter("messageId");
			User user = userFormValidator.validateSecurity(uuid);
			messageService.acceptFriendRequest(user, Long.parseLong(msgId));
			return new JsonResponse(JsonResponseACK.Success.name(), DateUtils.switchNowToString());
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Invalid paramer - messageId");
		} catch (InvalidAttributesException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Unknown error");
		}
	}
	
	@RequestMapping(value = "/rejectFriendRequest", method = RequestMethod.POST)
	public @ResponseBody JsonResponse rejectFriendRequest(HttpServletRequest request) {
		try {
			String uuid = request.getParameter("uuid");
			String msgId = request.getParameter("messageId");
			User user = userFormValidator.validateSecurity(uuid);
			messageService.rejectFriendRequest(user, Long.parseLong(msgId));
			return new JsonResponse(JsonResponseACK.Success.name(), DateUtils.switchNowToString());
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Invalid paramer - messageId");
		} catch (InvalidAttributesException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Unknown error");
		}
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody JsonResponse delete(HttpServletRequest request) {
		try {
			String uuid = request.getParameter("uuid");
			String msgId = request.getParameter("messageId");
			User user = userFormValidator.validateSecurity(uuid);
			messageService.deleteMessage(user, Long.parseLong(msgId));
			return new JsonResponse(JsonResponseACK.Success.name(), DateUtils.switchNowToString());
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Invalid paramer - messageId");
		} catch (InvalidAttributesException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Unknown error");
		}
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public @ResponseBody JsonResponse reply(HttpServletRequest request) {
		try {
			String uuid = request.getParameter("uuid");
			String content = request.getParameter("content");
			String messageId = request.getParameter("messageId");
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
			messageService.replyMsg(user, content, file, w, h, Long.parseLong(messageId));
			return new JsonResponse(JsonResponseACK.Success.name(), DateUtils.switchNowToString());
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			return new JsonResponseWithError(JsonResponseACK.Failure.name(), DateUtils.switchNowToString(), "Invalid paramer - receiver, messageId");
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
