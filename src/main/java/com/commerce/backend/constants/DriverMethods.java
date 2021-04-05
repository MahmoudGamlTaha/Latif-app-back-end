package com.commerce.backend.constants;

import lombok.Getter;

@Getter
public enum DriverMethods {
    CAR(Values.CAR),
    MOTO_CYCLE (Values.MOTO_CYCLE);
	
	private String type;
	 DriverMethods(String type) {
		this.type = type;
	}
	 public static class Values{
		 public static final String CAR = "CAR";
		 public static final String MOTO_CYCLE ="Moto Cycle" ;
	 }
}
