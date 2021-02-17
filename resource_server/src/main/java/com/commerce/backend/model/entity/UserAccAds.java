package com.commerce.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.commerce.backend.constants.AdsType;

@DiscriminatorValue(AdsType.Values.ACCESORIESS)
@Entity
public class UserAccAds  extends UserAds{
  @Column(name = "used")
  public Boolean used;
  @ManyToOne
  @JoinColumn(name ="category_type")
  ItemCategory itemCategory;
  
  @ManyToOne
  @JoinColumn(name ="category_id")
  ItemCategory itemCategoryId;
}
