package com.tchappy.photo.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tchappy.photo.util.INFO.Notice_Info;
import com.tchappy.photo.util.INFO.Notice_Server;
import com.tchappy.photo.util.INFO.Query_Info;
import com.tchappy.photo.util.INFO.Server_Info;

import android.text.TextUtils;


/**
 * @author 张浩翔
 * 解析查询业务JSON
 *
 */
public class JSONParser {
	public static Server_Info ParseJeson_Query(String jsonString) {
		Server_Info server_info = new Server_Info();
		if(!TextUtils.isEmpty(jsonString)) {
			JSONObject mainJson = null;
			try {
				mainJson = new JSONObject(jsonString);
				server_info.setInfo(mainJson.optString("info"));
				server_info.setStatus(mainJson.optString("status"));
				
				JSONObject dataObj = mainJson.getJSONObject("data");
				server_info.setTotal(dataObj.optInt("total"));
				server_info.setPerPageSize(dataObj.optInt("perPageSize"));
				JSONArray ja_queryInfo = dataObj.getJSONArray("data");
				ArrayList<Query_Info> al_queryInfos = new ArrayList<Query_Info>();
				for(int i = 0; i < ja_queryInfo.length(); i++) {
					Query_Info qinfo = new Query_Info();
					JSONObject jobj = ja_queryInfo.getJSONObject(i);
					qinfo.setUname(jobj.optString("uname"));
					qinfo.setUphone(jobj.optString("uphone"));
					qinfo.setAdd_time(jobj.optString("add_time"));
					qinfo.setYw_type(jobj.optString("yw_type"));
					al_queryInfos.add(qinfo);
				}
				server_info.setQuery_info(al_queryInfos);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return server_info;
	}
	
	public static Notice_Server ParseJeson_Notice(String jsonString) {
		Notice_Server notice_info = new Notice_Server();
		if(!TextUtils.isEmpty(jsonString)) {
			JSONObject mainJson = null;
			try {
				mainJson = new JSONObject(jsonString);
				notice_info.setInfo(mainJson.optString("info"));
				notice_info.setStatus(mainJson.optString("status"));
				JSONArray ja_noticeInfo = mainJson.getJSONArray("data");
				ArrayList<Notice_Info> al_notice_info = new ArrayList<Notice_Info>();
				for(int i = 0; i<ja_noticeInfo.length(); i++) {
					Notice_Info nInfo = new Notice_Info();
					JSONObject jobj = ja_noticeInfo.getJSONObject(i);
					nInfo.setTitle(jobj.optString("title"));
					nInfo.setAdd_time(jobj.optString("add_time"));
					al_notice_info.add(nInfo);
				}
				notice_info.setNotice_info(al_notice_info);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return notice_info;
		
	}
}
