package com.tchappy.photo.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

public class MoneyHistoryEntity extends BasesEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int month;
	public int money_all;
	public int cb_money;
	public int tc_money;
	public int prize_money;
	public List<MoneyHistoryEntity> list;

	public MoneyHistoryEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public MoneyHistoryEntity(JSONObject json){
		super(json);
		if(!TextUtils.isEmpty(json.toString()))
		{
			if (isStatus())
			{
				JSONArray arr = json.optJSONArray("data");
				if (arr.length() > 0){
					list = new ArrayList<MoneyHistoryEntity>();
					for (int i = 0; i < arr.length(); i++) {
						JSONObject data = (JSONObject) arr.opt(i);
						MoneyHistoryEntity moneys = new MoneyHistoryEntity();
						moneys.month = data.optInt("month");
						moneys.money_all = data.optInt("money_all");
						moneys.cb_money = data.optInt("cb_money");
						moneys.tc_money = data.optInt("tc_money");
						moneys.prize_money = data.optInt("prize_money");
						list.add(moneys);
					}
				}
			}
		}
	}

	
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getMoney_all() {
		return money_all;
	}

	public void setMoney_all(int money_all) {
		this.money_all = money_all;
	}

	public int getCb_money() {
		return cb_money;
	}

	public void setCb_money(int cb_money) {
		this.cb_money = cb_money;
	}

	public int getTc_money() {
		return tc_money;
	}

	public void setTc_money(int tc_money) {
		this.tc_money = tc_money;
	}

	public int getPrize_money() {
		return prize_money;
	}

	public void setPrize_money(int prize_money) {
		this.prize_money = prize_money;
	}

	public List<MoneyHistoryEntity> getList() {
		return list;
	}

	public void setList(List<MoneyHistoryEntity> list) {
		this.list = list;
	}

}
