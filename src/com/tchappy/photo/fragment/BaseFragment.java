package com.tchappy.photo.fragment;

import java.util.Vector;

import com.tchappy.photo.thread.SspanApplication;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class BaseFragment extends Fragment{
	
	protected FragmentActivity mActivity = null;
	protected Context mContext = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.mActivity = getActivity();
		this.mContext = SspanApplication.mContext;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	protected void hintKbTwo() {
		if (mActivity == null)
			return;
		
		 InputMethodManager imm = (InputMethodManager)mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);            
		 if(imm.isActive()&&mActivity.getCurrentFocus()!=null){
		    if (mActivity.getCurrentFocus().getWindowToken()!=null) {
		    imm.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
		Toast.makeText(this.mActivity != null ? this.mActivity : this.mContext, info, Toast.LENGTH_LONG).show();
	}
	
	protected void showTxt(String info) {
		Toast.makeText(this.mActivity != null ? this.mActivity : this.mContext, info, Toast.LENGTH_SHORT).show();
	}
	
	
	public FragmentActivity getmActivity() {
		return mActivity;
	}

	public Context getmContext() {
		return mContext;
	}

}
