package com.tchappy.photo.thread;

import java.io.File;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Handler;

import com.tchappy.photo.http.AsyncHttpClient;
import com.tchappy.photo.http.JsonHttpResponseHandler;
import com.tchappy.photo.http.RequestParams;

public class Thread_HttpClient extends JsonHttpResponseHandler{

	private AsyncHttpClient http;
	private JsonHttpResponseHandler json_handler;
	
	private static AsyncHttpClient client;
	private static AsyncHttpClient getAsyncHttpClient()
	{
		if (client != null)
			return client;
		client = new AsyncHttpClient();
		return client;
	}
	
	
	public void Thread_HttpClient(Activity activity, Handler handler, String URL,RequestParams params, Map<String, File> filemap, int msgWhat){
		http = getAsyncHttpClient();
		http.get(URL, params, json_handler);
	}
	
}
