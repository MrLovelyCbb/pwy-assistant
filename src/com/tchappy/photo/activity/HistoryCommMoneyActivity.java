package com.tchappy.photo.activity;

import java.util.Calendar;

import com.tchappy.photo.fragment.BaseFragmentActivity;
import com.tchappy.photo.util.Utils;
import com.tchappy.pwy.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class HistoryCommMoneyActivity extends BaseFragmentActivity {

	private EditText edit_startTime;
	private EditText edit_endTime;
	private EditText edit_uName;
	private EditText edit_uPhone;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		setContentView(R.layout.activity_5_2_2);
		
		final Button btnBack = (Button) findViewById(R.id.top_bar_back);
		final TextView txtTitle = (TextView) findViewById(R.id.top_bar1_title);
		
		txtTitle.setText(getString(R.string.query_item27_str));
		
		edit_startTime = (EditText) findViewById(R.id.history_comm_starttime);
		edit_endTime = (EditText) findViewById(R.id.histroy_comm_endtime);
		edit_uName = (EditText) findViewById(R.id.histroy_comm_uname);
		edit_uPhone = (EditText) findViewById(R.id.histroy_comm_uphone);
		
		final Button btnQuery = (Button) findViewById(R.id.histroy_comm_query);
		
		btnQuery.setOnClickListener(btnQuery_OnClickListener);
		
		edit_startTime.setHint(Utils.getFormatNowDate()+"  "+"(必填)");
		edit_endTime.setHint(Utils.getFormatNowDate()+"  "+"(必填)");
		
		edit_startTime.setOnClickListener(startTime_OnClickListener);
		edit_endTime.setOnClickListener(endTime_OnClickListener);
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void alertDialog(String title,final EditText edit_txt,final EditText next_edit)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this); 
        View view = View.inflate(this, R.layout.date_dialog, null); 
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        builder.setView(view);
        
        Calendar cal = Calendar.getInstance(); 
        cal.setTimeInMillis(System.currentTimeMillis()); 
        datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);
        
        builder.setTitle(title);
        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() { 
        	   
            @Override 
            public void onClick(DialogInterface dialog, int which) { 

                StringBuffer sb = new StringBuffer(); 
                sb.append(String.format("%d-%02d-%02d",  
                        datePicker.getYear(),  
                        datePicker.getMonth() + 1, 
                        datePicker.getDayOfMonth()));

                edit_txt.setText(sb); 
                next_edit.requestFocus(); 
                   
                dialog.cancel(); 
            } 
        });
        
        Dialog dialog = builder.create();
        dialog.show();
	}
	
	private boolean ValiData(){
		if (isEmptyByComponents(edit_startTime)){
			showTxt("请您选择起始日期...");
			return false;
		}
		if (isEmptyByComponents(edit_endTime)){
			showTxt("请您输入结束日期...");
			return false;
		}
		if (!isEmptyByComponents(edit_uPhone) && !Utils.isMobile(getTxt(edit_uPhone))){
			showTxt("您输入不是手机号...");
		}
		return true;
	}
	private View.OnClickListener startTime_OnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			hintKbTwo();
			alertDialog("选取起始日期",edit_startTime,edit_endTime);
		}
	};
	
	private View.OnClickListener endTime_OnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			hintKbTwo();
			alertDialog("选取结束日期",edit_endTime,edit_uName);
		}
	};
	
	private View.OnClickListener btnQuery_OnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (!ValiData()){
				return;
			}
			// 跳转至查询结果界面
			Intent intent = new Intent(HistoryCommMoneyActivity.this, HistoryMoneyResultActivity.class);
			intent.putExtra("his_startTime", getTxt(edit_startTime));
			intent.putExtra("his_endTime", getTxt(edit_endTime));
			intent.putExtra("his_uName", getTxt(edit_uName));
			intent.putExtra("his_uPhone", getTxt(edit_uPhone));
			startActivity(intent);
		}
	};
}
