package com.commerce.backend.dao;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.commerce.backend.model.entity.Policy;

@Repository
public interface PolicyRepository extends CrudRepository<Policy, Long> {
  List<Policy> findAll();
}
