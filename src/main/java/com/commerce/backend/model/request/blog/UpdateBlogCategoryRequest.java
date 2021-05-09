package com.commerce.backend.model.request.blog;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;
@Data
public class UpdateBlogCategoryRequest {
	    @NotNull
	    @Positive
	    Long id;
	  
	    @Size(min = 3, max = 250)  
	    String name;
	 
	    String description;
	    
	    String icon;
	    
	    String icon_select;
}
