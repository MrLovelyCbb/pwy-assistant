package com.tchappy.photo.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.tchappy.photo.adapter.MoneyHistoryAdapter;
import com.tchappy.photo.adapter.YongJListAdapter;
import com.tchappy.photo.data.BasesEntity;
import com.tchappy.photo.data.SellersEntity;
import com.tchappy.photo.data.YjongEntity;
import com.tchappy.photo.fragment.BaseFragmentActivity;
import com.tchappy.photo.http.JsonHttpResponseHandler;
import com.tchappy.photo.thread.SspanApplication;
import com.tchappy.pwy.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class CommAccountManagerActivity extends BaseFragmentActivity {

	private TextView tv_account;
	private TextView tv_Aname;
	
	private YjongEntity yjongEntity;
	
	private PullToRefreshListView m_listView;
	private YongJListAdapter m_adapter;
	private List<YjongEntity> m_data = new ArrayList<YjongEntity>();
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		setContentView(R.layout.comm_account_manager);
		
		final Button btnBack = (Button) findViewById(R.id.top_bar_back);
		final TextView txtTitle = (TextView) findViewById(R.id.top_bar1_title);
		final Button btnAdd = (Button) findViewById(R.id.btn_add);
		
		tv_account = (TextView) findViewById(R.id.comm_account_default);
		tv_Aname = (TextView) findViewById(R.id.txt_account_name);
		
		m_listView = (PullToRefreshListView) findViewById(R.id.mylistview);
		m_listView.setMode(Mode.BOTH);
		initRefresh();
		m_listView.setOnItemClickListener(m_listView_onItemClickListener);
		m_listView.setOnRefreshListener(m_OnRefreshListener2);
		

		// 发送请求
		ReqYJAccountList();
		
		txtTitle.setText(getString(R.string.login_title_manager));
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btnAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CommAccountManagerActivity.this, AddAccountManagerActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private OnItemClickListener m_listView_onItemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long id) {
			System.out.println("Position--------------------"+position);
			yjongEntity = m_data.get(position-1);
			if (yjongEntity == null)
				return;
			
			ReqModifyAccount(yjongEntity.getId()+"",yjongEntity.getUname(),yjongEntity.getAccount());
		}
	};
	
	private void ReqModifyAccount(String yjmoneyid,String uName,String uAccount){
		SspanApplication.asyncApi.ReqModifyAccount(SspanApplication.SID, yjmoneyid, uName, uAccount, "1", new JsonHttpResponseHandler()
		{

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				BasesEntity status = new BasesEntity(response);
				if (status.isStatus()){
					showTxt("设置默认账号成功");
				}else if (status.getStatusCode() == 1){
					showTxt("用户不存在");
				}else if (status.getStatusCode() == 2){
					showTxt("该支付账号已是默认账号");
				}
			}
			
		});
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
				m_data = yjongEntity.getList();
				if (m_data != null && m_data.size() > 0){
					for (YjongEntity yj : m_data){
						if (yj.getIs_default().equals("Y")){
							tv_Aname.setText(yj.getUname());
							tv_account.setText(yj.getAccount());
						}
					}
					m_adapter = new YongJListAdapter(CommAccountManagerActivity.this, m_data, btn1OnClickListener,btn2_OnClickListener);
					m_listView.setAdapter(m_adapter);
					m_adapter.notifyDataSetChanged();
				}else{
					showTxt("没有数据...");
				}
			}
		}
		
	};
	
	private View.OnClickListener btn1OnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			int postition = (Integer) v.getTag();
			yjongEntity = m_data.get(postition);
			if (yjongEntity == null)
				return;
			
			Dialog dialog = new AlertDialog.Builder(CommAccountManagerActivity.this).setTitle("提示").setMessage("您是否要将 “"+yjongEntity.getUname()+"” 此账号为默认账号？")
					// 设置内容
							.setPositiveButton("返回", null).setNegativeButton("确定", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int whichButton) {
									ReqModifyAccount(yjongEntity.getId()+"",yjongEntity.getUname(),yjongEntity.getAccount());
								}
							}).create();// 创建
					// 显示对话框
					dialog.show();
		}
	};
	
	private View.OnClickListener btn2_OnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			int postition = (Integer) v.getTag();
			yjongEntity = m_data.get(postition);
			if (yjongEntity == null)
				return;
			
			Dialog dialog = new AlertDialog.Builder(CommAccountManagerActivity.this).setTitle("提示").setMessage("您是否要 “"+yjongEntity.getUname()+"” 删除此账号！")
					// 设置内容
							.setPositiveButton("返回", null).setNegativeButton("确定", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int whichButton) {
									ReqDelAccount(yjongEntity.getId()+"");
								}
							}).create();// 创建
					// 显示对话框
			dialog.show();
		}
	};
	
	/**
	 * 删除佣金账号
	 * @param yjmoneyid
	 */
	private void ReqDelAccount(String yjmoneyid){
		SspanApplication.asyncApi.ReqDelAccount(SspanApplication.SID, yjmoneyid, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				BasesEntity status = new BasesEntity(response);
				if (status.isStatus()){
					showTxt("删除成功");
				}else if (status.getStatusCode() == 2){
					showTxt("不能删除默认账号");
				}else if (status.getStatusCode() == 1){
					showTxt("用户不存在");
				}
			}
			
		});
	}
	
	private OnRefreshListener2<ListView> m_OnRefreshListener2 = new OnRefreshListener2<ListView>() {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			// TODO Auto-generated method stub
			m_adapter.notifyDataSetChanged();
			m_listView.onRefreshComplete();
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			// TODO Auto-generated method stub
			m_adapter.notifyDataSetChanged();
			m_listView.onRefreshComplete();
		}
	};
	
	private void initRefresh()
	{
		ILoadingLayout startLabels = m_listView    
                .getLoadingLayoutProxy(true, false);    
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示    
        startLabels.setRefreshingLabel("正在载入...");// 刷新时    
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示    
    
        ILoadingLayout endLabels = m_listView.getLoadingLayoutProxy(    
                false, true);    
        endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示    
        endLabels.setRefreshingLabel("正在载入...");// 刷新时    
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示    
	}
}
