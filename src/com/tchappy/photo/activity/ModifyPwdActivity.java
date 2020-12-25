package com.tchappy.photo.activity;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.tchappy.photo.data.BasesEntity;
import com.tchappy.photo.http.JsonHttpResponseHandler;
import com.tchappy.photo.thread.SspanApplication;
import com.tchappy.photo.thread.SspanBaseActivity;
import com.tchappy.photo.util.Preferences;
import com.tchappy.pwy.R;

public class ModifyPwdActivity extends SspanBaseActivity {

	private EditText e_txt_oldPassword;
	private EditText e_txt_newPassword;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		setContentView(R.layout.modify_pwd_view);
		
		final Button btnBack = (Button) findViewById(R.id.top_bar_back);
		final TextView txtTitle = (TextView) findViewById(R.id.top_bar1_title);
		
		txtTitle.setText(getString(R.string.password_title_str));
		
		e_txt_oldPassword = (EditText) findViewById(R.id.oldPassword);
		e_txt_newPassword = (EditText) findViewById(R.id.new_password);
		
		final Button btnConfirm = (Button) findViewById(R.id.btnConfirm);
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		// 确认修改.
		btnConfirm.setOnClickListener(btnModifyPwdOnClickListener);
	}
	
	private View.OnClickListener btnModifyPwdOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if ( TextUtils.isEmpty(e_txt_newPassword.getText().toString()) || TextUtils.isEmpty(e_txt_oldPassword.getText().toString()))
				return;
			
			String sealer = SspanApplication.mPref.getString(Preferences.UID, "");
			SspanApplication.asyncApi.ReqModifyPwd(sealer, e_txt_oldPassword.getText().toString(), e_txt_newPassword.getText().toString(), new JsonHttpResponseHandler()
			{

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					BasesEntity status = new BasesEntity(response);
					if (status.isStatus()){
						SspanApplication.Is_AutoLogin = false;
						showTxt("ModifyPwd--------------"+status.getInfo());
						Intent login_intent = new Intent(ModifyPwdActivity.this, LoginActivity.class);
						startActivity(login_intent);
						finish();
					}else
					{
						showTxt("ModifyPwd--------------"+status.getInfo());
					}
				}
				
			});
		}
	};

}
