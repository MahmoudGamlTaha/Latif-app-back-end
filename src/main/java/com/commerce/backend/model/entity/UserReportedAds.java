package com.commerce.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user_reported_ads")
@Data
@DiscriminatorColumn(name ="type", discriminatorType = DiscriminatorType.STRING)
public class UserReportedAds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(name = "type", insertable = false, updatable = false, nullable = false)
	private String type;   
}
