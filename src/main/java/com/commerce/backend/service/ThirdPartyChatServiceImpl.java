package com.commerce.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.commerce.backend.dao.UserChatRepository;
import com.commerce.backend.model.entity.UserChat;

public class ThirdPartyChatServiceImpl implements ThirdPartyChatService {

	@Autowired
	UserChatRepository userChatRepository;
	
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
	public Page<UserChat> findChatBySenderAndReciverAndAds(Long Reciver, Long ads) {
		
		return null;
	}

}
