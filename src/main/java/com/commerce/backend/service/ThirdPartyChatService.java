package com.commerce.backend.service;


import javax.security.auth.login.AccountNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.commerce.backend.helper.ChatRoom;
import com.commerce.backend.helper.MessageRequest;
import com.commerce.backend.model.entity.UserChat;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessagingException;


public interface ThirdPartyChatService {
   Page<UserChat> findChatBySenderId(Long userId, Pageable page);
   Page<UserChat> findChatByReciverId(Long userId);
   Page<UserChat> findChatBySenderAndReciver(Long Reciver);
   Page<UserChat> findChatBySenderAndReciverAndAds(Long reciver,Long sender, Long ads);
   Object sendChatMessage(MessageRequest request) throws Exception;
   Page<UserChat> findChatByRoom(ChatRoom room, Pageable pagable);
   //
   UserChat checkExistHistory(Long ads, Pageable pagable) throws AccountNotFoundException; 
   
   Page<UserChat> findNextPageByMessageId(String rowID, String room, Pageable pagable);
}
