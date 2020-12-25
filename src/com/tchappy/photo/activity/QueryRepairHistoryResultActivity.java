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
import com.tchappy.photo.adapter.HistoryRepairAdapter;
import com.tchappy.photo.data.HistroyRepairEntity;
import com.tchappy.photo.fragment.BaseFragmentActivity;
import com.tchappy.photo.http.JsonHttpResponseHandler;
import com.tchappy.photo.thread.SspanApplication;
import com.tchappy.pwy.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class QueryRepairHistoryResultActivity extends BaseFragmentActivity {
	
	private PullToRefreshListView m_listView;
	private HistoryRepairAdapter m_adapter;
	private List<HistroyRepairEntity> m_data = new ArrayList<HistroyRepairEntity>();
	
	private String startTime;
	private String endTime;
	private String uName = "";
	private String uPhone = "";
	
	private int pages;
	private int totalNum;
	
	private TextView txt_startEndTime;
	private TextView txt_dataNum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		if (intent != null){
			startTime = intent.getStringExtra("repair_startTime");
			endTime = intent.getStringExtra("repair_endTime");
			uName = intent.getStringExtra("repair_uName");
			uPhone = intent.getStringExtra("repair_uPhone");
		}
		
		setContentView(R.layout.activity_5_2_2_1);
		
		final Button btnBack = (Button) findViewById(R.id.top_bar_back);
		final TextView txtTitle = (TextView) findViewById(R.id.top_bar1_title);
		
		txt_startEndTime = (TextView) findViewById(R.id.a5_2_1_time);
		txt_dataNum = (TextView) findViewById(R.id.a5_2_1_datanum);
		
		m_listView = (PullToRefreshListView) findViewById(R.id.mylistview);
		m_listView.setMode(Mode.BOTH);
		initRefresh();
		
		ReqRepairHistoryList(1);
		
		m_listView.setOnRefreshListener(m_OnRefreshListener2);
		
		txtTitle.setText(getString(R.string.query_menu3_str));
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void ReqRepairHistoryList(int pages){
		SspanApplication.asyncApi.ReqRepairHistoryList(SspanApplication.SID, startTime, endTime, uName, uPhone, pages+"", jsonResponseHandler);
	}

	private JsonHttpResponseHandler jsonResponseHandler = new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			HistroyRepairEntity repair = new HistroyRepairEntity(response);
			if (repair.isStatus() && repair.getTotalNum() > 0){
				m_data = repair.getList();
				m_adapter = new HistoryRepairAdapter(QueryRepairHistoryResultActivity.this, m_data);
				
				txt_startEndTime.setText(repair.getAdd_time() + " 至 " + repair.getKd_time());
				txt_dataNum.setText("查询结果("+repair.getTotalNum()+"笔)");
				
				m_listView.setAdapter(m_adapter);
				m_adapter.notifyDataSetChanged();
				hintKbTwo();
			}else{
				txt_startEndTime.setText(startTime + " 至 " + endTime);
				txt_dataNum.setText("查询结果(0笔)");
				showTxt("未查询到报修历史");
			}
		}
		
	};
	
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
