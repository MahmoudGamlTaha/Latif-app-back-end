package com.commerce.backend.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.commerce.backend.constants.CategoryType;

@Entity
@DiscriminatorValue(CategoryType.Values.SERVICE)
public class ServiceCategory extends ItemObjectCategory {
     
}
