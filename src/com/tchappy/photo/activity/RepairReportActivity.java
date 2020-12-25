package com.tchappy.photo.activity;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tchappy.photo.data.BasesEntity;
import com.tchappy.photo.http.JsonHttpResponseHandler;
import com.tchappy.photo.thread.SspanApplication;
import com.tchappy.photo.thread.SspanBaseActivity;
import com.tchappy.photo.util.Preferences;
import com.tchappy.pwy.R;

public class RepairReportActivity extends SspanBaseActivity{

	private String repair_uid;
	private String repair_phone;
	
	private EditText question_edit;
	private EditText adress_edit;
	private EditText send_edit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		if (intent != null)
		{
			repair_uid = intent.getStringExtra("repair_uid");
			repair_phone = intent.getStringExtra("repair_phone");
		}
		
		setContentView(R.layout.view_claim);
		
		question_edit = (EditText) findViewById(R.id.claim_miaosu_edit);
		adress_edit = (EditText) findViewById(R.id.claim_tel_edit);
		send_edit = (EditText) findViewById(R.id.claim_add_edit);
		
		final Button btnSend = (Button) findViewById(R.id.claim_send_btn);
		final Button btnBack = (Button) findViewById(R.id.claim_back_btn);
		
		btnSend.setOnClickListener(btnSend_OnClickListener);
		btnBack.setOnClickListener(btnBack_OnClickListener);
	}

	
	private View.OnClickListener btnSend_OnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (repair_uid.equals("") || repair_phone.equals(""))
				return;
			if (isEmptyByComponents(adress_edit))
			{
				showTxt("请输入您的详细地址.");
				return;
			}
			if (isEmptyByComponents(question_edit)){
				showTxt("请输入您的故障描述.");
				return;
			}
			String seallerid = SspanApplication.mPref.getString(Preferences.UID, "");
			SspanApplication.asyncApi.ReqReportRepair(seallerid, repair_uid, repair_phone, getTxt(adress_edit), getTxt(question_edit), jsonHandler);
		}
	};
	
	private View.OnClickListener btnBack_OnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finish();
		}
	};
	
	private JsonHttpResponseHandler jsonHandler = new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			BasesEntity status = new BasesEntity(response);
			if (status.isStatus())
			{
				showTxt("发送报修成功");
				finish();
			}
		}
		
	};
}
