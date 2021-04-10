package com.commerce.backend.model.request.category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Required;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CategoryRequest {
	 
     @NotBlank
     @Size(min = 3, max = 200)
     String name ;
     
     @NotBlank
     @Size(min = 3, max = 200)
     String nameAr ;
     
     String icon;
     
     String icon_select;
     
     @Positive
     @NotNull
     Integer type;
     
	  @Nullable
	 Long catParent;
	  
	 boolean active;
	 
	 boolean isExternalLink;
}
