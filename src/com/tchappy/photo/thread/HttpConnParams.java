package com.tchappy.photo.thread;

import java.util.HashMap;



public class HttpConnParams {

	private HashMap<String,Object> params =null;
	
	public HttpConnParams(){
		params = new HashMap<String, Object>();
	}
	
	public HttpConnParams(String key,Object value){
		this();
		put(key,value);
	}
	public void put(String key,Object value){
		params.put(key, value);
	}
	
	public Object get(String key){
		return params.get(key);
	}
	
	
	public boolean getBoolean(String key) {
		Object object = get(key);
		if (object.equals(Boolean.FALSE)
				|| (object instanceof String && ((String) object)
						.equalsIgnoreCase("false"))) {
			return false;
		} else if (object.equals(Boolean.TRUE)
				|| (object instanceof String && ((String) object)
						.equalsIgnoreCase("true"))) {
			return true;
		}
		return false;
	}
	
	public double getDouble(String key){
		Object object = get(key);
		try {
			return object instanceof Number ? ((Number) object).doubleValue()
					: Double.parseDouble((String) object);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public int getInt(String key){
		Object object = get(key);
		try {
			return object instanceof Number ? ((Number) object).intValue()
					: Integer.parseInt((String) object);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public String getString(String key){
		Object object = get(key);
		return object == null ? null : object.toString();
	}
	
	public boolean has(String key) {
		return this.params.containsKey(key);
	}
}
