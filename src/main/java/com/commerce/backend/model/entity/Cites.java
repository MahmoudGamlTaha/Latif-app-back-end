package com.commerce.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="cites")
@Data
public class Cites {
    
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
    
    @Column(name = "city_ar")
    String cityAr;
    
    @Column(name = "city_en")
    String cityEn;
    
    @Column(name = "active")
    boolean active;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    Country country;
    
}
