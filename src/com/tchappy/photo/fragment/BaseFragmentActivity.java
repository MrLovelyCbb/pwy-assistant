package com.tchappy.photo.fragment;

import java.util.Vector;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class BaseFragmentActivity extends FragmentActivity {

	public BaseFragmentActivity() {
		// TODO Auto-generated constructor stub
	}

	protected void hintKbTwo() {
		if (this == null)
			return;
		 InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);            
		 if(imm.isActive()&&this.getCurrentFocus()!=null){
		    if (this.getCurrentFocus().getWindowToken()!=null) {
		    imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		    }             
		 }
	}
	
	protected boolean isEmptyByComponents(EditText... edits)
	{
		boolean isEmpty = false;
		for (EditText edit : edits){
			if (TextUtils.isEmpty(edit.getText().toString()))
				isEmpty = true;
		}
		return isEmpty;
	}
	
	protected Vector<String> EditTextToString(EditText... edits)
	{
		if (edits == null || edits.length == 0)
			return null;
		
		Vector<String> v = new Vector<String>(edits.length);
		for (EditText edit : edits){
			if (!TextUtils.isEmpty(edit.getText().toString()))
				v.add(edit.getText().toString());
		}
		return v;
	}
	
	/**
	 * 获取一个EditText 值
	 * @param edit
	 * @return
	 */
	protected String getTxt(EditText edit){
		if (TextUtils.isEmpty(edit.getText().toString()))
			return " ";
		return edit.getText().toString();
	}
	
	protected void showLongTxt(String info){
		Toast.makeText(this, info, Toast.LENGTH_LONG).show();
	}
	
	protected void showTxt(String info) {
		Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
	}
}
