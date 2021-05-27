package com.commerce.backend.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.model.response.BasicResponse;

@RestController
public class ApplicationSetting extends PublicApiController{

	@GetMapping("/policy")
	public BasicResponse getPolicy() {
		BasicResponse response = new BasicResponse();
		return response;
	}
	
	@GetMapping("/external")
	public BasicResponse getExternalLink() {
		BasicResponse response = new BasicResponse();
		response.setMsg(MessageType.Success.getMessage());
	    response.setSuccess(true);
		return response;
	}
}
