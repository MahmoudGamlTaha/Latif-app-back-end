package com.commerce.backend.model.request.blog;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class BlogCategoryRequest {
    @NotBlank
    @Size(min = 3, max = 250)
    //@Pattern(regexp = "[0-9a-zA-Z #,_]+")
    String name;
    
    @Size(min = 3, max = 250)
    //@Pattern(regexp = "[0-9a-zA-Z #,_]+")
    String nameAr;
    
    @Null
    @Size(max = 300)
    String description;
    
    @Null
    Long parentCategory;
    
    String icon;
    
    String icon_select;
    
    Boolean external_link;
    
    boolean active;
    
}
