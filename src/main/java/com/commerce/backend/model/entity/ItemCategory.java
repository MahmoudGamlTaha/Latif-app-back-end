package com.commerce.backend.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@DiscriminatorValue("2")
@Entity
@Setter
@Getter
public class ItemCategory extends ItemObjectCategory{
   
}
