package com.commerce.backend.api;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.constants.SystemConstant;
import com.commerce.backend.helper.ChatHistoryRequest;
import com.commerce.backend.helper.ChatRoom;
import com.commerce.backend.helper.MessageRequest;
import com.commerce.backend.helper.resHelper;
import com.commerce.backend.model.entity.User;
import com.commerce.backend.model.entity.UserChat;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.service.ThirdPartyChatService;
import com.commerce.backend.service.UserService;

@RestController
public class ThirdPartyChatController extends PublicApiController{
    
	@Autowired
	ThirdPartyChatService thirdPartyChatService;
	
	@Autowired
	UserService userService;
	
	@GetMapping(value = {"/chat/history/list", "/chat/history/list?page={page}"})
	@ResponseBody
	public BasicResponse getChatHistory(@RequestBody ChatHistoryRequest chatRequest, @PathVariable(required = false) Integer page) {
		BasicResponse response = new BasicResponse();
		HashMap<String, Object> Messages = new HashMap<String, Object>();
		Pageable pagable = PageRequest.of(page, SystemConstant.MOBILE_PAGE_SIZE);
		Page<UserChat> chatting = this.thirdPartyChatService
				         .findChatBySenderAndReciverAndAds(chatRequest.getReciver(), chatRequest.getSender(), chatRequest.getAds());
	
		resHelper.res(chatting, true, MessageType.Success.getMessage(), pagable);
		return response;
	}
	
	@GetMapping(value = {"/chat/my-chat"})
	@ResponseBody
	public BasicResponse getUserChat(@RequestBody ChatHistoryRequest chatRequest, @PathVariable(required = false) Integer page) {
		BasicResponse response = new BasicResponse();
		HashMap<String, Object> Messages = new HashMap<String, Object>();
		Pageable pagable = PageRequest.of(page, SystemConstant.MOBILE_PAGE_SIZE);
		Page<UserChat> chatting = this.thirdPartyChatService
				         .findChatBySenderAndReciverAndAds(chatRequest.getReciver(), chatRequest.getSender(), chatRequest.getAds());
	
		resHelper.res(chatting, true, MessageType.Success.getMessage(), pagable);
		return response;
	}
	@GetMapping(value = {"/chat/history?room={room}", "/chat/history?room={room}&page={page}"})
	@ResponseBody
	public BasicResponse getChatRoom(String room, @RequestParam(required = false) Optional<Integer> page) {
		User user = this.userService.getCurrentUser(); 
		if(user == null ) {
		  throw new UsernameNotFoundException("User Not Found");	
		}
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setRoom(room);
		BasicResponse response = new BasicResponse();
		Pageable pagable = PageRequest.of(page.orElse(0), SystemConstant.MOBILE_PAGE_SIZE);
		Page<UserChat> chatting = this.thirdPartyChatService
				         .findChatByRoom(chatRoom, pagable);
	
		response = resHelper.res(chatting.getContent(), true, MessageType.Success.getMessage(), pagable);
		return response;
	}
	@PostMapping(value = "/chat/snd-msg")
	public BasicResponse sendNotification(@RequestBody MessageRequest request) throws Exception {
		//secure api
		User user = this.userService.getCurrentUser();
		if(user == null) {
			OAuth2Error err = new OAuth2Error(OAuth2ErrorCodes.ACCESS_DENIED);
			throw new OAuth2AuthenticationException(err, "Noting To Do");
		}
		
        BasicResponse response = new BasicResponse();
        HashMap<String, Object> mapResponse = new HashMap<String,Object>();
        Object res = this.thirdPartyChatService.sendChatMessage(request);
        mapResponse.put(MessageType.Data.getMessage(), res);
        response.setMsg(MessageType.Success.getMessage());
        response.setSuccess(true);
        response.setResponse(mapResponse);
		return response;
	}
}
