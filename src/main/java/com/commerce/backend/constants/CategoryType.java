package com.commerce.backend.constants;

import lombok.Getter;

@Getter
public enum CategoryType {
  PETS(1),
  ACCESSORIES(2),
  SERVICE(3),
  PET_CARE(4),
  FOOD(5),
  DELIVERY(6),
  VETERINARY(7);
	int type;
	String value;
   CategoryType(int type) {
	  this.type = type;
  }
   
    CategoryType(String value){
	   this.value = value;
   }
    public static class Values{
		 public static final String SERVICE = "3";
		 public static final String PETS ="1" ;
		 public static final String	DELIVERY = "6" ;
		 public static final String	VETERINARY = "7";
		 public static final String	PET_CARE = "4";
		 public static final String ACCESORIESS  = "2";
		 public static final String ALL = "ALL";
	 }
   
}
