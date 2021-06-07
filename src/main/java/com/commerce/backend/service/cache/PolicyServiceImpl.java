package com.commerce.backend.service.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commerce.backend.dao.PolicyRepository;
import com.commerce.backend.model.entity.Policy;

@Service
public class PolicyServiceImpl implements PolicyService {
    PolicyRepository repo;
    
	@Autowired
	public PolicyServiceImpl(PolicyRepository repo) {
		// TODO Auto-generated constructor stub
		this.repo = repo;
	}
	public Policy find(){
		return this.repo.findAll().stream().findFirst().orElse(null); 	
    }
	@Override
	public Policy updatePolicy() {
		// TODO Auto-generated method stub
		return null;
	}
}
