package com.commerce.backend.model.dto;

import lombok.Data;

@Data
public class CityVO {
  private String cityAr;
  private String city;
  private Long country;
  private boolean active;
}
