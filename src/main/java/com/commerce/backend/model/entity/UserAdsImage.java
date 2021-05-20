package com.commerce.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_ads_image")
@NoArgsConstructor
public class UserAdsImage {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ads_img")
   @SequenceGenerator(name="seq_ads_img", sequenceName= "seq_ads_img", allocationSize = 1)
 //  @Column(name = "id")
	private long id;

   @Column(name = "image")
   private String image;

   @JsonBackReference
   @ManyToOne(optional = true, fetch = FetchType.LAZY)
   @JoinColumn(name = "user_ads_id", nullable = false)
   @NotFound(action = NotFoundAction.IGNORE)
   private UserAds userAdsImage;
   
   @Column(name = "external_link")
   private Boolean isExternalLink;
   
}
