package com.tchappy.photo.activity;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.tchappy.photo.adapter.HistoryBusinessAdapter;
import com.tchappy.photo.data.HistoryBusinessEntity;
import com.tchappy.photo.fragment.BaseFragmentActivity;
import com.tchappy.photo.http.JsonHttpResponseHandler;
import com.tchappy.photo.thread.SspanApplication;
import com.tchappy.pwy.R;

public class HistoryMoneyResultActivity extends BaseFragmentActivity {

	private PullToRefreshListView m_listView;
	private HistoryBusinessAdapter m_adapter;
	private List<HistoryBusinessEntity> m_data = new ArrayList<HistoryBusinessEntity>();
	
	private TextView txt_dataNum;
	
	private String startTime;
	private String endTime;
	private String uName = "";
	private String uPhone = "";
	
	private int totalNum = 0;
	private int pageSize = 1;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		Intent intent = getIntent();
		if (intent != null)
		{
			startTime = intent.getStringExtra("his_startTime");
			endTime = intent.getStringExtra("his_endTime");
			uName = intent.getStringExtra("his_uName");
			uPhone = intent.getStringExtra("his_uPhone");
		}
		
		setContentView(R.layout.activity_listview_view);
		
		final Button btnBack = (Button) findViewById(R.id.top_bar_back);
		final TextView txtTitle = (TextView) findViewById(R.id.top_bar1_title);
		txt_dataNum = (TextView) findViewById(R.id.data_result_num);
		
		txtTitle.setText(getString(R.string.query_item27_str));
		
		m_listView = (PullToRefreshListView) findViewById(R.id.mylistview);
		m_listView.setMode(Mode.BOTH);
		initRefresh();
		
		ReqQueryHistoryList(pageSize);
		m_listView.setOnRefreshListener(m_OnRefreshListener2);
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	
	
	
	/**
	 * 请求历史佣金数据
	 * @param pages
	 */
	private void ReqQueryHistoryList(int pages) {
		SspanApplication.asyncApi.ReqHistoryList(SspanApplication.SID, startTime, endTime, uName, uPhone, pages+"", jsonResponseHandler);
	}
	
	
	private JsonHttpResponseHandler jsonResponseHandler = new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			HistoryBusinessEntity history = new HistoryBusinessEntity(response);
			if (history.isStatus()){
				m_data = history.getList();
				if (m_data != null){
					totalNum = history.totalNum;
					pageSize = history.pageSize;
					txt_dataNum.setText("查询结果("+totalNum+"笔)");
					m_adapter = new HistoryBusinessAdapter(HistoryMoneyResultActivity.this, m_data);
					m_listView.setAdapter(m_adapter);
					m_adapter.notifyDataSetChanged();
					hintKbTwo();
				}else{
					txt_dataNum.setText("查询结果0笔)");
					showTxt("未查询到历史业务");
				}
			}else{
				showTxt("未查询到历史业务");
			}
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
	
	private OnRefreshListener2<ListView> m_OnRefreshListener2 = new OnRefreshListener2<ListView>() {

		// 向下刷新数据 - 
		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			// TODO Auto-generated method stub
			m_adapter.notifyDataSetChanged();
			m_listView.onRefreshComplete();
		}

		// 向上加载数据 - 刷新
		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			// TODO Auto-generated method stub
			m_adapter.notifyDataSetChanged();
			m_listView.onRefreshComplete();
			if(totalNum / 100 == 0)
			{
				pageSize = 1;
			}else{
				pageSize = (int)(totalNum / 100);
			}
			
			ReqQueryHistoryList(pageSize);
		}
	};
}
