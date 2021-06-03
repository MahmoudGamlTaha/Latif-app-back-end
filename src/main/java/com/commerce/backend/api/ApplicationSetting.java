package com.commerce.backend.api;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.constants.SystemConstant;
import com.commerce.backend.dao.SystemSettingRepository;
import com.commerce.backend.model.entity.SystemSetting;
import com.commerce.backend.model.response.BasicResponse;
//import com.commerce.backend.service.cache.PolicyService;

@RestController
public class ApplicationSetting extends PublicApiController{

	
	//private final PolicyService policyService;
	private final SystemSettingRepository systemSettingRepository;
	
	@Autowired
	 public ApplicationSetting(SystemSettingRepository systemSettingRepository) {
	//	this.policyService = policyService;
		this.systemSettingRepository = systemSettingRepository;
	}
	@GetMapping("/policy")
	public BasicResponse getPolicy() {
		BasicResponse response = new BasicResponse();
		 SystemSetting policy = this.systemSettingRepository.findByType(SystemConstant.Policy);
		 HashMap<String, Object> mapResponse = new HashMap<String, Object>();
		 mapResponse.put(MessageType.Data.getMessage(), policy.getDescription());
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
