package com.tchappy.photo.view;

import com.tchappy.photo.view.TitleView.OnLeftButtonClickListener;
import com.tchappy.photo.view.TitleView.OnRightButtonClickListener;
import com.tchappy.pwy.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyTopBar extends FrameLayout implements View.OnClickListener{
	
	private Button leftBtn;
	private TextView txtTitle;
	private View v;
	
	private Button rightBtn;
	private View frameBg;
	
	private OnLeftButtonClickListener mOnLeftButtonClickListener;
	private OnRightButtonClickListener mOnRightButtonClickListener;
	
	public MyTopBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.topbar, this,true);
		
		//v = LayoutInflater.from(context).inflate(R.layout.topbar, this, true);
		leftBtn = (Button) findViewById(R.id.top_bar_left);
		txtTitle = (TextView) findViewById(R.id.top_bar_title);
		
		rightBtn = (Button) findViewById(R.id.top_bar_right);
	}
	
	public void setBg(int resid) {
		v.setBackgroundResource(resid);
	}
	
	public void setBack(int resId) {
		leftBtn.setBackgroundResource(resId);
	}
	
	public void setTitle(int resId) {
		txtTitle.setText(resId);
	}
	
	public void setRightVisibilitys(int visibility) {
		rightBtn.setVisibility(visibility);
	}
	
	public void setLeftVisibilitys(int visibility) {
		leftBtn.setVisibility(visibility);
	}

	public void setTextSize(float size){
		txtTitle.setTextSize(size);
	}
	
	public void setFrameBg(int resid) {
		frameBg.setBackgroundResource(resid);
	}
	
	public void setRightTxt(int resid) {
		rightBtn.setText(resid);
	}
	
	@Override
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.left_btn:
			if(mOnLeftButtonClickListener != null)
				mOnLeftButtonClickListener.onClick(v);
			break;
		case R.id.right_btn:
			if(mOnRightButtonClickListener != null)
				mOnRightButtonClickListener.onClick(v);
			break;
		}
	}
}
