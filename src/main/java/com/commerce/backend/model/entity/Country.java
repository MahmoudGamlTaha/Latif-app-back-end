package com.commerce.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "countries")
@Data
public class Country {
	
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name ="name_ar")
  private String nameAr;
  
  @Column(name = "name_en")
  private String nameEn;
  
  @Column(name = "active")
  private Boolean active;
  
  @Column(name = "language")
  private String language;
}
