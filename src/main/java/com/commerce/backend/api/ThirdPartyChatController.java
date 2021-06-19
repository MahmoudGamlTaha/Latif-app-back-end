package com.commerce.backend.api;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.helper.MessageRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.service.ThirdPartyChatService;

@RestController
public class ThirdPartyChatController extends PublicApiController{
    
	@Autowired
	ThirdPartyChatService thirdPartyChatService;
	
	@GetMapping(value = "/chat/history")
	public BasicResponse getChatHistory() {
		BasicResponse response = new BasicResponse();
		return response;
	}
	
	@PostMapping(value = "/chat/snd-msg")
	public BasicResponse sendNotification(@RequestBody MessageRequest request) throws Exception {
        BasicResponse response = new BasicResponse();
        HashMap<String, Object> mapResponse = new HashMap<String,Object>();
        String res = this.thirdPartyChatService.sendChatMessage(request);
        mapResponse.put(MessageType.Data.getMessage(), res);
        response.setMsg(MessageType.Success.getMessage());
        response.setSuccess(true);
        response.setResponse(mapResponse);
		return response;
	}
}
