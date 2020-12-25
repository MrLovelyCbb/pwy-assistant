package com.tchappy.photo.data;

import java.io.Serializable;
import org.json.JSONObject;
import android.text.TextUtils;

public class RepairEntity extends BasesEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5506990265842157749L;
	public String uid;
	public String Uname;
	public String Uphone;
	public String imei;
	public String img1;
	public String img2;
	
	public RepairEntity(JSONObject json)
	{
		super(json);
		if(!TextUtils.isEmpty(json.toString()))
		{
			if (isStatus())
			{
				JSONObject dataJson = json.optJSONObject("data");
				this.uid = dataJson.optString("id");
				this.Uname = dataJson.optString("name");
				this.Uphone = dataJson.optString("phone");
				this.imei = dataJson.optString("imei");
				this.img1 = dataJson.optString("img1");
				this.img2 = dataJson.optString("img2");
			}
		}
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getUname() {
		return Uname;
	}

	public void setUname(String uname) {
		Uname = uname;
	}

	public String getUphone() {
		return Uphone;
	}

	public void setUphone(String uphone) {
		Uphone = uphone;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

}
