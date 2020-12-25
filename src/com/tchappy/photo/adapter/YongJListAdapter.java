package com.tchappy.photo.adapter;

import java.util.List;

import com.tchappy.photo.data.YjongEntity;
import com.tchappy.pwy.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class YongJListAdapter extends BaseAdapter {

	private Context m_context;
	private List<YjongEntity> m_list;
	protected LayoutInflater mInflater;
	private OnClickListener[] onClicks;
	
	public YongJListAdapter(Context context,List<YjongEntity> m_list,OnClickListener... onClick) {
		this.m_context=context;
		mInflater = LayoutInflater.from(m_context);
		this.m_list=m_list;
		this.onClicks = onClick;
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null){
			view = mInflater.inflate(R.layout.comm_account_manager_item,null);
			YongjHolder holder = new YongjHolder();
			holder.alipy_Account = (TextView) view.findViewById(R.id.comm_alipay_default);
			holder.alipy_btn1 = (ImageButton) view.findViewById(R.id.comm_alipay_btn1);
			holder.alipy_btn2 = (ImageButton) view.findViewById(R.id.comm_alipay_btn2);
			view.setTag(holder);
		}else{
			view = convertView;
		}
		YongjHolder holder = (YongjHolder) view.getTag();
		YjongEntity yiong = (YjongEntity) m_list.get(position);
		if (holder != null){
			holder.alipy_Account.setText(yiong.getAccount());
			holder.alipy_btn1.setOnClickListener(onClicks[0]);
			holder.alipy_btn1.setTag(position);
			holder.alipy_btn2.setTag(position);
			holder.alipy_btn2.setOnClickListener(onClicks[1]);
		}
		return view;
	}

	public class YongjHolder{
		TextView alipy_Account;
		ImageButton alipy_btn1;
		ImageButton alipy_btn2;
	}
}
