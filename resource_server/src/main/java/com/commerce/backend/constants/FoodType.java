package com.commerce.backend.constants;

import lombok.Getter;

@Getter
public enum FoodType {
    DRY(Values.DRY),
    WET (Values.WET),
    HOME_COOKED(Values.HOME_COOKED);
	
	private String type;
	 FoodType(String type) {
		this.type = type;
	}
	 public static class Values{
		 public static final String DRY = "Dry";
		 public static final String HOME_COOKED ="Home Cooked" ;
		 public static final String WET = "Wet";
	 }
}
