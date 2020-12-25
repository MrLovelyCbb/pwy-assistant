package com.tchappy.photo.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

public class HistoryBusinessEntity extends BasesEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String uName;
	public String uPhone;
	public String service;
	public String time;
	
	public int totalNum;
	public int pageSize;
	public List<HistoryBusinessEntity> list;

	public HistoryBusinessEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public HistoryBusinessEntity(JSONObject json){
		super(json);
		if(!TextUtils.isEmpty(json.toString()))
		{
			if (isStatus())
			{
				JSONObject dataJson = json.optJSONObject("data");
				totalNum = dataJson.optInt("total");
				pageSize = dataJson.optInt("perPageSize");
				JSONArray arr = dataJson.optJSONArray("data");
				if (arr.length() > 0){
					list = new ArrayList<HistoryBusinessEntity>();
					for (int i = 0; i < arr.length(); i++) {
						JSONObject data = (JSONObject) arr.opt(i);
						HistoryBusinessEntity todays = new HistoryBusinessEntity();
						todays.uName = data.optString("name");
						todays.uPhone = data.optString("phone");
						todays.service = data.optString("service");
						todays.time = data.optString("time");
						list.add(todays);
					}
				}
			}
		}
	}

	
	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getuPhone() {
		return uPhone;
	}

	public void setuPhone(String uPhone) {
		this.uPhone = uPhone;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<HistoryBusinessEntity> getList() {
		return list;
	}

	public void setList(List<HistoryBusinessEntity> list) {
		this.list = list;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
