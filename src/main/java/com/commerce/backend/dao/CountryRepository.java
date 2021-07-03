package com.commerce.backend.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.commerce.backend.model.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
	
	@Query(value= "SELECT c FROM Country c WHERE c.active = true")
	List<Country> findActiveCountries();
	
	@Query(value = "SELECT c from Country c")
	List<Country> findAllCountry();
	
	@Query(value = "DELETE FROM Country c where c.id = ?1")
    Integer deleteCountry(Long id);
	@Transactional
	@Modifying
	@Query(value = "UPDATE Country C SET C.active = ?2 WHERE C.id = ?1 ")
	Integer activateCountry(Long id, boolean active);
	
}
