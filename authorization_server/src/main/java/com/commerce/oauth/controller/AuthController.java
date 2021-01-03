package com.commerce.oauth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController{
	@RequestMapping("/")
	public String login() {
	  return "login";	
	}
}