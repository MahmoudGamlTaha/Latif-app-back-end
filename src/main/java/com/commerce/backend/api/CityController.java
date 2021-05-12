package com.commerce.backend.api;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.converter.blog.UrlOptionVOConverter;
import com.commerce.backend.dao.CityRepository;
import com.commerce.backend.model.dto.CityVO;
import com.commerce.backend.model.dto.UrlOptionVO;
import com.commerce.backend.model.response.BasicResponse;

@RestController
public class CityController extends PublicApiController {
  
	@Autowired
	private CityRepository cityReposioty;
	
	@Autowired
	private UrlOptionVOConverter urlOptionVoConverter;
	
	@GetMapping(value = "/cites")
	public BasicResponse cites() {
	    BasicResponse response = new BasicResponse();
	    response.setMsg(MessageType.Success.getMessage());
	   
	    HashMap<String, Object> responseMap = new HashMap<String, Object>();
	    List<UrlOptionVO> urlOptions = new LinkedList<UrlOptionVO>();
	    this.cityReposioty.findByActive(true).forEach(city -> {
	        urlOptions.add(this.urlOptionVoConverter.apply(city));    	
	    });
	    
	    responseMap.put(MessageType.Data.getMessage(), urlOptions);
	    response.setResponse(responseMap);
	   return response;
   }
	@PostMapping(value = "/create")
	public BasicResponse create(CityVO city) {
		BasicResponse response = new BasicResponse();
		HashMap<String, Object>  responseMap = new HashMap<String, Object>();
		
		response.setMsg(MessageType.Data.getMessage());
		response.setSuccess(true);
		response.setResponse(responseMap);
		return response;
	}
}

