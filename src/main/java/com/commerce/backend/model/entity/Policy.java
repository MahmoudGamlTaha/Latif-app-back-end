package com.commerce.backend.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import com.commerce.backend.constants.SystemConstant;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue(SystemConstant.Policy)
public class Policy extends SystemSetting {
}
