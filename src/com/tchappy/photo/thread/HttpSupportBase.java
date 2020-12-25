package com.tchappy.photo.thread;

import com.tchappy.photo.http.AsyncHttpClient;

public class HttpSupportBase {

protected HttpConnectionClient http = null;
	
	HttpSupportBase(){
		http = getHttpClient();
	}
	
	public HttpConnectionClient getHttpClient(){
		if (http != null)
			return http;
		else 
			return new HttpConnectionClient();

	}
}
