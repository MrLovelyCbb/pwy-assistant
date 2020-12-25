package com.tchappy.photo.activity;

import com.tchappy.photo.util.Utils;
import com.tchappy.pwy.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QueryCommissionActivity extends FragmentActivity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_5_2);
		
		final Button btnBack = (Button) findViewById(R.id.top_bar_back);
		final TextView txtTitle = (TextView) findViewById(R.id.top_bar1_title);
		
		txtTitle.setText(getString(R.string.query_menu1_str));
		
		btnBack.setOnClickListener(this);
		
		final TextView txtTime = (TextView) findViewById(R.id.query_dtime); 
		
		txtTime.setText(Utils.getFormatNowDate());
		
		final LinearLayout today_comm_view = (LinearLayout) findViewById(R.id.today_comm);
		final LinearLayout history_comm_view = (LinearLayout) findViewById(R.id.history_comm);
//		final LinearLayout reward_comm_view = (LinearLayout) findViewById(R.id.reward_comm);
		
		today_comm_view.setOnClickListener(this);
		history_comm_view.setOnClickListener(this);
//		reward_comm_view.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.today_comm:
			intent = new Intent(this, TodayCommMoneyActivity.class);
			break;
		case R.id.history_comm:
			intent = new Intent(this, HistoryCommMoneyActivity.class);
			break;
//		case R.id.reward_comm:
//			intent = new Intent(this, RewardCommMoneyActivity.class);
//			break;
		case R.id.top_bar_back:
			finish();
			return;
		default:
			break;
		}
		startActivity(intent);
	}

}
