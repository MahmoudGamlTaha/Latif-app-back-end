package com.commerce.backend.service;


import com.commerce.backend.constants.MessageType;
import com.commerce.backend.converter.CategoryTypeConverter;
import com.commerce.backend.dao.CategoryTypeRepository;
import com.commerce.backend.model.entity.CategoryType;
import com.commerce.backend.model.request.category.CategoryTypeRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.categoryType.CategoryTypeResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryTypeServiceImpl implements CategoryTypeService {
    
	@Autowired
 	private CategoryTypeRepository categoryTypeRepository;
	
	@Autowired
	private CategoryTypeConverter categoryTypeConverter;
	
    @Override
	public ResponseEntity<BasicResponse> getCategoryTypes() {
    	BasicResponse response = new BasicResponse();
    	HashMap<String, Object> hashMap = new HashMap<String, Object>();
    	 try {
    	 List<CategoryType> categoryTypes = this.categoryTypeRepository.findAll();
    	 List<CategoryTypeResponse> categoryTypeResponse =   categoryTypes
    	   .stream()
    	   .map(categoryTypeConverter)
    	   .collect(Collectors.toList());
    	   response.setMsg(MessageType.Success.getMessage());
    	   response.setSuccess(true);
    	   hashMap.put(MessageType.Data.getMessage(), categoryTypeResponse);
    	 }
    	 catch(Exception ex) {
    	    response.setMsg(ex.getMessage());
    		 hashMap.put(MessageType.Data.getMessage(), MessageType.Fail.getMessage());
    		 response.setSuccess(false);
    		 
    	 }
    	 response.setResponse(hashMap);
		return new ResponseEntity<BasicResponse>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<BasicResponse> getCategoryTypeById(Long id) {
		this.categoryTypeRepository.findById(id);
		return null;
	}

	@Override
	public List<CategoryTypeResponse> search(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<BasicResponse> saveCategoryType(CategoryTypeRequest blog) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<BasicResponse> deleteCategoryType(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<BasicResponse> updateCategoryType(CategoryTypeRequest request) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	
   
}
