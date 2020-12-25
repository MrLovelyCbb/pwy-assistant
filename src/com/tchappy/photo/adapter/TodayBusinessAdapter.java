package com.tchappy.photo.adapter;

import java.util.List;
import com.tchappy.photo.data.TodaysEntity;
import com.tchappy.pwy.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TodayBusinessAdapter extends BaseAdapter {

	private Context m_context;
	private List<TodaysEntity> m_list;
	protected LayoutInflater mInflater;
	
	public TodayBusinessAdapter(Context context,List<TodaysEntity> m_list) {
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
			holder.todays_Uname = (TextView) view.findViewById(R.id.today_item_name);
			holder.todays_Uphone = (TextView) view.findViewById(R.id.today_item_phone);
			holder.todays_Service = (TextView) view.findViewById(R.id.today_item_service);
			holder.todays_Time = (TextView) view.findViewById(R.id.today_item_time);
			view.setTag(holder);
		}else{
			view = convertView;
		}
		TodaysHolder holder = (TodaysHolder) view.getTag();
		TodaysEntity todadys = (TodaysEntity) m_list.get(position);
		if (holder != null){
			holder.todays_Uname.setText(todadys.uName);
			holder.todays_Uphone.setText(todadys.uPhone);
			holder.todays_Service.setText(todadys.service);
			holder.todays_Time.setText((todadys.time).substring(0, 10));
		}
		return view;
	}

	public class TodaysHolder{
		TextView todays_Uname;
		TextView todays_Uphone;
		TextView todays_Service;
		TextView todays_Time;
	}
}
