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
import com.tchappy.photo.data.MoneyHistoryEntity;
import com.tchappy.photo.fragment.BaseFragmentActivity;
import com.tchappy.photo.http.JsonHttpResponseHandler;
import com.tchappy.photo.thread.SspanApplication;
import com.tchappy.photo.view.TabTextView;
import com.tchappy.pwy.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class QueryMoneyHistoryResultActivity extends BaseFragmentActivity{
	
	private PullToRefreshListView m_listView;
	private MoneyHistoryAdapter m_adapter;
	private List<MoneyHistoryEntity> m_data = new ArrayList<MoneyHistoryEntity>();
	
	private String starTime;
	private String endTime;
	private String moneyFive;
	private String highMoney;
	
	private TabTextView txt_his_all;
	private TabTextView txt_his_bl;
	private TabTextView txt_his_tc;
	private TabTextView txt_his_jl;
	
	private int types = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		if (intent != null){
			starTime = intent.getStringExtra("QMHRA_startTime");
			endTime = intent.getStringExtra("QMHRA_endTime");
			moneyFive = intent.getStringExtra("QMHRA_moneyFive");
			highMoney = intent.getStringExtra("QMHRA_highMoney");
		}
		
		setContentView(R.layout.query_money_history);
		
		final Button btnBack = (Button) findViewById(R.id.top_bar_back);
		final TextView txtTitle = (TextView) findViewById(R.id.top_bar1_title);
		
		txt_his_all = (TabTextView) findViewById(R.id.query_money_his_all);
		txt_his_bl = (TabTextView) findViewById(R.id.query_money_his_blyj);
		txt_his_tc = (TabTextView) findViewById(R.id.query_money_his_tcyj);
		txt_his_jl = (TabTextView) findViewById(R.id.query_money_his_jlyj);
		
		m_listView = (PullToRefreshListView) findViewById(R.id.mylistview);
		m_listView.setMode(Mode.BOTH);
		initRefresh();
		
		ReqQueryResultHistory(types);
		
		m_listView.setOnRefreshListener(m_OnRefreshListener2);
		
		txt_his_all.setOnClickListener(btnSelected_OnClickListener);
		txt_his_bl.setOnClickListener(btnSelected_OnClickListener);
		txt_his_tc.setOnClickListener(btnSelected_OnClickListener);
		txt_his_jl.setOnClickListener(btnSelected_OnClickListener);
		
		txtTitle.setText(getString(R.string.query_menu2_str));

		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	/**
	 * 1.全部佣金<br>
	 * 2.办理佣金<br>
	 * 3.提成佣金<br>
	 * 4.奖励佣金<br>
	 * @param types
	 */
	private void ReqQueryResultHistory(int types)
	{
		SspanApplication.asyncApi.ReqQueryResultHistory(SspanApplication.SID, starTime, endTime, moneyFive, highMoney, types + "", jsonResponseHandler);
	}
	
	private JsonHttpResponseHandler jsonResponseHandler = new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			MoneyHistoryEntity money = new MoneyHistoryEntity(response);
			if (money.isStatus()){
				m_data = money.getList();
				if (m_data != null){
					m_adapter = new MoneyHistoryAdapter(QueryMoneyHistoryResultActivity.this, m_data);
					
					m_listView.setAdapter(m_adapter);
					m_adapter.notifyDataSetChanged();
					hintKbTwo();
				}else{
					showTxt("未查询到已结算佣金");
				}
			}else{
				showTxt("未查询到已结算佣金");
			}
		}
		
	};
	
	private View.OnClickListener btnSelected_OnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.query_money_his_all:
				types = 1;
				txt_his_all.setImgVisible(View.VISIBLE);
				txt_his_bl.setImgVisible(View.INVISIBLE);
				txt_his_tc.setImgVisible(View.INVISIBLE);
				txt_his_jl.setImgVisible(View.INVISIBLE);
				break;
			case R.id.query_money_his_blyj:
				types = 2;
				txt_his_all.setImgVisible(View.INVISIBLE);
				txt_his_bl.setImgVisible(View.VISIBLE);
				txt_his_tc.setImgVisible(View.INVISIBLE);
				txt_his_jl.setImgVisible(View.INVISIBLE);
				break;
			case R.id.query_money_his_tcyj:
				types = 3;
				txt_his_all.setImgVisible(View.INVISIBLE);
				txt_his_bl.setImgVisible(View.INVISIBLE);
				txt_his_tc.setImgVisible(View.VISIBLE);
				txt_his_jl.setImgVisible(View.INVISIBLE);
				break;
			case R.id.query_money_his_jlyj:
				types = 4;
				txt_his_all.setImgVisible(View.INVISIBLE);
				txt_his_bl.setImgVisible(View.INVISIBLE);
				txt_his_tc.setImgVisible(View.INVISIBLE);
				txt_his_jl.setImgVisible(View.VISIBLE);
				break;
			default:
				types = 1;
				break;
			}
			
			ReqQueryResultHistory(types);
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
