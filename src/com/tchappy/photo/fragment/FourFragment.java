package com.tchappy.photo.fragment;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.tchappy.photo.activity.AboutActivity;
import com.tchappy.photo.activity.AccountViewActivity;
import com.tchappy.photo.activity.CommAccountManagerActivity;
import com.tchappy.photo.activity.LoginActivity;
import com.tchappy.photo.activity.ModifyPwdActivity;
import com.tchappy.photo.adapter.YongJListAdapter;
import com.tchappy.photo.data.YjongEntity;
import com.tchappy.photo.http.JsonHttpResponseHandler;
import com.tchappy.photo.thread.SspanApplication;
import com.tchappy.photo.view.TitleView;
import com.tchappy.photo.view.TitleView.OnLeftButtonClickListener;
import com.tchappy.photo.view.TitleView.OnRightButtonClickListener;
import com.tchappy.pwy.R;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author yangyu
 *	功能描述：设置fragment页面
 */
public class FourFragment extends BaseFragment implements OnClickListener{
	
	private View mParent;
	private FragmentActivity mActivity;
	
	private TextView m_Title;
	private ImageView m_Image;
	private Button m_Button;
	
	private TextView m_notice;

	/**
	 * Create a new instance of DetailsFragment, initialized to show the text at
	 * 'index'.
	 */
	public static FourFragment newInstance(int index) {
		FourFragment f = new FourFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);

		return f;
	}

	public int getShownIndex() {
		return getArguments().getInt("index", 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_four, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mParent = getView();
		mActivity = getActivity();

		m_Title = (TextView) mParent.findViewById(R.id.top_bar_title);
		m_Title.setText(R.string.my_title_str);
		
		m_Image = (ImageView) mParent.findViewById(R.id.top_bar_left);
		
		m_Button = (Button) mParent.findViewById(R.id.top_bar_right);
		
		m_notice = (TextView) mParent.findViewById(R.id.comm_account_notice);
		
		m_Image.setVisibility(View.INVISIBLE);
		m_Button.setVisibility(View.INVISIBLE);
		
		final TextView accountView = (TextView) mParent.findViewById(R.id.my_account_view);
		final TextView modifyView = (TextView) mParent.findViewById(R.id.modify_pwd_view);
		final LinearLayout commView = (LinearLayout) mParent.findViewById(R.id.comm_account_view);
		final TextView logoutView = (TextView) mParent.findViewById(R.id.logout_comm);
		final TextView exitView = (TextView) mParent.findViewById(R.id.exit_comm);
		final TextView abountView = (TextView) mParent.findViewById(R.id.abount_view);
		
		accountView.setOnClickListener(this);
		modifyView.setOnClickListener(this);
		commView.setOnClickListener(this);
		logoutView.setOnClickListener(this);
		exitView.setOnClickListener(this);
		abountView.setOnClickListener(this);
		
		ReqYJAccountList();
	}
	
	private void ReqYJAccountList(){
		SspanApplication.asyncApi.ReqYJAccountList(SspanApplication.SID, YJList_JsonResHandler);
	}

	private JsonHttpResponseHandler YJList_JsonResHandler = new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			YjongEntity yjongEntity = new YjongEntity(response);
			if (yjongEntity.isStatus()){
				List<YjongEntity> m_data = yjongEntity.getList();
				if (m_data != null && m_data.size() > 0){
					m_notice.setText("");
					m_notice.setTextColor(getResources().getColor(R.color.white));
				}else{
					m_notice.setText("您还未添加收款账号");
					m_notice.setTextColor(getResources().getColor(R.color.red));
				}
			}
		}
		
	};

	private Intent intent = null;
	@Override
	public void onClick(View v) {
		intent = new Intent(mActivity, LoginActivity.class);
		switch (v.getId()) {
		case R.id.my_account_view:
			intent = new Intent(mActivity, AccountViewActivity.class);
			break;
		case R.id.modify_pwd_view:
			intent = new Intent(mActivity, ModifyPwdActivity.class);
			break;
		case R.id.comm_account_view:
			intent = new Intent(mActivity, CommAccountManagerActivity.class);
			break;
		case R.id.logout_comm:
			Dialog dialog = new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("是否要注销当前账号？")
			// 设置内容
					.setPositiveButton("返回", null).setNegativeButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							SspanApplication.mPref.edit().clear().commit();
							SspanApplication.Is_AutoLogin = false;
							intent = new Intent(mActivity, LoginActivity.class);
							System.out.println("logout");
							startActivity(intent);
							getActivity().finish();
						}
					}).create();// 创建
			// 显示对话框
			dialog.show();
			return;
		case R.id.exit_comm:
			Dialog dialog1 = new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("是否要退出当前应用？")
			// 设置内容
					.setPositiveButton("返回", null).setNegativeButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							System.exit(0);
						}
					}).create();// 创建
			// 显示对话框
			dialog1.show();
			return;
		case R.id.abount_view:
			intent = new Intent(mActivity, AboutActivity.class);
			break;

		default:
			break;
		}
		
		startActivity(intent);
	}

}
