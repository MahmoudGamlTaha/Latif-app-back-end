package com.commerce.backend.api;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.backend.constants.MessageType;
import com.commerce.backend.converter.blog.UrlOptionVOConverter;
import com.commerce.backend.dao.CityRepository;
import com.commerce.backend.dao.CountryRepository;
import com.commerce.backend.helper.resHelper;
import com.commerce.backend.model.dto.CityVO;
import com.commerce.backend.model.dto.CountryVO;
import com.commerce.backend.model.dto.UrlOptionVO;
import com.commerce.backend.model.entity.Cites;
import com.commerce.backend.model.entity.Country;
import com.commerce.backend.model.response.BasicResponse;

@RestController
public class CityController extends PublicApiController {
  
	@Autowired
	private CityRepository cityReposioty;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private UrlOptionVOConverter urlOptionVoConverter;
	
	@GetMapping(value = "/cites")
	public BasicResponse cites() {
	    BasicResponse response = new BasicResponse();
	    response.setMsg(MessageType.Success.getMessage());
	   
	    HashMap<String, Object> responseMap = new HashMap<String, Object>();
	    List<Object> urlOptions = new LinkedList<Object>();
	    this.cityReposioty.findByActive(true).forEach(city -> {
	        urlOptions.add(this.urlOptionVoConverter.apply(city));    	
	    });
	    
	    responseMap.put(MessageType.Data.getMessage(), urlOptions);
	    response.setResponse(responseMap);
	   return response;
   }
	@PostMapping(value = "city/create")
	public BasicResponse create(CityVO city) {
		BasicResponse response = new BasicResponse();
		HashMap<String, Object>  responseMap = new HashMap<String, Object>();
		Cites cities = new Cites();
		Country country = this.countryRepository.findById(city.getCountryId()).orElse(null);
		
		cities.setActive(city.isActive());
		cities.setCountry(country);
		cities.setCityAr(city.getCityAr());
		cities.setCityEn(city.getCity());
		response = resHelper.res(cities, true, MessageType.Success.getMessage(), null);
		return response;
	}
	
	@PostMapping(value = "country/create")
	@ResponseBody
	public BasicResponse createCountry(@RequestBody CountryVO countryVO) {
		BasicResponse response = new BasicResponse();
	    Country country = new Country();
	    country.setActive(countryVO.isActive());
	    country.setNameAr(countryVO.getCountry());
	    country.setNameEn(countryVO.getCountryAr());
	    country = this.countryRepository.save(country);
		response.setMsg(MessageType.Data.getMessage());
    	resHelper.res(country, true, MessageType.Success.getMessage(), null);
		return response;
	}
	@PostMapping(value = "country/disable")
	public BasicResponse disableCountry(CountryVO city) {
		BasicResponse response = new BasicResponse();
		HashMap<String, Object>  responseMap = new HashMap<String, Object>();
		
		response.setMsg(MessageType.Data.getMessage());
		response.setSuccess(true);
		response.setResponse(responseMap);
		return response;
	}
	
	@PostMapping(value = "country/delete")
	public BasicResponse deleteCountry(@RequestParam Long id) {
		BasicResponse response = new BasicResponse();
		HashMap<String, Object>  responseMap = new HashMap<String, Object>();
		this.countryRepository.deleteById(id);
		response.setMsg(MessageType.Data.getMessage());
		response.setSuccess(true);
		response.setResponse(responseMap);
		return response;
	}
	
	@GetMapping(value = "/countries")
	public BasicResponse CountryList(CountryVO city) {
		BasicResponse response = new BasicResponse();
	    List<Country> countries = this.countryRepository.findCountry();
	    response = resHelper.res(countries, true, MessageType.Success.getMessage(), null);
		return response;
	}
}

