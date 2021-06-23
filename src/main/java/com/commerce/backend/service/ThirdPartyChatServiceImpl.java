package com.commerce.backend.service;

import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.security.auth.login.AccountNotFoundException;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.commerce.backend.api.PublicApiController;
import com.commerce.backend.constants.MessageType;
import com.commerce.backend.constants.SystemConstant;
import com.commerce.backend.dao.CustomUserAdsRepo;
import com.commerce.backend.dao.UserAdsRepository;
import com.commerce.backend.dao.UserAdsRepositoryCustom;
import com.commerce.backend.dao.UserChatRepository;
import com.commerce.backend.dao.UserRepository;
import com.commerce.backend.helper.ChatRoom;
import com.commerce.backend.helper.MessageRequest;
import com.commerce.backend.model.entity.SystemSetting;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.entity.UserAds;
import com.commerce.backend.model.entity.UserChat;
import com.commerce.backend.model.entity.VerificationToken;
import com.google.firebase.messaging.Notification;
import com.google.firebase.auth.AuthErrorCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

@Service
public class ThirdPartyChatServiceImpl implements ThirdPartyChatService {
	private static final Logger logger = LoggerFactory.getLogger(ThirdPartyChatServiceImpl.class);
	@Autowired
	UserChatRepository userChatRepository;
	
	@Autowired
    UserRepository userRepository;
	
	@Autowired
	CustomUserAdsRepo userAdsRepo;
	
	FirebaseMessaging firebaseMessage;
    FirebaseAuth firebaseAuth;
    @Autowired
    public ThirdPartyChatServiceImpl( FirebaseMessaging firebaseMessage,  FirebaseAuth firebaseAuth){
    	this.firebaseMessage = firebaseMessage;
    	this.firebaseAuth = firebaseAuth;
    }
	
    @Override
	public Page<UserChat> findChatBySenderId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<UserChat> findChatByReciverId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<UserChat> findChatBySenderAndReciver(Long Reciver) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<UserChat> findChatBySenderAndReciverAndAds(Long reciver, Long sender, Long ads) {
		Pageable pagable = PageRequest.of(0, SystemConstant.MOBILE_PAGE_SIZE); 
		Page<UserChat> chating =  this.userChatRepository.getUserChatBySenderIdAndReciverIdAndItemId(sender, reciver, ads, pagable);
		return chating;
	}
	
	@Override
	public Page<UserChat> findChatByRoom(ChatRoom room, Pageable pagable) {
		//Pageable pagable = PageRequest.of(0, SystemConstant.MOBILE_PAGE_SIZE); 
		Page<UserChat> chating =  this.userChatRepository.findChatByRoom(room.getRoom(), pagable);
		return chating;
	}
	
    @Transactional
	@Override
	public BatchResponse sendChatMessage(MessageRequest request) throws Exception {
		// TODO Auto-generated method stub
    	 User sender = this.userRepository.findById(request.getSender()).orElse(null);
       UserAds ads =  userAdsRepo.findById(request.getAd_item()).orElse(null);
       String lname = sender.getLastName() == null ? "" :sender.getLastName();
       List<Long> receivers = new LinkedList<Long>();
         if(request.getRecevier() == null || request.getRecevier() == 0){
        	 List<UserChat> chatReciever = this.userChatRepository.findReciverByChatRoom(request.getRoom(), sender.getId());
            if(chatReciever.isEmpty()) {
            	chatReciever = this.userChatRepository.findReciverByChatRoom(request.getRoom());
            	
            	 chatReciever.stream().forEach((userChat) ->{
            		 if(userChat.getSenderId() != sender.getId())
                	   receivers.add(userChat.getSenderId());
                 });  
            	 if(chatReciever.isEmpty()) {
                 	throw new AccountNotFoundException("Not found chat");
                 	}
            }else {
        	 chatReciever.stream().forEach((userChat) ->{
            	 receivers.add(userChat.getReciverId());
             });  
            }
         }else {
        	 receivers.add(request.getRecevier());
         }
         
	        return this.SendMessagesToReceviers(receivers, sender, ads, request);
	}
    
    private BatchResponse SendMessagesToReceviers(List<Long> receivers, User sender, UserAds ads, MessageRequest request) throws FirebaseMessagingException {
    	 
    	Notification notification = Notification
	                .builder()
	                .setTitle(MessageType.NEW_MESSAGE.getMessage())
	                .setBody(request.getMessage())
	                .build();
    	
    	Map<String, String> data = new HashMap<String, String>();
    	 String lname = sender.getLastName() == null ? "" :sender.getLastName();
    	 String avatar = sender.getAvatar()== null? SystemConstant.DEFAULT_IMAGE : sender.getAvatar();
         data.put("sender_name", sender.getFirstName() + " " + lname);
         data.put("sender_avater", avatar );
         data.put("prod_name", ads.getName());
         data.put("message", request.getMessage());
         data.put("prod_id", String.valueOf(ads.getId()));
        
       
      //   Date maxDate = receiver.getToken().stream().filter(emp -> emp.getExpiryDate() != null).map(VerificationToken::getExpiryDate).max(Date::compareTo).get();
         Comparator<VerificationToken> comparator = Comparator.comparing(VerificationToken::getExpiryDate);
        
         List<Message> messages  = new LinkedList<Message>();
         // push---------- --------------
         receivers.stream().forEach((userId) -> { 
         User receiver = this.userRepository.findById(userId).orElse(null);
         Hibernate.initialize(receiver.getToken());
         if(receiver == null || ( receiver != null && receiver.getToken().size() == 0)) {
        	 throw new UsernameNotFoundException("User Not found");
         }
         request.setRecevier(receiver.getId());
         UserChat chat = this.saveInternalyMessage(request);
         data.put("chat_room", chat.getRoom());
         String firebaseToken = receiver.getToken().stream().filter(subs -> subs.getExpiryDate() != null).max(comparator).get().getFirebaseToken();
         logger.info("token:",firebaseToken);
         Message message = Message
	                .builder()
	                .setToken(firebaseToken)
	                .setNotification(notification)
	                .putAllData(data)
	                .build();
         messages.add(message);
         });
         
	        return firebaseMessage.sendAll(messages);
    }
    
    private UserChat saveInternalyMessage(MessageRequest request) {
    	UserChat chat = new UserChat();
    	UserChat chatRoom = this.userChatRepository.getChatRoom(request.getRecevier(), request.getSender(), request.getAd_item());
    	String chatUId = null;
    	if(chatRoom == null)
    	    chatUId = UUID.randomUUID().toString();
    	else 
    		chatUId = chatRoom.getRoom();
    	
    	 chat.setMessage(request.getMessage());
         chat.setReciverId(request.getRecevier());
         chat.setSenderId(request.getSender());
         chat.setCreateAt(new Date());
         chat.setRoom(chatUId);
         chat.setItemId(request.getAd_item());
         chat.setDeviceModel(request.getDevice_model());
        this.userChatRepository.save(chat);
        return chat;
         
    }
    @Deprecated
    private String checkUserExist(MessageRequest request)  {
    	try {
    	UserRecord userRecord = FirebaseAuth.getInstance().getUser(getUUID(request));
           return userRecord.getUid();
    	}catch(FirebaseAuthException ex) {
            AuthErrorCode code	= ex.getAuthErrorCode();
           System.out.print("error code"+ code);
    	}
    	return null;
    }
    private String registerFCM(MessageRequest requestMessage) throws FirebaseAuthException {
    	CreateRequest request = new CreateRequest()
    		    .setUid(getUUID(requestMessage))
    		    .setEmailVerified(false)
    		    .setPassword("rtdj9pir")
    		    .setDisabled(false);
    	
    		UserRecord userRecord = firebaseAuth.createUser(request);
    		return userRecord.getUid();
    }
    
    private String getUUID(MessageRequest requestMessage) {
    	//String token = requestMessage.getRecevier().toString()+"-"+requestMessage.getSender().toString()+requestMessage.getAd_item();
    	return 	UUID.randomUUID().toString();
    }

}
