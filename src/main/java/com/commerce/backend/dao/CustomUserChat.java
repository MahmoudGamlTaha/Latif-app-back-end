package com.commerce.backend.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.commerce.backend.service.UserService;

public class CustomUserChat {
	@PersistenceContext(type  =  PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	private static final Logger loggerS = LoggerFactory.getLogger(CustomUserAdsCriteriaHelper.class);
	
	@Autowired
	private UserService userService; 
	
}
