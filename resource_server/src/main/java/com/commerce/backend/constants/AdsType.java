package com.commerce.backend.constants;


import lombok.Getter;

@Getter
public enum AdsType {
	SERVICE(Values.SERVICE),
	PETS(Values.PETS),
	PET_CARE(Values.PET_CARE),
	ACCESORIESS(Values.ACCESORIESS),
	ALL(Values.ALL);
	
	private String type;
	 AdsType(String type) {
		this.type = type;
	}
	 public static class Values{
		 public static final String SERVICE = "SERVICE";
		 public static final String PETS ="PETS" ;
		 public static final String	DELIVERY = "DELIVERY" ;
		 public static final String	VETERINARY = "VETERINARY";
		 public static final String	PET_CARE = "PET_CARE";
		 public static final String ACCESORIESS  = "ACCESORIESS";
		 public static final String ALL = "ALL";
	 }
}
