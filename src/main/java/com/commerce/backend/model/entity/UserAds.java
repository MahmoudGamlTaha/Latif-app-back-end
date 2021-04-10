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
@Table(name = "user_ads", schema="public")
@DiscriminatorColumn(name ="type", discriminatorType = DiscriminatorType.STRING)
public class UserAds {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ads_sequence")
	@SequenceGenerator(name="ads_sequence", sequenceName= "ads_sequence", allocationSize = 1)
	@Column(name = "id")
    private long id;
	
	@Column(name = "code", length = 250, unique = true)
	private String code;
	
	//@Transient
	@Column(name = "type", insertable = false, updatable = false, nullable = false)
	private AdsType type;        
	
   @ManyToOne(cascade = {CascadeType.MERGE})
	 @JoinColumn(name = "created_by")
	 private User createdBy;
	
	@Column(name = "active")
	private boolean active;
	
	@Column(name = "name", length = 250)
	private String name ;
	
	@Column(name = "longitude", length = 200)
	private double longitude;
	
	@Column(name = "latitude", length = 200 )
	private double latitude;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "short_description")
	private String shortDescription;
	
	@Column(name = "price")
	private Float price;
	
	@Column(name = "external_link")
	private Boolean externalLink;  
  
}
