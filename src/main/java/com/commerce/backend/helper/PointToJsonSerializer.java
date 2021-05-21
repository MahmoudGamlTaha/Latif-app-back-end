package com.commerce.backend.helper;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commerce.backend.api.PublicApiController;
import com.commerce.backend.constants.SystemConstant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.locationtech.jts.geom.Point;

public class PointToJsonSerializer extends JsonSerializer<Point> {
	private static final Logger logger = LoggerFactory.getLogger(PointToJsonSerializer.class);
	@Override
	public void serialize(Point value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		 String jsonValue = "null";
	        try
	        {
	            if(value != null) {             
	                double lat = value.getY();
	                double lon = value.getX();
	                jsonValue = String.format("POINT (%s %s)",lat, lon);
	            }
	        }
	        catch(Exception ex) {
	        	ex.printStackTrace();
	        }
	       logger.info("JSSSO"+ jsonValue);
	        gen.writeString(jsonValue);
	    
		
	}

}
