package com.commerce.backend.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.commerce.backend.model.entity.Cites;
import com.commerce.backend.model.entity.Country;

@Repository
public interface CityRepository extends JpaRepository<Cites, Long> {
	
	@Query(value = "SELECT c FROM Cites c")   
    public List<Cites> findByActive(boolean active);
    
	@Query(value = "SELECT c FROM Cites c WHERE c.country = ?1 AND c.active = true")
	public List<Cites> findByCounty(Country id);
 
	@Transactional
	@Modifying
	@Query(value = "UPDATE Cites C SET C.active = ?2 WHERE C.id = ?1 ")
	Integer activateCity(Long id, boolean active);
	
	public List<Cites> findAll();
  
}
