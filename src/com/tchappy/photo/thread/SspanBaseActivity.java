package com.tchappy.photo.thread;

import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class SspanBaseActivity extends Activity{

	protected void hintKbTwo() {
		 InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);            
		 if(imm.isActive()&&getCurrentFocus()!=null){
		    if (getCurrentFocus().getWindowToken()!=null) {
		    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
		return edit.getText().toString();
	}
	
	protected void showLongTxt(String info){
		Toast.makeText(this, info, Toast.LENGTH_LONG).show();
	}
	
	protected void showTxt(String info) {
		Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
	}
}
