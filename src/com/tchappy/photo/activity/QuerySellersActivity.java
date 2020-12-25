package com.tchappy.photo.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tchappy.photo.adapter.QuerySellersAdapter;
import com.tchappy.photo.data.SellersEntity;
import com.tchappy.photo.fragment.BaseFragmentActivity;
import com.tchappy.photo.http.JsonHttpResponseHandler;
import com.tchappy.photo.thread.SspanApplication;
import com.tchappy.pwy.R;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class QuerySellersActivity extends BaseFragmentActivity {

	private PullToRefreshListView m_listView;
	private QuerySellersAdapter m_adapter;
	private List<SellersEntity> m_data = new ArrayList<SellersEntity>();
	
	private TextView sellers_level;
	private TextView sellers_num;
	private TextView sellers_count_money;
	
	private EditText edit_serach;
	
	private int top_level = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_5_1);

		final Button btnBack = (Button) findViewById(R.id.top_bar_back);
		final TextView txtTitle = (TextView) findViewById(R.id.top_bar1_title);
		
		txtTitle.setText(getString(R.string.query_menu_str));
		
		edit_serach = (EditText) findViewById(R.id.a5_1_search);
		edit_serach.addTextChangedListener(txtwatcher);
		
		sellers_level = (TextView) findViewById(R.id.sellers_level);
		sellers_num = (TextView) findViewById(R.id.sellers_num);
		sellers_count_money = (TextView) findViewById(R.id.sellers_count_money);
		
		m_listView = (PullToRefreshListView) findViewById(R.id.mylistview);
		m_listView.setMode(Mode.BOTH);
		initRefresh();
		
		ReqQuerySellersList(SspanApplication.SID,getTxt(edit_serach),1);
		
		m_listView.setOnRefreshListener(m_OnRefreshListener2);
		m_listView.setOnItemClickListener(listView_OnItemClickListener);
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

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
	
	private TextWatcher txtwatcher = new TextWatcher() {
		private CharSequence temp;  
        private int editStart;  
        private int editEnd; 
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			temp = s;
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			editStart = edit_serach.getSelectionStart();  
            editEnd = edit_serach.getSelectionEnd();
            if (temp.length() >= 12) {
                s.delete(editStart-1, editEnd);  
                int tempSelection = editStart;  
                edit_serach.setText(s);
                edit_serach.setSelection(tempSelection);
            }  
		}
	};
	
	private void ReqQuerySellersList(String sid,String phone,int page)
	{
		SspanApplication.asyncApi.ReqQuerySalerList(sid, phone, page+"", jsonResponseHandler);
	}
	
	private OnItemClickListener listView_OnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long id) {
			System.out.println("Position--------------------"+position);
			SellersEntity sellers = (SellersEntity)m_data.get(position-1);
			ReqQuerySellersList(sellers.uId+"",getTxt(edit_serach),1);
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
	
	private JsonHttpResponseHandler jsonResponseHandler = new JsonHttpResponseHandler()
	{
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			SellersEntity sellers = new SellersEntity(response);
			if (sellers.isStatus() && sellers.getDataNumber() > 0){
				m_data = sellers.getList();
				m_adapter = new QuerySellersAdapter(QuerySellersActivity.this, m_data);
				
				// 底部数据
				sellers_level.setText(getLevelStr(top_level));
				sellers_num.setText(sellers.dataNumber+"人");
				sellers_count_money.setText("￥"+sellers.moneyCount);
				
				m_listView.setAdapter(m_adapter);
				m_adapter.notifyDataSetChanged();
				hintKbTwo();
			}else{
				Toast.makeText(QuerySellersActivity.this, "无下线直销员数据", Toast.LENGTH_SHORT).show();
			}
		}
		
	};
	
	private String getLevelStr(int topLevel)
	{
		switch (topLevel) {
		case 1:
			return "一级下线直销员";
		case 2:
			return "二级下线直销员";
		case 3:
			return "三级下线直销员";
		case 4:
			return "四级下线直销员";
		case 5:
			return "五级下线直销员";
		case 6:
			return "六级下线直销员";
		case 7:
			return "七级下线直销员";
		case 8:
			return "八级下线直销员";
		case 9:
			return "九级下线直销员";
		case 10:
			return "十级下线直销员";
		default:
			return "一级下线直销员";
		}
	}
}
