package com.tchappy.photo.activity;


import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tchappy.photo.data.BasesEntity;
import com.tchappy.photo.http.JsonHttpResponseHandler;
import com.tchappy.photo.thread.SspanApplication;
import com.tchappy.photo.thread.SspanBaseActivity;
import com.tchappy.pwy.R;

public class FindPwdActivity extends SspanBaseActivity{

	private EditText edit_Account;
	private EditText edit_valiCode;
	private EditText edit_newPwd;
	private EditText edit_confirmPwd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.getpassword);
		
		final Button btnBack = (Button) findViewById(R.id.top_bar_back);
		final TextView txtTitle = (TextView) findViewById(R.id.top_bar1_title);
		
		txtTitle.setText(getString(R.string.login_item10_str));
		btnBack.setVisibility(8);
		
		edit_Account = (EditText) findViewById(R.id.getpassword_account_edit);
		edit_valiCode = (EditText) findViewById(R.id.getpassword_valicode_edit);
		edit_newPwd = (EditText) findViewById(R.id.getpassword_old_edit);
		edit_confirmPwd = (EditText) findViewById(R.id.getpassword_new_edit);
		
		final Button btnFindCode = (Button) findViewById(R.id.btn_getsmspwd_code);
		final Button btnFind = (Button) findViewById(R.id.btn_findpassword);
		btnFindCode.setOnClickListener(findValiCodeOnClickListener);
		btnFind.setOnClickListener(findPwdOnClickListener);
	}
	
	private View.OnClickListener findValiCodeOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (isEmptyByComponents(edit_Account)){
				showTxt("手机号不能为空");
				return;
			}
			
			SspanApplication.asyncApi.ReqGetValiCode(getTxt(edit_Account), 2, jsonResValiCodeHandler);
		}
	};
	
	private JsonHttpResponseHandler jsonResValiCodeHandler = new JsonHttpResponseHandler()
	{

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			BasesEntity status = new BasesEntity(response);
			if (status.isStatus())
			{
				showTxt("验证码已发送");
			}else{
				showTxt("获取失败"+status.getInfo());
			}
		}
		
	};
	
	private JsonHttpResponseHandler jsonResFindPwdHandler = new JsonHttpResponseHandler()
	{

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			BasesEntity status = new BasesEntity(response);
			if (status.isStatus())
			{
				showTxt("找回密码成功,您的新密码是:"+getTxt(edit_confirmPwd));
				Intent login_intent = new Intent(FindPwdActivity.this, LoginActivity.class);
				startActivity(login_intent);
				finish();
			}else{
				showTxt("找回密码失败"+status.getInfo());
			}
		}
		
	};
	
	private View.OnClickListener findPwdOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (isEmptyByComponents(edit_newPwd,edit_confirmPwd)){
				showTxt("密码不能为空");
				return;
			}
			System.out.println("newpwd"+getTxt(edit_newPwd)+"||||||||||||"+getTxt(edit_confirmPwd));
			if (!getTxt(edit_newPwd).equals(getTxt(edit_confirmPwd)))
			{
				showTxt("两次输入密码不对");
				return;
			}
			if (isEmptyByComponents(edit_valiCode,edit_confirmPwd)){
				showTxt("验证码不能为空");
				return;
			}
			SspanApplication.asyncApi.ReqFindPwd(getTxt(edit_Account), getTxt(edit_confirmPwd), getTxt(edit_valiCode), jsonResFindPwdHandler);
		}
	};
}
