package com.commerce.backend.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.commerce.backend.constants.AdsType;

import lombok.Getter;
import lombok.Setter;

@DiscriminatorValue("unseen")
@Entity
@Getter
@Setter
public class UnseenUserAds  extends UserReportedAds{
 
}
