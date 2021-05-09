package com.commerce.backend.model.request.blog;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Positive;


@Data
public class UpdateBlogRequest {

    @NotNull
    @Positive
    Long id;
    
    Long category;
    
    String title;

    String description;
    
    Boolean active;  
}
