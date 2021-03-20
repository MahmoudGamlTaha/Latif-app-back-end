package com.commerce.backend.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("1")
@Entity
public class PetCategory extends ItemObjectCategory{

}
