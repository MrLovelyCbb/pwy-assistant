package com.tchappy.photo.adapter;

import java.util.List;

import com.tchappy.photo.data.SellersEntity;


import com.tchappy.pwy.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class QuerySellersAdapter extends BaseAdapter {

	private Context m_context;
	private List<SellersEntity> m_list;
	protected LayoutInflater mInflater;
	
	public QuerySellersAdapter(Context context,List<SellersEntity> m_list) {
		this.m_context=context;
		mInflater = LayoutInflater.from(m_context);
		this.m_list=m_list;
	}

	@Override
	public int getCount() {
		if(m_list!=null)
			return m_list.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		if(m_list!=null)
			return m_list.get(position);
		else
			return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null){
			view = mInflater.inflate(R.layout.activity_5_1_item,null);
			SellersHolder holder = new SellersHolder();
			holder.seller_Uname = (TextView) view.findViewById(R.id.sellers_item_name);
			holder.seller_Uphone = (TextView) view.findViewById(R.id.sellers_item_phone);
			holder.seller_Umoney = (TextView) view.findViewById(R.id.sellers_item_money);
			holder.seller_Viewnext = (TextView) view.findViewById(R.id.sellers_item_next);
			view.setTag(holder);
		}else{
			view = convertView;
		}
		SellersHolder holder = (SellersHolder) view.getTag();
		SellersEntity sellers = (SellersEntity) m_list.get(position);
		if (holder != null){
			holder.seller_Uname.setText(sellers.uName);
			holder.seller_Uphone.setText(sellers.uPhone);
			holder.seller_Umoney.setText("￥"+sellers.uMoney);
//			holder.seller_Viewnext.setOnClickListener(l);
		}
		return view;
	}

	public class SellersHolder{
		TextView seller_Uname;
		TextView seller_Uphone;
		TextView seller_Umoney;
		TextView seller_Viewnext;
	}
}
