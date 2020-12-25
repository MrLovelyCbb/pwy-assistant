package com.tchappy.photo.thread;

import com.tchappy.photo.http.AsyncHttpClient;

public class AsyncHttpSupportBase {

protected AsyncHttpClient http = null;
	
	protected AsyncHttpSupportBase(){
		http = GetInstance();
	}
	
	public AsyncHttpClient GetInstance()
	{
		if (http == null)
		{
			http = new AsyncHttpClient();
		}
		return http;
	}
}
