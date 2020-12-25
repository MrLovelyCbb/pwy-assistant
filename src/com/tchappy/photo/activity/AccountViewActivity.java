package com.tchappy.photo.activity;

import org.apache.http.Header;
import org.json.JSONObject;

import com.tchappy.photo.data.BasesEntity;
import com.tchappy.photo.http.JsonHttpResponseHandler;
import com.tchappy.photo.thread.SspanApplication;
import com.tchappy.photo.thread.SspanBaseActivity;
import com.tchappy.photo.util.Preferences;
import com.tchappy.photo.util.Utils;
import com.tchappy.pwy.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AccountViewActivity extends SspanBaseActivity {

	private TextView tv_account;
	private EditText edit_invate;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		setContentView(R.layout.account_query_view);
		
		final Button btnBack = (Button) findViewById(R.id.top_bar_back);
		final TextView txtTitle = (TextView) findViewById(R.id.top_bar1_title);
		
		txtTitle.setText(getString(R.string.login_item11_str));
		
		tv_account = (TextView) findViewById(R.id.account_query_txt);
		edit_invate = (EditText) findViewById(R.id.invate_number_edit);
		final Button btnEditInvate = (Button) findViewById(R.id.btn_edit_invate);
//		edit_invate.setEnabled(false);
		
		tv_account.setText(SspanApplication.mPref.getString(Preferences.UserName, ""));
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ReqSaveInvate();
				finish();
			}
		});
		
		String invateStr = SspanApplication.mPref.getString(Preferences.Invate, "");
		edit_invate.setText(invateStr);
		
		if (SspanApplication.mPref.getString(Preferences.Invate, "").equals(""))
		{
			edit_invate.setEnabled(true);
		}else{
			edit_invate.setEnabled(false);
		}
		
		btnEditInvate.setOnClickListener(editInvateOnClickListener);
	}

	private View.OnClickListener editInvateOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (SspanApplication.mPref.getString(Preferences.Invate, "").equals(""))
			{
				edit_invate.setEnabled(true);
				edit_invate.setFocusable(true);
				
				ReqSaveInvate();
			}else{
				edit_invate.setEnabled(false);
				edit_invate.setFocusable(false);
			}
		}
	};
	
	private void ReqSaveInvate()
	{
		if(SspanApplication.mPref.getString(Preferences.Invate, "").equals("")){
			if (isEmptyByComponents(edit_invate)){
				showTxt("您还没有设置推荐人哦...");
				return;
			}
			if (!Utils.isMobile(getTxt(edit_invate))){
				showTxt("您输入手机号码不正确");
				return;
			}
			String sealerid = SspanApplication.mPref.getString(Preferences.UID, "");
			SspanApplication.asyncApi.ReqModifyInvite(sealerid, getTxt(edit_invate), jsonResponseHandler);
		}
	}
	
	private JsonHttpResponseHandler jsonResponseHandler = new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			BasesEntity status = new BasesEntity(response);
			switch(status.getStatusCode()){
				case 0:
					showTxt("添加介绍人成功");
					return;
				case 1:
					showTxt("用户不存在");
					return;
				case 2:
					showTxt("已经有推荐人了");
					return;
				case 3:
					showTxt("推荐人不存在");
					return;
				case 4:
					showTxt("不能互为推荐人");
					return;
				case 5:
					showTxt("没有填推荐人手机号码");
					return;
			}
		}
		
	};
}
