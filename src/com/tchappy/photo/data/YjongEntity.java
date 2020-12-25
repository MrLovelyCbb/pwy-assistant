package com.tchappy.photo.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

public class YjongEntity extends BasesEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int Id;
	public int Sid;
	public String Uname;
	public String Account;
	public String Is_default;
	public List<YjongEntity> list;
	
	public YjongEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public YjongEntity(JSONObject json){
		super(json);
		if(!TextUtils.isEmpty(json.toString()))
		{
			if (isStatus())
			{
				JSONArray arr = json.optJSONArray("data");
				if (arr.length() > 0){
					list = new ArrayList<YjongEntity>();
					for (int i = 0; i < arr.length(); i++) {
						JSONObject data = (JSONObject) arr.opt(i);
						YjongEntity yjing = new YjongEntity();
						yjing.Id = data.optInt("id");
						yjing.Sid = data.optInt("saler_id");
						yjing.Uname = data.optString("true_name");
						yjing.Account = data.optString("account");
						yjing.Is_default = data.optString("is_default");
						list.add(yjing);
					}
				}
			}
		}
	}

	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getSid() {
		return Sid;
	}

	public void setSid(int sid) {
		Sid = sid;
	}

	public String getUname() {
		return Uname;
	}

	public void setUname(String uname) {
		Uname = uname;
	}

	public String getAccount() {
		return Account;
	}

	public void setAccount(String account) {
		Account = account;
	}

	public String getIs_default() {
		return Is_default;
	}

	public void setIs_default(String is_default) {
		Is_default = is_default;
	}

	public List<YjongEntity> getList() {
		return list;
	}

	public void setList(List<YjongEntity> list) {
		this.list = list;
	}

}
