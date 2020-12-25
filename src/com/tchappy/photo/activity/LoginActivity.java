package com.tchappy.photo.activity;

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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends SspanBaseActivity {

	private EditText txtUser;
	private EditText txtPwd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if (SspanApplication.Is_AutoLogin)
		{
			Intent main_intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(main_intent);
			finish();
		}
		
		setContentView(R.layout.login);
		
		final Button btnBack = (Button) findViewById(R.id.top_bar_back);
		final TextView txtTitle = (TextView) findViewById(R.id.top_bar1_title);
		
		txtTitle.setText(getString(R.string.login_title_str));
		btnBack.setVisibility(8);
		
		txtUser = (EditText) findViewById(R.id.username_edit);
		txtPwd = (EditText) findViewById(R.id.password_edit);
		final Button btn_signin = (Button) findViewById(R.id.signin_button);
		
		final TextView lblRegister = (TextView) findViewById(R.id.register_link);
		final TextView lblFindpwd = (TextView) findViewById(R.id.get_password_link);
		
		btn_signin.setOnClickListener(SignOnClickLister);
		lblRegister.setOnClickListener(RegisterOnClickLister);
		lblFindpwd.setOnClickListener(FindpwdOnClickLister);
	}

	private View.OnClickListener FindpwdOnClickLister = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent find_intent = new Intent(LoginActivity.this, FindPwdActivity.class);
			startActivity(find_intent);
		}
	};

	private View.OnClickListener RegisterOnClickLister = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent login_intent = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(login_intent);
		}
	};
	
	private View.OnClickListener SignOnClickLister = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (TextUtils.isEmpty(txtUser.getText().toString()) || TextUtils.isEmpty(txtPwd.getText().toString()))
				return;
			
			SspanApplication.asyncApi.ReqLogin(txtUser.getText().toString(), txtPwd.getText().toString(),new JsonHttpResponseHandler()
			{

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					hintKbTwo();
					BasesEntity status = new BasesEntity(response);
					if (status.isStatus()){
						SharedPreferences.Editor mEditor = SspanApplication.mPref.edit();
						mEditor.putString(Preferences.UserName,txtUser.getText().toString());
						mEditor.putString(Preferences.PassWord,txtPwd.getText().toString());
						mEditor.putString(Preferences.UID,((JSONObject) status.getData()).optString("id"));
						mEditor.putString(Preferences.Invate,status.getData().optString("invited_phone"));
						SspanApplication.SID = status.getData().optString("id");
						SspanApplication.MYPHONE = getTxt(txtUser);
						mEditor.commit();
						
						Intent main_intent = new Intent(LoginActivity.this, MainActivity.class);
						startActivity(main_intent);
						finish();
					}else
					{
						showTxt("用户名或密码错误" + status.getInfo());
					}
				}
			});
		}
	};
	
}
