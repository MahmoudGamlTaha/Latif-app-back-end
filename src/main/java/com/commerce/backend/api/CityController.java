package com.commerce.backend.api;

import org.springframework.web.bind.annotation.GetMapping;

import com.commerce.backend.model.response.BasicResponse;

public class CityController extends PublicApiController {
   @GetMapping(value = "/cites")
	public BasicResponse cites() {
	   BasicResponse response = new BasicResponse();
	   
	   return response;
   }
}
