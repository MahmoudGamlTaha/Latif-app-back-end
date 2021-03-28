package com.commerce.backend.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.commerce.backend.constants.CategoryType;

@DiscriminatorValue(CategoryType.Values.PETS)
@Entity
public class PetCategory extends ItemObjectCategory{

}
