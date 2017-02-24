package com.arno.myapplication.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtil {

	public static String toJsonString(Object value) {
		Gson gson = getGson();
		return gson.toJson(value);
	}

	public static <T> T getObject(String jsonString, Class<T> cls) {
		T t = null;
		try {
			Gson gson = getGson();
			t = gson.fromJson(jsonString, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	public static <T> List<T> getObjects(String jsonString, Class<T> classOfT) {
		List<T> listOfT = new ArrayList<T>();
		try {
			Gson gson = getGson();
			JsonParser parser = new JsonParser();
		    JsonArray Jarray = parser.parse(jsonString).getAsJsonArray();
		    for (JsonElement jsonElement : Jarray) {
		    	T t = gson.fromJson(jsonElement, classOfT);
		    	listOfT.add(t);
			}
//			listOfT = gson.fromJson(jsonString, new TypeToken<List<T>>() {
//			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfT;
		// Type type = new TypeToken<ArrayList<JsonObject>>() {
		// }.getType();
		// ArrayList<JsonObject> jsonObjs = new Gson().fromJson(jsonString, type);
		//
		// ArrayList<T> listOfT = new ArrayList<T>();
		// for (JsonObject jsonObj : jsonObjs) {
		// listOfT.add(new Gson().fromJson(jsonObj, classOfT));
		// }
		// return listOfT;
	}

	/**
	 *
	 * 描述：将json转化为列表.
	 * @param json
	 * @param typeToken new TypeToken<ArrayList<?>>() {};
	 * @return
	 */
	public static List<?> fromJson(String json,TypeToken typeToken) {
		List<?> list = null;
		try {
			GsonBuilder gsonb = new GsonBuilder();
			Gson gson = gsonb.create();
			Type type = typeToken.getType();
			list = gson.fromJson(json,type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getList(String jsonString) {
		List<String> list = new ArrayList<String>();
		try {
			Gson gson = getGson();
			list = gson.fromJson(jsonString, new TypeToken<List<String>>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	public static List<Map<String, Object>> listKeyMap(String jsonString) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			Gson gson = getGson();
			list = gson.fromJson(jsonString, new TypeToken<List<Map<String, Object>>>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	private static Gson getGson() {
		GsonBuilder builder = new GsonBuilder();
		GsonBooleanDeserializer serializer = new GsonBooleanDeserializer();
		builder.registerTypeAdapter(Boolean.class, serializer);
		builder.registerTypeAdapter(boolean.class, serializer);
		Gson gson = builder.create();
		return gson;
	}

}
