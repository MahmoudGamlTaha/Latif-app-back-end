package com.commerce.backend.converter.blog;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.commerce.backend.converter.category.ItemObjectCategoryResponseConverter;
import com.commerce.backend.model.dto.UrlOptionVO;
import com.commerce.backend.model.entity.Cites;
import com.commerce.backend.model.entity.Country;
import com.commerce.backend.model.entity.ItemObjectCategory;
import com.commerce.backend.model.response.category.ItemObjectCategoryResponse;

@Component
public class UrlOptionVOConverter  implements Function<Cites, ItemObjectCategoryResponse>{

	@Override
	public ItemObjectCategoryResponse apply(Cites city) {
		ItemObjectCategoryResponse urlOp = new ItemObjectCategoryResponse();
	       if(city == null) {
	    	   return urlOp;
	       }
	  // shoud me chnaged
	     ItemObjectCategoryResponseConverter convert = new ItemObjectCategoryResponseConverter();
	     ItemObjectCategory object = new ItemObjectCategory();
	     object.setName(city.getCityEn());
	     object.setNameAr(city.getCityAr());
	     object.setId(city.getId());
	     object.setIsExternalLink(true);
	     return convert.apply(object);
	     //  return urlOp;
	}
	
	
	public UrlOptionVO apply2(Country country) {
		UrlOptionVO object = new UrlOptionVO();
	       if(country == null) {
	    	   return object;
	       }
	     object.setName(country.getNameEn());
	     object.setNameAr(country.getNameAr());
	     object.setId(country.getId());
	
	     return object;
	     //  return urlOp;
	}
}