package com.commerce.backend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.commerce.backend.model.entity.PetCategory;
import com.commerce.backend.model.entity.ServiceCategory;

@Repository
public interface ServiceCategoryRepository extends CrudRepository<ServiceCategory, Long> {
	List<ServiceCategory> findAllByOrderByName();
}
