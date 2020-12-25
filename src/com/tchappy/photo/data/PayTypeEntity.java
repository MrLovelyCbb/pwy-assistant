package com.tchappy.photo.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

public class PayTypeEntity extends BasesEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int Id;

	public String Uname;
	public String Price;
	public String xb_Price;
	public List<PayTypeEntity> list;
	public List<PayType> typeList;
	
	public PayTypeEntity() {
		// TODO Auto-generated constructor stub
	}

	public PayTypeEntity(JSONObject json){
		super(json);
		if(!TextUtils.isEmpty(json.toString()))
		{
			if (isStatus())
			{
				JSONArray arr = json.optJSONArray("data");
				if (arr.length() > 0){
					list = new ArrayList<PayTypeEntity>();
					for (int i = 0; i < arr.length(); i++) {
						JSONObject data = (JSONObject) arr.opt(i);
						PayTypeEntity ptypes = new PayTypeEntity();
						ptypes.Id = data.optInt("id");
						ptypes.Uname = data.optString("name");
						ptypes.Price = data.optString("price");
						ptypes.xb_Price = data.optString("xb_price");
						list.add(ptypes);
						
						JSONArray arr1 = data.optJSONArray("pay_type");
						if (arr1.length() > 0){
							typeList = new ArrayList<PayTypeEntity.PayType>();
							for (int k = 0; k <arr1.length(); k++){
								JSONObject data1 = (JSONObject) arr1.opt(k);
								PayType pType = new PayType();
								pType.Uname = data1.optString("name");
								pType.Uvalue = data1.optInt("value");
								typeList.add(pType);
							}
						}
					}
				}
			}
		}
	}
	
	public class PayType{
		public String Uname;
		public int Uvalue;
		
		public PayType(){
			
		}
		
		public String getUname() {
			return Uname;
		}
		public void setUname(String uname) {
			Uname = uname;
		}
		public int getUvalue() {
			return Uvalue;
		}
		public void setUvalue(int uvalue) {
			Uvalue = uvalue;
		}
	}
	
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getUname() {
		return Uname;
	}

	public void setUname(String uname) {
		Uname = uname;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getXb_Price() {
		return xb_Price;
	}

	public void setXb_Price(String xb_Price) {
		this.xb_Price = xb_Price;
	}

	public List<PayTypeEntity> getList() {
		return list;
	}

	public void setList(List<PayTypeEntity> list) {
		this.list = list;
	}

	public List<PayType> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<PayType> typeList) {
		this.typeList = typeList;
	}

}
