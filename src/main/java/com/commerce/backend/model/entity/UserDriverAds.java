package com.commerce.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.commerce.backend.constants.AdsType;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(AdsType.Values.Driver)
@Setter
@Getter
public class UserDriverAds extends UserAds {
   @Column(name = "driver_method")
    Boolean driver_method;
}
