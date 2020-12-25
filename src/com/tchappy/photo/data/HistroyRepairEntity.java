package com.tchappy.photo.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

public class HistroyRepairEntity extends BasesEntity{

	
	public String uName;
	public String uPhone;
	public String add_time;
	public String kd_time;
	
	public int totalNum;
	public int pageSize;
	public List<HistroyRepairEntity> list;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HistroyRepairEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public HistroyRepairEntity(JSONObject json){
		super(json);
		if(!TextUtils.isEmpty(json.toString()))
		{
			if (isStatus())
			{
				JSONObject dataJson = json.optJSONObject("data");
				totalNum = dataJson.optInt("num_all");
				pageSize = dataJson.optInt("perPageSize");
				JSONArray arr = dataJson.optJSONArray("data");
				if (arr.length() > 0){
					list = new ArrayList<HistroyRepairEntity>();
					for (int i = 0; i < arr.length(); i++) {
						JSONObject data = (JSONObject) arr.opt(i);
						HistroyRepairEntity his_repair = new HistroyRepairEntity();
						his_repair.uName = data.optString("name");
						his_repair.uPhone = data.optString("phone");
						his_repair.add_time = data.optString("add_time");
						his_repair.kd_time = data.optString("kd_time");
						list.add(his_repair);
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

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public String getKd_time() {
		return kd_time;
	}

	public void setKd_time(String kd_time) {
		this.kd_time = kd_time;
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

	public List<HistroyRepairEntity> getList() {
		return list;
	}

	public void setList(List<HistroyRepairEntity> list) {
		this.list = list;
	}


}
