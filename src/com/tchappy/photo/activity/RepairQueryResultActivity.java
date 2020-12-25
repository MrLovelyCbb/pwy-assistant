package com.tchappy.photo.activity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.tchappy.photo.data.RepairEntity;
import com.tchappy.photo.http.AsyncHttpResponseHandler;
import com.tchappy.photo.thread.SspanApplication;
import com.tchappy.pwy.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class RepairQueryResultActivity extends FragmentActivity {

	private String intentStatus;
	private Intent othreIntent;
	
	protected RepairEntity repair = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		othreIntent = getIntent();
		
		if (othreIntent != null){
			intentStatus = (String)getIntent().getStringExtra("data");
			try {
				repair = new RepairEntity(new JSONObject(intentStatus));
			} catch (JSONException e) {
				new Exception("Maybe RepairEntity intentStatus == null");
			}
		}
		
		setContentView(R.layout.repair_queryresult_view);
		
		final Button btnBack = (Button) findViewById(R.id.top_bar_back);
		final TextView txtTitle = (TextView) findViewById(R.id.top_bar1_title);
		final Button btnReportrepair = (Button) findViewById(R.id.btn_report_repair);
		
		if (repair == null)return;
		
		// 组件
		final EditText edit_name = (EditText) findViewById(R.id.repair_result_name);
		final EditText edit_phone = (EditText) findViewById(R.id.repair_result_phone);
		final EditText edit_imei = (EditText) findViewById(R.id.repair_result_imei);
		edit_name.setText(repair.getUname());
		edit_phone.setText(repair.getUphone());
		edit_imei.setText(repair.getImei());
		edit_name.setEnabled(false);
		edit_phone.setEnabled(false);
		edit_imei.setEnabled(false);
		
		final ImageView imgBtn = (ImageView) findViewById(R.id.repair_result_img1);
		final ImageView imgBtn1 = (ImageView) findViewById(R.id.repair_result_img2);
		
		// 第一幅图
		SspanApplication.asyncApi.ReqGetImg(repair.img1, new AsyncHttpResponseHandler()
		{

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				if (statusCode == 200){
//					BitmapFactory factory=new BitmapFactory();  
                    Bitmap bitmap=BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);  
                    imgBtn.setImageBitmap(bitmap);
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				error.printStackTrace();
			}
			
		});
		
		// 第二幅图
		SspanApplication.asyncApi.ReqGetImg(repair.img2, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				if (statusCode == 200){
//					BitmapFactory factory=new BitmapFactory();  
                    Bitmap bitmap=BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);  
                    imgBtn1.setImageBitmap(bitmap);
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				error.printStackTrace();
			}
		});
		
		txtTitle.setText(getString(R.string.repair_title1_str));
		
		btnReportrepair.setOnClickListener(btnReportrepair_onClickListener);
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private View.OnClickListener btnReportrepair_onClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (repair == null)
				return;
			// 跳转至报修 联系地址界面
			Intent reportIntent = new Intent(RepairQueryResultActivity.this, RepairReportActivity.class);
			reportIntent.putExtra("repair_uid", repair.uid);
			reportIntent.putExtra("repair_phone", repair.Uphone);
			startActivity(reportIntent);
			finish();
		}
	};

}
