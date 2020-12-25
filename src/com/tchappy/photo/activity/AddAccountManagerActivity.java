package com.tchappy.photo.activity;

import org.apache.http.Header;
import org.json.JSONObject;

import com.tchappy.photo.data.BasesEntity;
import com.tchappy.photo.fragment.BaseFragmentActivity;
import com.tchappy.photo.http.JsonHttpResponseHandler;
import com.tchappy.photo.thread.SspanApplication;
import com.tchappy.pwy.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddAccountManagerActivity extends BaseFragmentActivity {

	private EditText edit_Uname;
	private EditText edit_Account;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		setContentView(R.layout.comm_add_acc_manager);
		
		final Button btnBack = (Button) findViewById(R.id.top_bar_back);
		final TextView txtTitle = (TextView) findViewById(R.id.top_bar1_title);
		
		txtTitle.setText(getString(R.string.login_title_manager));
		
		edit_Uname = (EditText) findViewById(R.id.account_name_alipay);
		edit_Account = (EditText) findViewById(R.id.account_account_alipay);
		
		
		final Button btnAdd = (Button) findViewById(R.id.btn_add_confirm);
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		btnAdd.setOnClickListener(btnAdd_OnClickListener);
	}

	
	private View.OnClickListener btnAdd_OnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			SspanApplication.asyncApi.ReqAddAccount(SspanApplication.SID, getTxt(edit_Uname), getTxt(edit_Account), new JsonHttpResponseHandler(){

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					BasesEntity status = new BasesEntity(response);
					if (status.isStatus()){
						showTxt("添加成功");
						// or finsh();
						Intent intent = new Intent(AddAccountManagerActivity.this, CommAccountManagerActivity.class);
						startActivity(intent);
					}else if (status.getStatusCode() == 1){
						showTxt("用户不存在");
					}else if (status.getStatusCode() == 2){
						showTxt("已经添加过该支付宝账号");
					}else if (status.getStatusCode() == 3){
						showTxt("添加账号的数量不能大于5个");
					}else if (status.getStatusCode() == 4){
						showTxt("未知错误");
					}
				}
				
			});
		}
	};
}
