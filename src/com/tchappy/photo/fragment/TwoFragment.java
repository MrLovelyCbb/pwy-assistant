package com.tchappy.photo.fragment;

import org.apache.http.Header;
import org.json.JSONObject;

import com.tchappy.photo.activity.RepairOrderListActivity;
import com.tchappy.photo.activity.RepairQueryResultActivity;
import com.tchappy.photo.data.BasesEntity;
import com.tchappy.photo.http.JsonHttpResponseHandler;
import com.tchappy.photo.thread.SspanApplication;
import com.tchappy.photo.view.SegmentedGroup;
import com.tchappy.pwy.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class TwoFragment extends BaseFragment{

	private View mParent;
	private FragmentActivity mActivity;
	
	private View tabView_1 = null;
	private View tabView_2 = null;
	
	private SegmentedGroup segmented;
	private RadioButton fristRadioBtn;
	private RadioButton secondRadioBtn;
	private FrameLayout frameContent;
	private TextView txtRepairNum;
	
	// tabView_1
	private EditText Uname;
	private EditText Uphone;
	
	// tabView_2
	private EditText Uname1;
	private EditText UPhone1;
	private String Uphoto1;
	private String Uphoto2;
	
	public static TwoFragment newInstance(int index) {
		TwoFragment f = new TwoFragment();
		
		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);
		
		return f;
	}
	
	public int getShowIndex() {
		return getArguments().getInt("index",0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(mParent==null){
			mParent = inflater.inflate(R.layout.fragment_two, container, false);
			tabView_1 =inflater.inflate(R.layout.repair_query_view,container,false);
			tabView_2 = inflater.inflate(R.layout.repair_nobuyquery_view,container,false);
        }
        ViewGroup parent = (ViewGroup) mParent.getParent();
        if (parent != null) {
            parent.removeView(mParent);
        }
        return mParent;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActivity = getActivity();
		mParent = getView();
		
		segmented = (SegmentedGroup) mParent.findViewById(R.id.segmented_topbar);
		segmented.setTintColor(0xFF1b7ce8);
		
		fristRadioBtn = (RadioButton) mParent.findViewById(R.id.topbar_1);
		secondRadioBtn = (RadioButton) mParent.findViewById(R.id.topbar_2);
//		defaultRadioBtn.setChecked(true);
		frameContent = (FrameLayout)mParent.findViewById(R.id.mycontent);
//		final TextView txtView = (TextView) mParent.findViewById(R.id.frame_text);    // 调试信息
		
		Uname = (EditText) tabView_1.findViewById(R.id.repair_name);
		Uphone = (EditText) tabView_1.findViewById(R.id.repair_phone);
		
		
		final Button btnQuery = (Button) tabView_1.findViewById(R.id.btn_query);
		final Button btnQueryMoney = (Button) tabView_2.findViewById(R.id.btn_query_money);
		
		final Button btnQueryRepairMoney = (Button) mParent.findViewById(R.id.topbar_corner_btn);
		txtRepairNum = (TextView) mParent.findViewById(R.id.topbar_corner_num);
		
		frameContent.addView(tabView_2, 0);
		frameContent.addView(tabView_1,1);
		
		// 设置默认隐藏
		frameContent.getChildAt(0).setVisibility(View.INVISIBLE);
		
		txtRepairNum.setVisibility(View.INVISIBLE);
		btnQueryRepairMoney.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// if == 0 
				txtRepairNum.setVisibility(View.INVISIBLE);
				txtRepairNum.setText(0+"");
				goRepairOrderListActivity();
			}
		});
		
		btnQuery.setOnClickListener(new View.OnClickListener() {
			
			
			
			@Override
			public void onClick(View v) {
				
				goRepairResultActivity();
				
			}
		});
		
		btnQueryMoney.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Dialog dialog = new AlertDialog.Builder(mActivity).setTitle("").setMessage("您的报修询价已发送，专业人员正在为您报价，请耐心等待...")
						// 设置内容
							.setNegativeButton("确定", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int whichButton) {
										
									}
								}).create();// 创建
						// 显示对话框
						dialog.show();
			}
		});
		
		segmented.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				long starTime = System.currentTimeMillis();
//				txtRepairNum.setVisibility(0);
				switch (checkedId) {
		        case R.id.topbar_1:
		        	System.out.println("topbar1");
		        	frameContent.getChildAt(0).setVisibility(View.INVISIBLE);
		        	frameContent.getChildAt(1).setVisibility(View.VISIBLE);
//		    	    txtView.setText("消耗时间：-------"+(System.currentTimeMillis() - starTime));
//		    	    txtRepairNum.setText(""+(System.currentTimeMillis() - starTime));
		            break;
		        case R.id.topbar_2:
		        	fristRadioBtn.setChecked(true);
		        	secondRadioBtn.setChecked(false);
		        	Dialog dialog = new AlertDialog.Builder(mActivity).setTitle("提示").setMessage("您查询的用户未购买屏无忧服务，可拨打4000-820-136报修")
					// 设置内容
							.setPositiveButton("确定", null).create();// 创建
					// 显示对话框
					dialog.show();
//		        	System.out.println("topbar2");
//		        	frameContent.getChildAt(1).setVisibility(View.INVISIBLE);
//		        	frameContent.getChildAt(0).setVisibility(View.VISIBLE);
//		    	    txtView.setText("消耗时间：-------"+(System.currentTimeMillis() - starTime));
//		    	    txtRepairNum.setText((System.currentTimeMillis() - starTime)+"");
		            break;
		    }
			}
			
		});
	}
	
	private void goRepairResultActivity() {
		if (isEmptyByComponents(Uname))
		{
			showTxt("请输入查询的姓名");
			return;
		}
		
		if (isEmptyByComponents(Uphone))
		{
			showTxt("请输入查询的手机号");
			return;
		}

		SspanApplication.asyncApi.ReqQueryCanBao(getTxt(Uname), getTxt(Uphone),new JsonHttpResponseHandler()
		{
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				BasesEntity status = new BasesEntity(response);
				if (status.getStatusCode() == 1)
				{
					showTxt("您查询的不存在");
					return;
				}else if (status.getStatusCode() == 2)
				{
					showTxt("您未参保...");
					return;
				}else if (status.getStatusCode() == 3)
				{
					showTxt("您的保单已过期，请稍微再试...");
					return;
				}else if (status.getStatusCode() == 0)
				{
					System.out.println("data////////////"+status.getData()+"info///////////"+status.getInfo()+"statasCode/////////////"+status.getStatus());
					Intent intent = new Intent(mActivity,RepairQueryResultActivity.class);
					intent.putExtra("data",status.getJsonStr());
					mActivity.startActivity(intent);
				}
				
				int i = 0;
				if (i == 2){
						Dialog dialog = new AlertDialog.Builder(mActivity).setTitle("查询结果").setMessage("您查询的用户未购买屏无忧服务，是否询问保修价格")
								// 设置内容
										.setPositiveButton("返回", null).setNegativeButton("确定", new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog, int whichButton) {
												secondRadioBtn.setChecked(true);
												frameContent.getChildAt(1).setVisibility(View.INVISIBLE);
									        	frameContent.getChildAt(0).setVisibility(View.VISIBLE);
											}
										}).create();// 创建
								// 显示对话框
								dialog.show();
				}
			}
			
		});
		
	}
	
	private void goRepairOrderListActivity() {
		Dialog dialog = new AlertDialog.Builder(mActivity).setTitle("提示").setMessage("您查询的用户未购买屏无忧服务，可拨打4000-820-136报修")
				// 设置内容
						.setPositiveButton("确定", null).create();// 创建
				// 显示对话框
				dialog.show();
//		Intent intent = new Intent(mActivity, RepairOrderListActivity.class);
//		mActivity.startActivity(intent);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
	}

	@Override
	public void onDestroy() {
		
		super.onDestroy();
	}
}
