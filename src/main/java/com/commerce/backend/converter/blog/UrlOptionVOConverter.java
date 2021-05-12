package com.commerce.backend.converter.blog;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.commerce.backend.model.dto.UrlOptionVO;
import com.commerce.backend.model.entity.Cites;

@Component
public class UrlOptionVOConverter  implements Function<Cites, UrlOptionVO>{

	@Override
	public UrlOptionVO apply(Cites city) {
	       UrlOptionVO urlOp = new UrlOptionVO();
	       if(city == null) {
	    	   return urlOp;
	       }
	       urlOp.setId(city.getId());
	       urlOp.setName(city.getCityEn());
	       urlOp.setNameAr(city.getCityAr());
	       return urlOp;
	}
	
}