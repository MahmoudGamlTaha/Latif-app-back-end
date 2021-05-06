package com.commerce.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.commerce.backend.constants.AdsType;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(AdsType.Values.PET_CARE)
@Setter
@Getter
public class UserMedicalAds extends UserAds { 
   @Column(name = "allow_at_home")
   Boolean allowServiceAtHome;
}
