package com.tchappy.photo.adapter;

import java.util.List;

import com.tchappy.photo.data.HistroyRepairEntity;
import com.tchappy.pwy.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HistoryRepairAdapter extends BaseAdapter {

	private Context m_context;
	private List<HistroyRepairEntity> m_list;
	protected LayoutInflater mInflater;
	
	public HistoryRepairAdapter(Context context,List<HistroyRepairEntity> m_list) {
		this.m_context = context;
		this.mInflater = LayoutInflater.from(m_context);
		this.m_list = m_list;
	}

	@Override
	public int getCount() {
		if (m_list != null)
			return m_list.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		if (m_list != null)
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
			view = mInflater.inflate(R.layout.query_history_repair_item, null);
			HistoryRepairHolder holder = new HistoryRepairHolder();
			holder.his_Uname = (TextView) view.findViewById(R.id.a5_2_1_tv11);
			holder.his_Uphone = (TextView) view.findViewById(R.id.a5_2_1_tv12);
			holder.his_addTime = (TextView) view.findViewById(R.id.a5_2_1_addtime);
			holder.his_kdTime = (TextView) view.findViewById(R.id.a5_2_1_tv14);
		}else{
			view = convertView;
		}
		HistoryRepairHolder holder = (HistoryRepairHolder)view.getTag();
		HistroyRepairEntity repairs = (HistroyRepairEntity) m_list.get(position);
		if (holder != null){
			holder.his_Uname.setText(repairs.uName);
			holder.his_Uphone.setText(repairs.uPhone);
			holder.his_addTime.setText((repairs.add_time).substring(0, 10));
			holder.his_kdTime.setText((repairs.kd_time).substring(0, 10));
		}
		return null;
	}

	public class HistoryRepairHolder {
		TextView his_Uname;
		TextView his_Uphone;
		TextView his_addTime;
		TextView his_kdTime;
	}
}
