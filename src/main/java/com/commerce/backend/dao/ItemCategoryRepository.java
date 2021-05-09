package com.commerce.backend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.commerce.backend.model.entity.ItemCategory;

@Repository
public interface ItemCategoryRepository extends CrudRepository<ItemCategory, Long> {
	List<ItemCategory> findAllByOrderByName();

}
