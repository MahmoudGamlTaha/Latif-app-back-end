package com.commerce.backend.constants;

import lombok.Getter;

@Getter
public enum CategoryType {
  PET(1),
  ACCESSORIES(2);
	int type;
	String value;
   CategoryType(int type) {
	  this.type = type;
  }
   
    CategoryType(String value){
	   this.value = value;
   }
    
   
}
