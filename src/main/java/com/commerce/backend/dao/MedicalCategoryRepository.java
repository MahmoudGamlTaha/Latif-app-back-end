package com.commerce.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commerce.backend.model.entity.ItemObjectCategory;
import com.commerce.backend.model.entity.MedicalCategory;

public interface MedicalCategoryRepository extends JpaRepository<MedicalCategory, Long>{

}
