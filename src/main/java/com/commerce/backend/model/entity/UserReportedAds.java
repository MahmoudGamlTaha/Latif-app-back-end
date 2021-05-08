package com.commerce.backend.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "user_reported_ads")
@Data
public class UserReportedAds {
    @Id
	private Long id;
}
