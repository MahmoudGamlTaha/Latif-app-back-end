package com.commerce.backend.model.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

import com.commerce.backend.constants.AdsType;
import com.commerce.backend.helper.JsonToPointDeserializer;
import com.commerce.backend.helper.PointToJsonSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@SqlResultSetMapping( //
	    name = "user_ads", //
	    classes = @ConstructorResult(targetClass = UserAds.class, //
	        columns = { //
	            @ColumnResult(name = "total_item", type = long.class), //
	            @ColumnResult(name = "total_page", type = long.class) //
	        }))
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
	//@Column(name = "id")
    private long id;
	
	@Column(name = "code", length = 250, unique = true, nullable = true)
	private String code;
	
	@Column(name = "city", length = 100)
	private String city;
	
	//@Transient
	@Column(name = "type", insertable = false, updatable = false, nullable = false)
	private AdsType type;        
	
   @ManyToOne(cascade = {CascadeType.ALL})
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
	
	@Setter(onMethod_ = {@JsonSerialize(using = PointToJsonSerializer.class)})
	@Getter(onMethod_ = {@JsonDeserialize(contentUsing = JsonToPointDeserializer.class)} )
	@Column(columnDefinition = "Geometry", name = "geom", nullable = true)
	private Geometry coordinates;
	
	
	@OneToMany(mappedBy="userAdsImage", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	//@OneToMany(cascade = CascadeType.ALL)
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<UserAdsImage> userAdsImage = new HashSet<UserAdsImage>();
	@Transient
	@Setter(AccessLevel.PRIVATE)
	@Column(name = "total_page", insertable = false, updatable = false)
	 private long totalPage;
	@Transient
	@Setter(AccessLevel.PRIVATE)
	@Column(name = "total_item", insertable = false, updatable = false)
	private long totalItem;
}
