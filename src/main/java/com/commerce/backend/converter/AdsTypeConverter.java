package com.commerce.backend.converter;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.commerce.backend.constants.AdsType;

@Converter(autoApply = true)
public class AdsTypeConverter implements AttributeConverter<AdsType, String> {

	@Override
	public String convertToDatabaseColumn(AdsType attribute) {
		System.out.println(attribute);
		if(attribute == null) {
			return AdsType.ALL.getType();
		}
		return attribute.getType();
	}

	@Override
	public AdsType convertToEntityAttribute(String dbData) {
		 if(dbData == null) {
			 return AdsType.ALL;
		 }
		 
		AdsType type = Stream.of(AdsType.values())
				.filter(t -> t.getType().equals( dbData))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
		System.out.println(type);
		return type;
	}

}
