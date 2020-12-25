package com.tchappy.photo.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class INFO {
	
	public static class Query_Info implements Serializable {
		private String uname;
		private String uphone;
		private String add_time;
		private String yw_type;
		public String getUname() {
			return uname;
		}
		public void setUname(String uname) {
			this.uname = uname;
		}
		public String getUphone() {
			return uphone;
		}
		public void setUphone(String uphone) {
			this.uphone = uphone;
		}
		public String getAdd_time() {
			return add_time;
		}
		public void setAdd_time(String add_time) {
			this.add_time = add_time;
		}
		public String getYw_type() {
			return yw_type;
		}
		public void setYw_type(String yw_type) {
			this.yw_type = yw_type;
		}
		
	}
	
	
	public static class Server_Info implements Serializable {
		private String info;
		private String status;
		private int total;
		private int perPageSize;
		ArrayList<Query_Info> query_info;
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
		
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public int getPerPageSize() {
			return perPageSize;
		}
		public void setPerPageSize(int perPageSize) {
			this.perPageSize = perPageSize;
		}
		public ArrayList<Query_Info> getQuery_info() {
			return query_info;
		}
		public void setQuery_info(ArrayList<Query_Info> query_info) {
			this.query_info = query_info;
		}
		
	}
	
	public static class Notice_Info implements Serializable {
		private String title;
		private String add_time;
		
		/*获取广告内容*/
		public String getTitle() {
			return title;
		}
		/*设置广告内容*/
		public void setTitle(String title) {
			this.title = title;
		}
		/*获取广告时间*/
		public String getAdd_time() {
			return add_time;
		}
		/*设置广告时间*/
		public void setAdd_time(String add_time) {
			this.add_time = add_time;
		}
		
	}
	
	
	public static class Notice_Server implements Serializable {
		private String info;
		private String status;
		ArrayList<Notice_Info> notice_info;
		/*获取返回信息*/
		public String getInfo() {
			return info;
		}
		/*设置返回信息*/
		public void setInfo(String info) {
			this.info = info;
		}
		/*获取返回状态*/
		public String getStatus() {
			return status;
		}
		/*设置返回状态*/
		public void setStatus(String status) {
			this.status = status;
		}
		/*获取广告列表*/
		public ArrayList<Notice_Info> getNotice_info() {
			return notice_info;
		}
		/*设置广告列表*/
		public void setNotice_info(ArrayList<Notice_Info> notice_info) {
			this.notice_info = notice_info;
		}
		
		
	}
	
}
