package com.tchappy.photo.activity;

import com.tchappy.pwy.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		setContentView(R.layout.about);
		
		final Button btnBack = (Button) findViewById(R.id.top_bar_back);
		final TextView txtTitle = (TextView) findViewById(R.id.top_bar1_title);
		
		txtTitle.setText(getString(R.string.about_title_str));
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

}
