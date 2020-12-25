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
import com.tchappy.photo.adapter.TodayBusinessAdapter;
import com.tchappy.photo.data.TodaysEntity;
import com.tchappy.photo.fragment.BaseFragmentActivity;
import com.tchappy.photo.http.JsonHttpResponseHandler;
import com.tchappy.photo.thread.SspanApplication;
import com.tchappy.pwy.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TodayCommMoneyActivity extends BaseFragmentActivity {

	private PullToRefreshListView m_listView;
	private TodayBusinessAdapter m_adapter;
	private List<TodaysEntity> m_data = new ArrayList<TodaysEntity>();
	
	private TextView txt_dataNum;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		setContentView(R.layout.activity_5_2_1);
		
		final Button btnBack = (Button) findViewById(R.id.top_bar_back);
		final TextView txtTitle = (TextView) findViewById(R.id.top_bar1_title);
		
		txtTitle.setText(getString(R.string.query_title_str));
		txt_dataNum = (TextView) findViewById(R.id.todays_data_num);
		
		m_listView = (PullToRefreshListView) findViewById(R.id.mylistview);
		m_listView.setMode(Mode.BOTH);
		initRefresh();
		
		ReqQueryTodayList(SspanApplication.SID);
		m_listView.setOnRefreshListener(m_OnRefreshListener2);
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	
	private void ReqQueryTodayList(String sealerid){
		SspanApplication.asyncApi.ReqQueryTodayList(sealerid, jsonResponseHandler);
	}
	
	private JsonHttpResponseHandler jsonResponseHandler = new JsonHttpResponseHandler()
	{

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			TodaysEntity todays = new TodaysEntity(response);
			if (todays.isStatus()){
				m_data = todays.getList();
				if (m_data != null){
				txt_dataNum.setText("查询结果("+m_data.size()+"笔)");
				m_adapter = new TodayBusinessAdapter(TodayCommMoneyActivity.this, m_data);
				m_listView.setAdapter(m_adapter);
				m_adapter.notifyDataSetChanged();
				hintKbTwo();
				}else{
					txt_dataNum.setText("查询结果0笔)");
					showTxt("今日未办理业务");
				}
			}else{
				showTxt("今日未办理业务");
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
}
