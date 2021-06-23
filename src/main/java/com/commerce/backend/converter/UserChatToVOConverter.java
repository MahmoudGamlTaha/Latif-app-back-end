package com.commerce.backend.converter;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.commerce.backend.model.dto.UserChatVO;
import com.commerce.backend.model.entity.UserChat;
@Component
public class UserChatToVOConverter implements Function<UserChat, UserChatVO> {

	@Override
	public UserChatVO apply(UserChat userChat) {
		UserChatVO userChatVO = new UserChatVO();
		userChatVO.setId(userChat.getId());
		userChatVO.setMessage(userChat.getMessage());
		userChatVO.setReciverId(userChat.getReciverId());
		userChatVO.setItemId(userChat.getItemId());
		userChatVO.setRoom(userChat.getRoom());
		userChatVO.setSenderId(userChat.getSenderId());
		return userChatVO;
	}

}
