package com.commerce.backend.api;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.model.entity.Policy;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.service.cache.PolicyService;

@RestController
public class ApplicationSetting extends PublicApiController{

	
	private final PolicyService policyService;
	
	@Autowired
	 public ApplicationSetting(PolicyService policyService) {
		this.policyService = policyService;
	}
	@GetMapping("/policy")
	public BasicResponse getPolicy() {
		BasicResponse response = new BasicResponse();
		 Policy policy = this.policyService.find();
		 HashMap<String, Object> mapResponse = new HashMap<String, Object>();
		 if(policy != null) {
		   mapResponse.put(MessageType.Data.getMessage(), policy.getDescription());
		 }
		 response.setMsg(MessageType.Success.getMessage());
		 response.setSuccess(true);
		 response.setResponse(mapResponse);
		return response;
	}
	@PostMapping("/policy/update")
	public BasicResponse policyUpdate() {
		 BasicResponse response = new BasicResponse();
		 Policy policy = this.policyService.find();
		 HashMap<String, Object> mapResponse = new HashMap<String, Object>();
		 return response;
		
	}
	@GetMapping("/policies")
	public BasicResponse getPolicies() {
		BasicResponse response = new BasicResponse();
		 Policy policy = this.policyService.find();
		 HashMap<String, Object> mapResponse = new HashMap<String, Object>();
		 if(policy != null) {
		   mapResponse.put(MessageType.Data.getMessage(), policy);
		 }
		 response.setMsg(MessageType.Success.getMessage());
		 response.setSuccess(true);
		 response.setResponse(mapResponse);
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
