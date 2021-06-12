package com.commerce.backend.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "system_setting")
@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name ="type", discriminatorType = DiscriminatorType.STRING)
public class SystemSetting {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sys_sequence")
	@SequenceGenerator(name="sys_sequence", sequenceName= "sys_sequence", allocationSize = 1)
     private Long id; 
	 
	@Column(name = "type", insertable = false, updatable = false)
	private String type; 
	
	@Column(name = "value")
	private String description;
	
	@Column(name = "value_ar")
	private String descriptionAr;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_at")
	private Date updatedAt;
}
