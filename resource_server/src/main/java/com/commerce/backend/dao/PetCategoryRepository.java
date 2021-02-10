package com.commerce.backend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.commerce.backend.model.entity.PetCategory;

@Repository
public interface PetCategoryRepository extends CrudRepository<PetCategory, Long> {
	List<PetCategory> findAllByOrderByName();
}
