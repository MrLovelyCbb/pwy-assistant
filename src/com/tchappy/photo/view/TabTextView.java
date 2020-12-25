package com.tchappy.photo.view;

import com.tchappy.pwy.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabTextView extends LinearLayout {

	private ImageView imageView;
	private TextView textView;
	
	public TabTextView(Context context)
	{
		super(context);
	}
	public TabTextView(Context context,AttributeSet attrs){
		super(context, attrs);
		
		initCompontent(attrs);
	}
	
	public void initCompontent(AttributeSet attrs){
		LayoutInflater.from(getContext()).inflate(R.layout.tab_txtbutton, this, true);
		imageView = (ImageView) findViewById(R.id.my_button_img);
		textView = (TextView) findViewById(R.id.my_button_text);
		
		TypedArray t = getContext().obtainStyledAttributes(attrs,R.styleable.TabTextView);
		
		String textValue = t.getString(R.styleable.TabTextView_txt_value);
		boolean isVisible = t.getBoolean(R.styleable.TabTextView_img_visible, false);
		
		t.recycle();
		
		if (isVisible){
			imageView.setVisibility(View.VISIBLE);
		}else{
			imageView.setVisibility(View.GONE);
		}
		
		textView.setText(textValue);
	}

	public void setImgVisible(int visible){
		imageView.setVisibility(visible);
	}
	
	public void setText(String text) {
		textView.setText(text);
	}
}
