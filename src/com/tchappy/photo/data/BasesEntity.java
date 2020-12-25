package com.tchappy.photo.data;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tchappy.photo.util.Preferences;

import android.util.Log;

public class BasesEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2245579372521484183L;
	private JSONObject data;
	private String info;
	private String status;
	
	private String jsonStr = "";
	private JSONObject jsonObject = null;
	private JSONArray jsonArray = null;
	
//	// 调用方法枚举
//	public enum FuncEnum {
//		reg,login,
//	}
//	
//	//
//	public enum StatusEnum {
//		error,ok,exception
//	}
	
	public BasesEntity(){
		
	}
	
	public BasesEntity(Object obj){
		try{
			if (obj == null)
				throw new Exception("Maybe BasesEntity obj == null");
			if (obj instanceof String){
				obj = (String)obj;
				getResultByStr((String)obj);
			}
			else if (obj instanceof JSONObject){
				obj = (JSONObject)obj;
				getResultByJsonObject((JSONObject) obj);
			}
			else if (obj instanceof JSONArray)
			{
				obj = (JSONArray)obj;
				getResultByJsonArray((JSONArray) obj);
			}
		}catch(Exception ex){
			Log.e(Preferences.EX_SPB, ex.getMessage());
		}
	}
	
	protected void getResultByStr(String jsonStr) throws JSONException
	{
		JSONObject jsonObject=new JSONObject(jsonStr);
		this.status = jsonObject.optString("status");
		this.data = jsonObject.optJSONObject("data");
		this.info = jsonObject.optString("info");
		this.jsonStr = jsonStr;
		this.jsonObject = jsonObject;
	}
	
	protected void getResultByJsonObject(JSONObject json)
	{
		this.status = json.optString("status");
		this.data = json.optJSONObject("data");
		this.info = json.optString("info");
		this.jsonObject = json;
		this.jsonStr = json.toString();
	}
	
	protected void getResultByJsonArray(JSONArray json)
	{
		this.data = json.optJSONObject(0);
		this.status = json.optString(1);
		this.info = json.optString(2);
		this.jsonArray = json;
		this.jsonStr = json.toString();
	}
	

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getJsonStr() {
		return jsonStr;
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}
	
	public int getStatusCode() {
		return Integer.parseInt(this.status);
	}
	
	public boolean isStatus() {
		if (this.status.equals("0"))
			return true;
		else if (this.getStatusCode() == 0)
			return true;
		else
			return false;
	}
}
