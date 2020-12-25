package com.tchappy.photo.adapter;

import java.util.List;

import com.tchappy.photo.data.HistoryBusinessEntity;
import com.tchappy.photo.data.TodaysEntity;
import com.tchappy.pwy.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HistoryBusinessAdapter extends BaseAdapter {

	private Context m_context;
	private List<HistoryBusinessEntity> m_list;
	protected LayoutInflater mInflater;
	
	public HistoryBusinessAdapter(Context context,List<HistoryBusinessEntity> m_list) {
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
			TodaysHolder holder = new TodaysHolder();
			holder.his_Uname = (TextView) view.findViewById(R.id.today_item_name);
			holder.his_Uphone = (TextView) view.findViewById(R.id.today_item_phone);
			holder.his_Service = (TextView) view.findViewById(R.id.today_item_service);
			holder.his_Time = (TextView) view.findViewById(R.id.today_item_time);
			view.setTag(holder);
		}else{
			view = convertView;
		}
		TodaysHolder holder = (TodaysHolder) view.getTag();
		HistoryBusinessEntity todadys = (HistoryBusinessEntity) m_list.get(position);
		if (holder != null){
			holder.his_Uname.setText(todadys.uName);
			holder.his_Uphone.setText(todadys.uPhone);
			holder.his_Service.setText(todadys.service);
			holder.his_Time.setText((todadys.time).substring(0, 10));
		}
		return view;
	}

	public class TodaysHolder{
		TextView his_Uname;
		TextView his_Uphone;
		TextView his_Service;
		TextView his_Time;
	}
}
