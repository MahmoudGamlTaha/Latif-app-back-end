package com.commerce.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;

import com.commerce.backend.constants.AdsType;

@DiscriminatorValue(AdsType.Values.ACCESORIESS)
public class UserAccAds  extends UserAds{
  @Column(name = "used")
  public Boolean used;
}
