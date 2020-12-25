package com.tchappy.photo.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;

import com.tchappy.photo.fragment.BaseFragmentActivity;
import com.tchappy.photo.view.FragmentIndicator;
import com.tchappy.photo.view.FragmentIndicator.OnIndicateListener;
import com.tchappy.pwy.R;

public class MainActivity extends BaseFragmentActivity{

	// 记录返回键按下的时间
	private long mExitTime;
	public static Fragment[] mFragments;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initFragmentIndicator(0);
		
	}

	
	/**
	 * 初始化fragment
	 */
	private void initFragmentIndicator(int whichIsDefault) {
		mFragments = new Fragment[4];
		mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.fragment_one);
		mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.fragment_two);
		mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.fragment_three);
		mFragments[3] = getSupportFragmentManager().findFragmentById(R.id.fragment_four);
		getSupportFragmentManager().beginTransaction().hide(mFragments[0])
				.hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).show(mFragments[whichIsDefault]).commit();

		FragmentIndicator mIndicator = (FragmentIndicator) findViewById(R.id.indicator);
		FragmentIndicator.setIndicator(whichIsDefault);
		mIndicator.setOnIndicateListener(new OnIndicateListener() {
			@Override
			public void onIndicate(View v, int which) {
				getSupportFragmentManager().beginTransaction()
						.hide(mFragments[0]).hide(mFragments[1])
						.hide(mFragments[2]).hide(mFragments[3]).show(mFragments[which]).commit();
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	// 在按一次返回登录界面
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 如果两次按键时间间隔大于2000毫秒，则不退出
			if ((System.currentTimeMillis() - mExitTime) > 1800) {
				showTxt("再按一次结束程序");
				// 更新mExitTime
				mExitTime = System.currentTimeMillis();
			} else {
				this.finish();
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
