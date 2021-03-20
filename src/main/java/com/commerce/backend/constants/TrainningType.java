package com.commerce.backend.constants;

import lombok.Getter;

@Getter
public enum TrainningType {
    LITTER_BOX(Values.LITTER_BOX),
    GUARDING (Values.GUARDING);
	
	private String type;
	 TrainningType(String type) {
		this.type = type;
	}
	 public static class Values{
		 public static final String LITTER_BOX = "Litter Box";
		 public static final String GUARDING ="Guarding" ;
	 }
}
