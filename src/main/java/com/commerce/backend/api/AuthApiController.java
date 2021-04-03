package com.commerce.backend.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.commerce.backend.model.response.BasicResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Controller
@RequestMapping("/api/auth")

public abstract class AuthApiController {
	private static final Logger logger = LoggerFactory.getLogger(AuthApiController.class);
	
	public Logger getLogger() {
          return logger;		
	}
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BasicResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
         BasicResponse response = new BasicResponse();
         response.setMsg(ex.getMessage());
         response.setSuccess(false);
    	return new ResponseEntity<BasicResponse>(response, HttpStatus.NOT_ACCEPTABLE);
    }
    
    @ExceptionHandler(MethodArgumentConversionNotSupportedException.class)
    public ResponseEntity<BasicResponse> handleMethodArgumentConversionNotSupported(MethodArgumentConversionNotSupportedException ex){
    	 BasicResponse response = new BasicResponse();
         response.setMsg(ex.getMessage());
         response.setSuccess(false);
    	return new ResponseEntity<BasicResponse>(response, HttpStatus.NOT_ACCEPTABLE);
    }
}
