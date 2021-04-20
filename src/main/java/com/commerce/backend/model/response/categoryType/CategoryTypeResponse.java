package com.commerce.backend.model.response.categoryType;

import java.util.Date;

import lombok.Data;

@Data
public class CategoryTypeResponse {
 private Long id;
 private Boolean active;
 private String code;
 private String name;
 private String nameAr;
 private Date createdDate;
}
