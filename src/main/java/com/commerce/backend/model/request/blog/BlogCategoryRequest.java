package com.commerce.backend.model.request.blog;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class BlogCategoryRequest {
    @NotBlank
    @Size(min = 3, max = 250)
    //@Pattern(regexp = "[0-9a-zA-Z #,_]+")
    String name;

    @NotBlank
    @Size(min = 40, max = 300)
    String description;
    
    @Positive
    Long category;
    
    String icon;
    
    Boolean external_link;
}
