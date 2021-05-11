package com.commerce.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="cites")
@Data
public class Cites {
    @Id
	Long id;
    @Column(name = "city_ar")
    String cityAr;
    @Column(name = "city_en")
    String cityEn;
}
