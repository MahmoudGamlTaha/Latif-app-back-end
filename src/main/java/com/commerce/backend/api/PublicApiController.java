package com.commerce.backend.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Controller
@RequestMapping("/api/public")

public abstract class PublicApiController {
	private static final Logger logger = LoggerFactory.getLogger(PublicApiController.class);
	
	public Logger getLogger() {
          return logger;		
	}
}
