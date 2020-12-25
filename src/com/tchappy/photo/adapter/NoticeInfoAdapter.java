package com.tchappy.photo.adapter;

import java.util.ArrayList;

import com.tchappy.photo.util.INFO.Notice_Info;
import com.tchappy.pwy.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NoticeInfoAdapter extends BaseAdapter {
	
	private ArrayList<Notice_Info> notice_info;
	private Context context;

	public NoticeInfoAdapter(ArrayList<Notice_Info> notice_info, Context context) {
		this.notice_info = notice_info;
		this.context = context;
	}
	@Override
	public int getCount() {
		if(notice_info != null) {
			return notice_info.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView= LayoutInflater.from(context).inflate(R.layout.list_tv_item, null);
		}
		TextView tv_notice = (TextView) convertView.findViewById(R.id.list_title);
		
		if(notice_info.get(position).getTitle().equals("")) {
			tv_notice.setText("系统公告");
		} else {
			tv_notice.setText(position +1 + "." + notice_info.get(position).getTitle());
		}
		
		return convertView;
	}

}
