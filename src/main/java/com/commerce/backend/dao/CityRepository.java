package com.commerce.backend.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.commerce.backend.model.entity.Cites;
public interface CityRepository extends JpaRepository<Cites, Long> {

}
