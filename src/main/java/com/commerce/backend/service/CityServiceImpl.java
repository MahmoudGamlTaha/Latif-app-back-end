package com.commerce.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commerce.backend.dao.CityRepository;

@Service
public class CityServiceImpl {
	@Autowired
	CityRepository repository;
	
}
