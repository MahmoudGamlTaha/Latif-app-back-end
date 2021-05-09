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
public class CategoryUpdateRequest {
	@NotBlank
	@Positive
	Long id;
     
	 @Size(min = 3, max = 200)
     String name ;
     
     @NotBlank
     @Size(min = 3, max = 200)
     String nameAr ;
     
     @Nullable
     String icon;
     
     @Nullable
     String icon_select;
     
     @Positive
     @NotNull
     Integer type;
     
	  @Nullable
	 Long catParent;
	 
	  @Nullable
	 Boolean active;
	 @Nullable
	 Boolean isExternalLink;
}
