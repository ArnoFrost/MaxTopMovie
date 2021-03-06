package com.arno.myapplication.util;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
//Gson工具类
public class GsonBooleanDeserializer implements JsonDeserializer<Object> {

	@Override
	public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {

		try {
			String value = json.getAsJsonPrimitive().getAsString();
			
			if("1".equalsIgnoreCase(value.toLowerCase()) || "true".equalsIgnoreCase(value.toLowerCase())){
				return true;
			}
			
			if("0".equalsIgnoreCase(value.toLowerCase()) || "false".equalsIgnoreCase(value.toLowerCase())){
				return false;
			}
			
		} catch (Exception e) {

		}
		
		return false;
	}
	
}
