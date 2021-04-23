package com.commerce.backend.helper;

import java.io.IOException;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;

public class JsonToPointDeserializer  extends JsonDeserializer<Geometry>{
	private final static GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel()); 
	@Override
	public Geometry deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		   try {
	            String text = jp.getText();
	            if(text == null || text.length() <= 0)
	                return null;

	            String[] coordinates = text.replaceFirst("POINT ?\\(", "").replaceFirst("\\)", "").split(" ");
	            double lat = Double.parseDouble(coordinates[0]);
	            double lon = Double.parseDouble(coordinates[1]);
	            String pointStr = String.format("POINT (%s %s)", lon, lat);     
	            Geometry point = wktToGeometry(pointStr);
	            return point;
	        }
	        catch(Exception e){
	            return null;
	        }
	    }
	  public Geometry wktToGeometry(String wellKnownText) throws ParseException {
		    return new WKTReader().read(wellKnownText);
		}
	}

