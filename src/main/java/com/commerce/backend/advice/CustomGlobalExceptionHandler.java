package com.commerce.backend.advice;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.commerce.backend.constants.MessageType;
import com.commerce.backend.model.response.BasicResponse;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        BasicResponse response = new BasicResponse();
        HashMap<String, Object> body = new HashMap<String, Object>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        response.setMsg(MessageType.Fail.getMessage());
        response.setResponse(body);
        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(response, headers, status);

    }
	@ExceptionHandler(value = {UsernameNotFoundException.class})
	protected ResponseEntity<BasicResponse> handleUserNotFoundException(UsernameNotFoundException ex){
		 BasicResponse response = new BasicResponse();
         response.setMsg(ex.getMessage());
         response.setSuccess(false);
    	return new ResponseEntity<BasicResponse>(response, HttpStatus.NOT_ACCEPTABLE);
	}
	@ExceptionHandler(value = {RuntimeException.class})
	protected ResponseEntity<BasicResponse> handleRuntimeException(UsernameNotFoundException ex){
		 BasicResponse response = new BasicResponse();
        response.setMsg(ex.getMessage());
        response.setSuccess(false);
   	return new ResponseEntity<BasicResponse>(response, HttpStatus.NOT_ACCEPTABLE);
	}
	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<BasicResponse> tokenExpiredException(TokenExpiredException ex) {
	 	 BasicResponse response = new BasicResponse();
		response.setMsg(ex.getMessage());
	        response.setSuccess(false);
	   	return new ResponseEntity<BasicResponse>(response, HttpStatus.BAD_REQUEST);
	}
}
