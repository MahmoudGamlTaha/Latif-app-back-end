package com.commerce.backend.model.request.blog;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class UpdateCategoryRequest {

    @NotNull
    @Positive
    Long id;
  
    @Size(min = 3, max = 250)  
    String name;
    
    String description;
    
    String icon;
    
    String icon_select;
}
