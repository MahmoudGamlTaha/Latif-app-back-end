package com.commerce.backend.api;

import org.springframework.web.bind.annotation.GetMapping;

import com.commerce.backend.model.response.BasicResponse;

public class ThirdPartyChatController extends PublicApiController{
    
	@GetMapping(value = "/chat/history")
	public BasicResponse getChatHistory() {
		BasicResponse response = new BasicResponse();
	   	
		return response;
	}
}
