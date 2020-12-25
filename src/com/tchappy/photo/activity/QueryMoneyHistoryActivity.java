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

public class QueryMoneyHistoryActivity extends BaseFragmentActivity{

	private EditText edit_starTime;
	private EditText edit_endTime;
	private EditText edit_moneyfive;
	private EditText edit_highmoney;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_5_3);
		
		final Button btnBack = (Button) findViewById(R.id.top_bar_back);
		final TextView txtTitle = (TextView) findViewById(R.id.top_bar1_title);
		
		edit_starTime = (EditText) findViewById(R.id.activity_5_3_starTime_edit);
		edit_endTime = (EditText) findViewById(R.id.activity_5_3_endTime_edit);
		edit_moneyfive = (EditText) findViewById(R.id.activity_5_3_moneyfive_edit);
		edit_highmoney = (EditText) findViewById(R.id.activity_5_3_highmoney_edit);
		
		final Button btnQuery = (Button) findViewById(R.id.btn_5_3_query);
		
		txtTitle.setText(getString(R.string.query_menu2_str));
		edit_starTime.setHint(Utils.getFormatNowDate());
		edit_endTime.setHint(Utils.getFormatNowDate());
		
		edit_starTime.setOnClickListener(startTime_OnClickListener);
		edit_endTime.setOnClickListener(endTime_OnClickListener);
		
		btnQuery.setOnClickListener(btnQuery_OnClickListener);
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
	
	private View.OnClickListener startTime_OnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			hintKbTwo();
			alertDialog("选取起始日期",edit_starTime,edit_endTime);
		}
	};
	
	private View.OnClickListener endTime_OnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			hintKbTwo();
			alertDialog("选取结束日期",edit_endTime,edit_moneyfive);
		}
	};
	
	private boolean ValiData(){
		if (isEmptyByComponents(edit_starTime)){
			showTxt("请您选择起始日期...");
			return false;
		}
		if (isEmptyByComponents(edit_endTime)){
			showTxt("请您输入结束日期...");
			return false;
		}
		if (!isEmptyByComponents(edit_moneyfive) &&((Integer.parseInt(getTxt(edit_moneyfive))) % 5) != 0){
			showTxt("您输入的不是5的倍数");
			return false;
		}
		return true;
	}
	
	private View.OnClickListener btnQuery_OnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (!ValiData()){
				return;
			}
			// 跳转至查询结果界面
			Intent intent = new Intent(QueryMoneyHistoryActivity.this, QueryMoneyHistoryResultActivity.class);
			intent.putExtra("QMHRA_startTime", getTxt(edit_starTime));
			intent.putExtra("QMHRA_endTime", getTxt(edit_endTime));
			intent.putExtra("QMHRA_moneyFive", getTxt(edit_moneyfive));
			intent.putExtra("QMHRA_highMoney", getTxt(edit_highmoney));
			startActivity(intent);
		}
	};

}
