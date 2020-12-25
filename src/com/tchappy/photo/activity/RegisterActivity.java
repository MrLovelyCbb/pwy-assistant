package com.tchappy.photo.activity;

import java.util.Vector;

import org.apache.http.Header;
import org.json.JSONObject;

import com.tchappy.photo.data.BasesEntity;
import com.tchappy.photo.http.JsonHttpResponseHandler;
import com.tchappy.photo.thread.SspanApplication;
import com.tchappy.photo.thread.SspanBaseActivity;
import com.tchappy.photo.util.Preferences;
import com.tchappy.pwy.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends SspanBaseActivity{

	private EditText edit_account;
	private EditText edit_smscode;
	private EditText edit_truename;
	private EditText edit_password;
	private EditText edit_invite;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.register);
		
		final Button btnBack = (Button) findViewById(R.id.top_bar_back);
		final TextView txtTitle = (TextView) findViewById(R.id.top_bar1_title);
		
		txtTitle.setText(getString(R.string.login_item9_str));
		btnBack.setVisibility(8);
		
		edit_account = (EditText) findViewById(R.id.register_username_edit);
		edit_smscode = (EditText) findViewById(R.id.register_smscode_edit);
		edit_truename = (EditText) findViewById(R.id.register_truename_edit);
		edit_password = (EditText) findViewById(R.id.register_password_edit);
		edit_invite = (EditText) findViewById(R.id.register_invite_edit);
		
		final Button btn_getSmsCode = (Button) findViewById(R.id.register_getsmscode);
		final Button btn_register = (Button) findViewById(R.id.register_bt);
		
		btn_getSmsCode.setOnClickListener(btnSmsOnClickListener);
		btn_register.setOnClickListener(btnRegisterOnClickListener);
	}
	
	private View.OnClickListener btnSmsOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (isEmptyByComponents(edit_account)){
				showTxt("账号不能为空");
				return;
			}
			
			SspanApplication.asyncApi.ReqGetValiCode(getTxt(edit_account), 1, getValiCodeResponseHandler);
		}
	};
	
	private View.OnClickListener btnRegisterOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (isEmptyByComponents(edit_smscode)){
				showTxt("验证码不能为空");
				return;
			}
			if (isEmptyByComponents(edit_account,edit_password,edit_truename)){
				showTxt("密码及姓名不能为空...");
				return;}
			Vector<String> v1 = EditTextToString(edit_account,edit_password,edit_smscode,edit_truename,edit_invite);
			if (v1.size() >= 3)
				SspanApplication.asyncApi.ReqRegister(v1.get(0), v1.get(1), v1.get(2), "117.963984", "28.420003", v1.get(3), " ", jsonResponseHandler);
			else if (v1.size() >= 4)
				SspanApplication.asyncApi.ReqRegister(v1.get(0), v1.get(1), v1.get(2), "117.963984", "28.420003", v1.get(3), v1.get(4), jsonResponseHandler);
		}
	};
	
	private JsonHttpResponseHandler getValiCodeResponseHandler = new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			hintKbTwo();
			BasesEntity status = new BasesEntity(response);
			if (status.isStatus()){
//				System.out.println("GetSmsRegister Code ------------"+response.toString());
			}else{
				showTxt("获取验证码错误" + status.getInfo());
			}
		}
		
	};
	
	private JsonHttpResponseHandler jsonResponseHandler = new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			hintKbTwo();
			System.out.println("Register UI Response-----------"+response.toString());
			BasesEntity status = new BasesEntity(response);
			if (status.isStatus())
			{
				SharedPreferences.Editor mEditor = SspanApplication.mPref.edit();
				mEditor.putString(Preferences.UserName,getTxt(edit_account));
				mEditor.putString(Preferences.PassWord,getTxt(edit_password));
				mEditor.putString(Preferences.UID, status.getData().optString("id"));
				mEditor.putString(Preferences.Invate,status.getData().optString("invited_phone"));
				SspanApplication.SID = status.getData().optString("id");
				SspanApplication.MYPHONE = getTxt(edit_account);
				mEditor.commit();
				
				showTxt("注册成功" + status.getInfo());
				
				Intent autoLogin_intent = new Intent(RegisterActivity.this, MainActivity.class);
				startActivity(autoLogin_intent);
				finish();
			}else{
				showTxt("注册失败" + status.getInfo());
			}
		}
		
	};
}
