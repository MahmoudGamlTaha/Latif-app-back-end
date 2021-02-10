package com.commerce.backend.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.commerce.backend.constants.AdsType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Table(name = "user_ads")
@DiscriminatorColumn(name ="type", discriminatorType = DiscriminatorType.STRING)
public class UserAds {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ads_sequence")
	@SequenceGenerator(name="ads_sequence", sequenceName= "ads_sequence", allocationSize = 1)
	@Column(name = "id")
    private long id;
	
	@Column(name = "code", length = 250, unique = true)
	private String code;
	
	@Column(name = "type", insertable = false, updatable = false, nullable = false)
	@Enumerated(EnumType.STRING)
	private AdsType type;        
	
	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;
	
	@Column(name = "active")
	private boolean active;
	
	@Column(name = "name", length = 250)
	private String name ;
	
	@Column(name = "longitude", length = 200)
	private String longitude;
	
	@Column(name = "latitude", length = 200 )
	private String latitude;
	
	@Column(name = "created_at")
	private Date created_at;
	
	@Column(name = "updated_at")
	private Date updated_at;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "short_description")
	private String short_description;
  
}
