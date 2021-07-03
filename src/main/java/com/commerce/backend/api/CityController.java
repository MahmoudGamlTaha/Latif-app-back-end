package com.commerce.backend.api;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

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
import com.commerce.backend.service.UserService;

@RestController
public class CityController extends PublicApiController {
  
	@Autowired
	private CityRepository cityReposioty;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private UrlOptionVOConverter urlOptionVoConverter;
	
	@Autowired
	private UserService userService;
	// for mobile create form
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
	@GetMapping(value = "/cites-list")
	public BasicResponse activeCites() {
	    BasicResponse response = new BasicResponse();
	    response.setMsg(MessageType.Success.getMessage());
	    
	    HashMap<String, Object> responseMap = new HashMap<String, Object>();
	    
	    List<Cites> allCities = this.cityReposioty.findByActive(true);
	    
	    responseMap.put(MessageType.Data.getMessage(), allCities);
	    response.setResponse(responseMap);
	   return response;
   }
	
	@GetMapping(value = "/cites-all")
	public BasicResponse allCites() {
	    BasicResponse response = new BasicResponse();
	    response.setMsg(MessageType.Success.getMessage());
	    
	    HashMap<String, Object> responseMap = new HashMap<String, Object>();
	    
	    List<Cites> allCities = this.cityReposioty.findAll();
	    
	    responseMap.put(MessageType.Data.getMessage(), allCities);
	    response.setResponse(responseMap);
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
	    country.setLanguage(countryVO.getLanguage());
	    country = this.countryRepository.save(country);
		response.setMsg(MessageType.Data.getMessage());
		response = resHelper.res(country, true, MessageType.Success.getMessage(), null);
		return response;
	}
	
	@PostMapping(value = "cites/create")
	@ResponseBody
	public BasicResponse createCity(@RequestBody CityVO cityVO) {
		BasicResponse response = new BasicResponse();
		Country country = this.countryRepository.findById(cityVO.getCountry()).orElse(null);
		if(country == null) {
			throw new EntityNotFoundException("country not found");
		}
		Cites city = new Cites();
	    city.setActive(cityVO.isActive());
	    city.setCityEn(cityVO.getCity());
	    city.setCityAr(cityVO.getCityAr());
	    city.setCountry(country);
	    city = this.cityReposioty.save(city);
		response.setMsg(MessageType.Data.getMessage());
		response = resHelper.res(city, true, MessageType.Success.getMessage(), null);
		return response;
	}
	
	@PostMapping(value = "country/disable")
	@ResponseBody
	public BasicResponse disableCountry(@RequestParam Long country, @RequestParam boolean active){
		BasicResponse response = new BasicResponse();
		HashMap<String, Object>  responseMap = new HashMap<String, Object>();
		Integer retValue = this.countryRepository.activateCountry(country, active);
		response.setMsg(MessageType.Data.getMessage());
		response.setSuccess(true);
		response.setResponse(responseMap);
		return response;
	}
	
	@PostMapping(value = "city/disable")
	@ResponseBody
	public BasicResponse disableCity(@RequestParam Long city, @RequestParam boolean active){
		BasicResponse response = new BasicResponse();
		HashMap<String, Object>  responseMap = new HashMap<String, Object>();
		Integer retValue = this.cityReposioty.activateCity(city, active);
		response.setMsg(MessageType.Data.getMessage());
		response.setSuccess(true);
		response.setResponse(responseMap);
		return response;
	}
	
	@GetMapping(value = "city/find-by-country-id")
	public BasicResponse findCityInCountry(@RequestParam Long country) {
		BasicResponse response = new BasicResponse();
		Country countryObj = new Country();
		countryObj.setId(country);
		List<Cites> cites = this.cityReposioty.findByCounty(countryObj);
		response = resHelper.res(cites, true, MessageType.Success.getMessage(), null);
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
	    List<Country> countries = this.countryRepository.findActiveCountries();
	    response = resHelper.res(countries, true, MessageType.Success.getMessage(), null);
		return response;
	}
}

