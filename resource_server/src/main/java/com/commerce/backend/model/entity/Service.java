package com.commerce.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "services")
public class Service  extends GeneralColumn{
     @Id
     @Column(name = "id")
     Long id;
     
     @Column(name = "name")
     String name;
     
     @Column(name = "icon")
     String icon;
     
     @Column(name = "active")
     Boolean active;
}
