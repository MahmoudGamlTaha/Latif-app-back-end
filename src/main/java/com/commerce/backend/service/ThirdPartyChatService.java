package com.commerce.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.commerce.backend.model.entity.UserChat;

@Service
public interface ThirdPartyChatService {
   Page<UserChat> findChatBySenderId(Long userId);
   Page<UserChat> findChatByReciverId(Long userId);
   Page<UserChat> findChatBySenderAndReciver(Long Reciver);
   Page<UserChat> findChatBySenderAndReciverAndAds(Long Reciver, Long ads);
}
