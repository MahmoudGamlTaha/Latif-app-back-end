package com.commerce.backend.model.entity;

import java.util.Date;

import javax.persistence.*;

import com.commerce.backend.constants.AdsType;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	@Enumerated(EnumType.ORDINAL)
	private AdsType type;        
	
	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name = "created_by")
	@JsonManagedReference
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
	
	@Column(name = "price")
	private Float price;
  
}