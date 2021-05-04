package com.commerce.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.commerce.backend.constants.AdsType;

import lombok.Getter;
import lombok.Setter;

@DiscriminatorValue(AdsType.Values.ACCESSORIES)
@Entity
@Getter
@Setter
public class UserAccAds  extends UserAds{
  @Column(name = "used")
  private Boolean used;
  //@ManyToOne
  //@JoinColumn(name ="category_type")
  //ItemCategory itemCategoryType;

  @Column(name = "allow_at_home")
  Boolean allowServiceAtHome;

  @ManyToOne
  @JoinColumn(name ="category_id")
   private ItemCategory itemCategoryId;
  
  @Column(name = "stock")
  private double stock;
}
