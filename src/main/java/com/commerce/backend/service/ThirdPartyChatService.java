package com.commerce.backend.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.commerce.backend.helper.ChatRoom;
import com.commerce.backend.helper.MessageRequest;
import com.commerce.backend.model.entity.UserChat;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessagingException;


public interface ThirdPartyChatService {
   Page<UserChat> findChatBySenderId(Long userId);
   Page<UserChat> findChatByReciverId(Long userId);
   Page<UserChat> findChatBySenderAndReciver(Long Reciver);
   Page<UserChat> findChatBySenderAndReciverAndAds(Long reciver,Long sender, Long ads);
   BatchResponse sendChatMessage(MessageRequest request) throws Exception;
   Page<UserChat> findChatByRoom(ChatRoom room, Pageable pagable);
}
