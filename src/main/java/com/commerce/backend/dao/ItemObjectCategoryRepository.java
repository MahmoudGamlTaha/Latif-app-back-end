package com.commerce.backend.dao;

import com.commerce.backend.model.entity.ItemObjectCategory;
import com.commerce.backend.model.entity.ProductCategory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemObjectCategoryRepository extends CrudRepository<ItemObjectCategory, Long> {
    List<ItemObjectCategory>    findAllByOrderByName();
    @Query("SELECT b FROM ItemObjectCategory b WHERE type = ?1 AND active = true")
    List<ItemObjectCategory>    findAllByType(Integer type);
}
