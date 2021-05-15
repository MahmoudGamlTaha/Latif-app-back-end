package com.commerce.backend.dao;

import com.commerce.backend.model.entity.ItemObjectCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.hibernate.annotations.Where;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ItemObjectCategoryRepository extends JpaRepository<ItemObjectCategory, Long> {
   
	Page<ItemObjectCategory> findAll(Pageable pageable);
	
   @Where(clause ="active = true")
    Page<ItemObjectCategory> findByType(Integer type, Pageable page);
}
