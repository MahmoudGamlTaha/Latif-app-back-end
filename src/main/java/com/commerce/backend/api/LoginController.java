package com.commerce.backend.api;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
  
	@RolesAllowed("USER")
	@RequestMapping( method = RequestMethod.GET, path = "/")
	public String get() {
	return "tr";  
  }
	@RequestMapping( method = RequestMethod.GET, path = "/test")
	public String get2() {
		return "tr";  
	  }
}
