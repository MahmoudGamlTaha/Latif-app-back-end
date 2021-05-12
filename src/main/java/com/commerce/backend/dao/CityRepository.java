package com.commerce.backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.commerce.backend.model.entity.Cites;

@Repository
public interface CityRepository extends JpaRepository<Cites, Long> {
  public List<Cites> findByActive(boolean active);
}
