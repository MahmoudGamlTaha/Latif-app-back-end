package com.commerce.backend.converter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KeyResponse {
    private String name;
    private String name_ar;
    private Object value;

    public KeyResponse(String name, String name_ar, Object value){
        this.name = name;
        this.name_ar = name_ar;
        this.value = value;
    }
  
}