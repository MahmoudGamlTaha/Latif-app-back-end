package com.commerce.backend.model.dto;

import lombok.Data;

@Data
public class CityVO {
  private String cityAr;
  private String city;
  private Long countryId;
  private boolean active;
}
