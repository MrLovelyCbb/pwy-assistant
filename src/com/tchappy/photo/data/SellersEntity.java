package com.tchappy.photo.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

public class SellersEntity extends BasesEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int uId;
	public String uName;
	public String uPhone;
	public int uMoney;
	public List<SellersEntity> list;
	public int dataNumber;
	public int moneyCount;
	public int pageSize;
	
	public SellersEntity(){
		
	}
	
	public SellersEntity(JSONObject json) {
		super(json);
		if(!TextUtils.isEmpty(json.toString()))
		{
			if (isStatus())
			{
				JSONObject dataJson = json.optJSONObject("data");
				this.moneyCount = dataJson.optInt("money_all");
				this.dataNumber = dataJson.optInt("num_all");
				this.pageSize = dataJson.optInt("perPageSize");
				JSONArray arr = dataJson.optJSONArray("data");
				if (arr.length() > 0){
					list = new ArrayList<SellersEntity>();
					for (int i = 0; i < arr.length(); i++) {
						JSONObject data = (JSONObject) arr.opt(i);
						SellersEntity sellers = new SellersEntity();
						sellers.uId = data.optInt("id");
						sellers.uName = data.optString("true_name");
						sellers.uPhone = data.optString("phone");
						sellers.uMoney = data.optInt("money");
						list.add(sellers);
					}
				}
			}
		}
	}

	public int getuId() {
		return uId;
	}

	public void setuId(int uId) {
		this.uId = uId;
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

	public int getuMoney() {
		return uMoney;
	}

	public void setuMoney(int uMoney) {
		this.uMoney = uMoney;
	}

	public List<SellersEntity> getList() {
		return list;
	}

	public void setList(List<SellersEntity> list) {
		this.list = list;
	}

	public int getDataNumber() {
		return dataNumber;
	}

	public void setDataNumber(int dataNumber) {
		this.dataNumber = dataNumber;
	}

	public int getMoneyCount() {
		return moneyCount;
	}

	public void setMoneyCount(int moneyCount) {
		this.moneyCount = moneyCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
