package com.commerce.backend.dao;

import java.util.List;

import org.hibernate.annotations.Where;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commerce.backend.model.entity.PetCategory;

@Repository
public interface PetCategoryRepository extends JpaRepository<PetCategory, Long> {
	List<PetCategory> findAllByOrderByName();
	@Where(clause="active = true")
	Page<PetCategory> findAll(Pageable page);
}
