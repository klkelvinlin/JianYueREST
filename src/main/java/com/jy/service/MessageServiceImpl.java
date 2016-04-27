package com.jy.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jy.dao.FriendDao;
import com.jy.dao.MessageDao;
import com.jy.dao.UserDao;
import com.jy.domain.Friend;
import com.jy.domain.Message;
import com.jy.domain.MessageStatus;
import com.jy.domain.MessageType;
import com.jy.domain.User;
import com.jy.domain.rest.MessageVO;
import com.jy.exception.InvalidAttributesException;
import com.jy.utils.DateUtils;
import com.jy.utils.StringUtils;
import com.jy.utils.UploadUtils;
import com.jy.web.utils.ImageScale;


@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private FriendDao friendDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PushNotificationHubService pushNotificationHubService;

	@Transactional
	private void sendMessage(User sender, Long receiver, String content, String type, String status, MultipartFile file, Integer width, Integer height) throws InvalidAttributesException{
		try {
			if (null==sender) {
				throw new InvalidAttributesException("Invalid parameter - sender");
			}
			if (null==receiver) {
				throw new InvalidAttributesException("Invalid parameter - receiver");
			}
			Message msg = new Message();
			msg.setSenderId(sender.getId());
			msg.setReceiverId(receiver);
			if (null!=type && type.equals(MessageType.friendRequest.name())) {
				msg.setMsg(sender.getName() + " wants to make friends with you.");
				msg.setMsgType("friendRequest");
				msg.setMsgStatus("inrequest");
			}else{
				if (null==content) {
					throw new InvalidAttributesException("Invalid parameter - content");
				}
				msg.setMsg(content);		
			}
			msg.setSendtime(DateUtils.timeConverter());
			msg.setIsRead(false);
			msg.setIsDeleted(false);
			msg.setIsReplied(false);
			String fileName = "";
			if (null != file) {
				fileName = file.getOriginalFilename();
				if (StringUtils.hasLength(fileName)) {
					String finalFileName= UploadUtils.INSTANCE.getUploadFileName(sender.getUuid(), fileName);
					ImageScale.scaleImage(file.getInputStream(), UploadUtils.INSTANCE.getUploadMessageFilePath() + finalFileName);
					String finalFileHttpPath=UploadUtils.INSTANCE.getUploadMessageHttpPath()+ finalFileName;
					msg.setSenderPic(finalFileHttpPath);
					msg.setSenderPicWidth(width);
					msg.setSenderPicHeight(height);
				}
			}else{
				msg.setSenderPic(sender.getPic());
				msg.setSenderPicWidth(sender.getPicWidth());
				msg.setSenderPicHeight(sender.getPicHeight());
			}
			messageDao.create(msg);
			//push notification to ios device
//			pushNotificationHubService.push(receiver, msg.getMsg(),messageDao.getUserUnreadMessageCount(receiver));
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
	public void sendFriendRequestMsgToReceiver(User sender, Long receiverId) throws InvalidAttributesException {
		Message request = messageDao.getFriendRequestBySenderAndReceiver(sender.getId(), receiverId, MessageType.friendRequest.name(), MessageStatus.inrequest.name());
		if (null!=request) {
			throw new InvalidAttributesException("You already sent a request");
		}
		Friend friend = friendDao.getFriendExist(sender.getId(), receiverId);
		if (null!=friend) {
			throw new InvalidAttributesException("You are friends already");
		}
		sendMessage(sender, receiverId, null, MessageType.friendRequest.name() , null, null, null, null);
	}


	@Override
	public Integer getUserUnreadMessageCount(Long userId)
			throws InvalidAttributesException {
		return messageDao.getUserUnreadMessageCount(userId);
	}

	@Override
	public List<MessageVO> getUserMessages(Long userId, Integer pageNumber, Integer pageSize, Long timestamp)
			throws InvalidAttributesException {
		Date duetime = null;
		try {
			duetime = DateUtils.timeConverter(new Date(timestamp));
		} catch (ParseException e) {
			e.printStackTrace();
			throw new InvalidAttributesException(e.getMessage());
		}
		return messageDao.getUserUnreadMessage(userId, (pageNumber - 1) * pageSize, pageSize, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(duetime));
	}


	@Override
	@Transactional
	public void sendCommonMsg(User sender, Long receiver, String content,
			MultipartFile file, Integer width, Integer height) throws InvalidAttributesException {
		sendMessage(sender, receiver, content, null, null, file, width, height);
	}

	@Override
	@Transactional
	public void deleteMessage(User receiver, Long msgId)
			throws InvalidAttributesException {
		try {
			Message message = messageDao.getById(msgId);
			if (null == message) {
				throw new InvalidAttributesException("Message not found");
			}
			if (!receiver.getId().equals(message.getReceiverId())) {
				throw new InvalidAttributesException("This is not your message");
			}
			message.setIsDeleted(true);
			message.setIsRead(true);
			if (null!=message.getMsgType()&&null!=message.getMsgStatus()) {
				if (message.getMsgType().equals(MessageType.friendRequest.name())&&message.getMsgStatus().equals(MessageStatus.inrequest.name())) {
					message.setMsgStatus(MessageStatus.rejected.name());
				}
			}
			messageDao.update(message);
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvalidAttributesException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public void acceptFriendRequest(User receiver, Long msgId)
			throws InvalidAttributesException {
		try {
			Message message = messageDao.getById(msgId);
			if (null == message) {
				throw new InvalidAttributesException("Message not found");
			}
			if (!receiver.getId().equals(message.getReceiverId())) {
				throw new InvalidAttributesException("This is not your message");
			}
			if (!message.getMsgType().equals(MessageType.friendRequest.name())) {
				throw new InvalidAttributesException("This is not a friend request");
			}
			if (!message.getMsgStatus().equals(MessageStatus.inrequest.name())) {
				throw new InvalidAttributesException("This request is expired");
			}
			if (null==friendDao.getFriendExist(message.getSenderId(), message.getReceiverId())) {
				Friend friend = new Friend();
				friend.setCreatetime(DateUtils.timeConverter());
				friend.setUserId(message.getSenderId());
				friend.setFriendId(message.getReceiverId());
				friendDao.create(friend);
			}
			message.setIsReplied(true);
			message.setIsRead(true);
			message.setMsgStatus(MessageStatus.accepted.name());
			messageDao.update(message);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvalidAttributesException(e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public void rejectFriendRequest(User receiver, Long msgId)
			throws InvalidAttributesException {
		try {
			Message message = messageDao.getById(msgId);
			if (null == message) {
				throw new InvalidAttributesException("Message not found");
			}
			if (!receiver.getId().equals(message.getReceiverId())) {
				throw new InvalidAttributesException("This is not your message");
			}
			if (!message.getMsgType().equals(MessageType.friendRequest.name())) {
				throw new InvalidAttributesException("This is not a friend request");
			}
			if (!message.getMsgStatus().equals(MessageStatus.inrequest.name())) {
				throw new InvalidAttributesException("This request is expired");
			}
			message.setIsReplied(true);
			message.setIsRead(true);
			message.setMsgStatus(MessageStatus.rejected.name());
			messageDao.update(message);
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvalidAttributesException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public void replyMsg(User sender, String content,
			MultipartFile file, Integer width, Integer height, Long messageId)
			throws InvalidAttributesException {
		try {
			Message message = messageDao.getById(messageId);
			if (null == message) {
				throw new InvalidAttributesException("Cannot find the message");
			}
			if (!sender.getId().equals(message.getReceiverId())) {
				throw new InvalidAttributesException("You are not the receiver");
			}
			message.setIsReplied(true);
			message.setIsRead(true);
			messageDao.update(message);
			sendMessage(sender, message.getSenderId(), content, null, null, file, width, height);
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvalidAttributesException(e.getMessage());
		}
	}

}
 