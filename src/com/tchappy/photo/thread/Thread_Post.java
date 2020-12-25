﻿package com.tchappy.photo.thread;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import com.tchappy.photo.util.MyProgressBar;
import com.tchappy.photo.util.Utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

/**
 * @see <b>主要作用：开启线程post数据到某URL地址，获取返回的数据，通过消息传递到Activity</b>
 * @author 罗超 2014 09 01
 */
public class Thread_Post extends Thread {

	private Handler handler;
	private String URL;
	private Map<String, String> infomap;
	private Map<String, File> filemap;
	private int msgWhat;
	private HttpClient httpClient;
	private Map<String, String> map;
	private int what = 0;
	private MyProgressBar bar;

	/**
	 * POST获取数据，代参
	 * 
	 * @param msgWhat
	 *            消息号
	 */
	public Thread_Post(Activity activity, Handler handler, String URL, Map<String, String> infomap, Map<String, File> filemap, int msgWhat) {
		this.bar = new MyProgressBar(activity);
		httpClient = getHttpClient();
		this.handler = handler;
		this.URL = URL;
		this.infomap = infomap;
		this.filemap = filemap;
		this.msgWhat = msgWhat;
		this.what = 0;
		showBar(msgWhat);
	}
	
	/**
	 * POST获取数据，代参 --更新程序
	 * 
	 * @param msgWhat
	 *            消息号
	 */
	public Thread_Post(Activity activity, Handler handler, String URL, Map<String, String> map, int msgWhat) {
		this.bar = new MyProgressBar(activity);
		httpClient = getHttpClient();
		this.handler = handler;
		this.URL = URL;
		this.map = map;
		this.msgWhat = msgWhat;
		this.what = 1;
		showBar(msgWhat);
	}

	/**
	 * POST获取数据， 不代参
	 * 
	 * @param msgWhat
	 *            消息号
	 */
	public Thread_Post(Activity activity, Handler handler, String URL, int msgWhat) {
		httpClient = getHttpClient();
		this.handler = handler;
		this.URL = URL;
		this.msgWhat = msgWhat;
	}

	@Override
	public void run() {
		String result = null;
		System.out.println("what="+what);
			switch (what) {
			case 0:
				try {
//					result = Utils.sendPost(httpClient, URL, map);
					result = Utils.post(URL, infomap, filemap);
					Utils.Log(msgWhat + "", result);
				} catch (HttpHostConnectException e) {
					// e.printStackTrace();
					System.out.println("无法连接主机异常");
					result = "网路异常,无法连接到服务器,错误代码001!";
				} catch (ConnectTimeoutException e) {
					// e.printStackTrace();
					System.out.println("网络连接超时异常");
					result = "网路异常,无法连接到服务器,错误代码002!";
				} catch (ClientProtocolException e) {
					// e.printStackTrace();
					System.out.println("网络连接协议异常");
					result = "网路异常,无法连接到服务器,错误代码003!";
				} catch (IOException e) {
					// e.printStackTrace();
					System.out.println("文件IO异常");
					result = "网路异常,无法连接到服务器,错误代码004!";
				} catch (Exception e) {
//					 e.printStackTrace();
					result = "网路异常,无法连接到服务器,错误代码005!";
				} finally {
					Message message = handler.obtainMessage();
					message.obj = result;
					message.what = msgWhat;
					handler.sendMessage(message);
					httpClient.getConnectionManager().shutdown();
					bar.dismiss();
				}
				break;
			case 1:
				try {
					result = Utils.sendPost(httpClient, URL, map);
					Utils.Log(msgWhat + "", result);
				} catch (HttpHostConnectException e) {
					// e.printStackTrace();
					System.out.println("无法连接主机异常");
					result = "网路异常,无法连接到服务器,错误代码001!";
				} catch (ConnectTimeoutException e) {
					// e.printStackTrace();
					System.out.println("网络连接超时异常");
					result = "网路异常,无法连接到服务器,错误代码002!";
				} catch (ClientProtocolException e) {
					// e.printStackTrace();
					System.out.println("网络连接协议异常");
					result = "网路异常,无法连接到服务器,错误代码003!";
				} catch (IOException e) {
					// e.printStackTrace();
					System.out.println("文件IO异常");
					result = "网路异常,无法连接到服务器,错误代码004!";
				} catch (Exception e) {
//					 e.printStackTrace();
					result = "网路异常,无法连接到服务器,错误代码005!";
				} finally {
					Message message = handler.obtainMessage();
					message.obj = result;
					message.what = msgWhat;
					handler.sendMessage(message);
					httpClient.getConnectionManager().shutdown();
					bar.dismiss();
				}
				break;

			default:
				break;
			}

	}

	public static HttpClient getHttpClient() {
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
		HttpConnectionParams.setSoTimeout(httpParams, 20000);
		HttpClient client = new DefaultHttpClient(httpParams);
		return client;
	}

	public static String getUTF8Str(String result) {
		String str = result;
		try {
			str = new String(result.getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 发起请求时进行的加载显示
	 * 
	 * @param type
	 */
	public void showBar(int type) {
		switch (type) {
		case Utils.POST_UPDATA:
			bar.show("检查更新");
			break;
		case Utils.POST_RECEIVE:
			bar.show("正在发送");
			break;
		default:
			break;
		}
	}
}
