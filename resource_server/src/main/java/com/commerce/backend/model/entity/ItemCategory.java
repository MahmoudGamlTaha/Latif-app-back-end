package com.commerce.backend.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("2")
@Entity
public class ItemCategory extends ItemObjectCategory{

}
