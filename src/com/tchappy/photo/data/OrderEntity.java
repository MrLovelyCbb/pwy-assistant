package com.tchappy.photo.data;

import org.json.JSONObject;

import android.text.TextUtils;

public class OrderEntity extends BasesEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int saler_id;
	public int uid;
	public int sid;
	public String imei;
	public String money;
	public String chargeTime;
	public String o_status;
	public int chargeSp;
	public String tradeNo;
	public String orderidrn;
	public String orderid;
	public String name;
	
	
	public OrderEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public OrderEntity(JSONObject json){
		super(json);
		if(!TextUtils.isEmpty(json.toString()))
		{
			if (isStatus())
			{
				JSONObject dataJson = json.optJSONObject("data");
				this.saler_id = dataJson.optInt("saler_id");
				this.uid = dataJson.optInt("uid");
				this.sid = dataJson.optInt("sid");
				this.imei = dataJson.optString("imei");
				this.money = dataJson.optString("money");
				this.chargeTime = dataJson.optString("charge_time");
				this.o_status = dataJson.optString("status");
				this.chargeSp = dataJson.optInt("charge_sp");
				this.tradeNo = dataJson.optString("trade_no");
				this.orderidrn = dataJson.optString("orderidrn");
				this.orderid = dataJson.optString("orderid");
				this.name = dataJson.optString("name");
			}
		}
	}

	public int getSaler_id() {
		return saler_id;
	}

	public void setSaler_id(int saler_id) {
		this.saler_id = saler_id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getChargeTime() {
		return chargeTime;
	}

	public void setChargeTime(String chargeTime) {
		this.chargeTime = chargeTime;
	}

	public String getO_status() {
		return o_status;
	}

	public void setO_status(String o_status) {
		this.o_status = o_status;
	}

	public int getChargeSp() {
		return chargeSp;
	}

	public void setChargeSp(int chargeSp) {
		this.chargeSp = chargeSp;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getOrderidrn() {
		return orderidrn;
	}

	public void setOrderidrn(String orderidrn) {
		this.orderidrn = orderidrn;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
