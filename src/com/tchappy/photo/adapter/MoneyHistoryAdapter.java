package com.tchappy.photo.adapter;

import java.util.List;

import com.tchappy.photo.data.MoneyHistoryEntity;
import com.tchappy.pwy.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MoneyHistoryAdapter extends BaseAdapter {

	private Context m_context;
	private List<MoneyHistoryEntity> m_list;
	protected LayoutInflater mInflater;
	
	public MoneyHistoryAdapter(Context context,List<MoneyHistoryEntity> m_list) {
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
			view = mInflater.inflate(R.layout.activity_today_item,null);
			MoneysHolder holder = new MoneysHolder();
			holder.moneys_Uname = (TextView) view.findViewById(R.id.today_item_name);
			holder.moneys_Uphone = (TextView) view.findViewById(R.id.today_item_phone);
			holder.moneys_Service = (TextView) view.findViewById(R.id.today_item_service);
			holder.moneys_Time = (TextView) view.findViewById(R.id.today_item_time);
			view.setTag(holder);
		}else{
			view = convertView;
		}
		MoneysHolder holder = (MoneysHolder) view.getTag();
		MoneyHistoryEntity moneys = (MoneyHistoryEntity) m_list.get(position);
		if (holder != null){
			holder.moneys_Uname.setText(moneys.cb_money);
			holder.moneys_Uphone.setText(moneys.getMoney_all());
			holder.moneys_Service.setText(moneys.getCb_money());
			holder.moneys_Time.setText(moneys.tc_money);
		}
		return view;
	}

	public class MoneysHolder{
		TextView moneys_Uname;
		TextView moneys_Uphone;
		TextView moneys_Service;
		TextView moneys_Time;
	}
}
