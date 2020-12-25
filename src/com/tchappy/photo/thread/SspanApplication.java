package com.tchappy.photo.thread;

import org.apache.http.Header;
import org.json.JSONObject;

import com.tchappy.photo.util.Preferences;
import com.tchappy.photo.activity.LoginActivity;
import com.tchappy.photo.activity.MainActivity;
import com.tchappy.photo.db.SspanDatabase;
import com.tchappy.photo.http.JsonHttpResponseHandler;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class SspanApplication extends Application{

	public static HttpLogic mApi = null;
	public static Context mContext = null;
	public static SharedPreferences mPref;
	public static HttpAsyncLogic asyncApi = null;
	public static SspanDatabase mDb;
	public static Boolean Is_AutoLogin = false;
	public static String SID = "";
	public static String MYPHONE = "";
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this.getApplicationContext();
		mApi = new HttpLogic();
		asyncApi = new HttpAsyncLogic();
		mPref = PreferenceManager.getDefaultSharedPreferences(mContext);
//		mDb = SspanDatabase.getInstance(mContext);
//		if (!mDb.DbIsExist()){
//			mDb.getDb(true);
//		}
		
		// Auto Login
		String username = mPref.getString(Preferences.UserName, "");
		String password = mPref.getString(Preferences.PassWord, "");
		Is_AutoLogin = mPref.getBoolean(Preferences.AUTO_LOGON, false);
		SID = mPref.getString(Preferences.UID, "");
		MYPHONE = mPref.getString(Preferences.UserName, "");
		
		//如果为True则自动登录
		asyncApi.ReqLogin(username, password, new JsonHttpResponseHandler()
		{
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				SharedPreferences.Editor mEditor = SspanApplication.mPref.edit();
				System.out.println("Application Response--------------"+response.toString());
				if (response.optString("status").equals("0")){
					Is_AutoLogin = true;
					mEditor.putString(Preferences.UserName, response.optJSONObject("data").optString("phone"));
					mEditor.putString(Preferences.PassWord, mPref.getString(Preferences.PassWord, ""));
					mEditor.putString(Preferences.UID, response.optJSONObject("data").optString("id"));
					mEditor.putString(Preferences.Invate,response.optJSONObject("data").optString("invited_phone"));
					mEditor.putBoolean(Preferences.AUTO_LOGON, Is_AutoLogin);
					MYPHONE = response.optJSONObject("data").optString("phone");
					mEditor.commit();
					Toast.makeText(mContext, "您已登陆", Toast.LENGTH_SHORT).show();
				}else{
					mEditor.clear();
					mEditor.commit();
					Is_AutoLogin = false;
				}
			}
		});
	}
	
	
	/**
	 * 获取网络状态，是wifi是wap，等
	 * @return
	 */
	public String getNetworkType() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null) {
			if(activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI)
				return activeNetInfo.getTypeName();
			return activeNetInfo.getExtraInfo(); 
			
		} else {
			return null;
		}
	}
	
	@Override
	public void onTerminate() {
		mDb.close();
		if(mApi.getHttpClient()!=null){
			asyncApi.GetInstance().cancelAllRequests(true);
		}
		Toast.makeText(this, "exit app", Toast.LENGTH_LONG);
		System.out.println("Exit");
		super.onTerminate();
	}
}
