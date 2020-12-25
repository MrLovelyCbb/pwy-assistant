package com.tchappy.photo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.tchappy.photo.data.PayTypeEntity.PayType;
import com.tchappy.photo.util.Utils.Pay_Info;
import com.tchappy.pwy.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author yangyu 功能描述：ViewPager适配器，用来绑定数据和view
 */
public class PayList_Adapter extends BaseAdapter {

	// 界面列表
	private List<PayType> pay_Infos;
	private Context context;
	private int selected = -1;

	public PayList_Adapter(List<PayType> pay_Infos, Context context) {
		this.context = context;
		this.pay_Infos = pay_Infos;
	}

	/**
	 * 获得当前界面数
	 */
	@Override
	public int getCount() {
		if (pay_Infos != null) {
			return pay_Infos.size();
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

	public PayType selected(int selected) {
		this.selected = selected;
		this.notifyDataSetChanged();
		return pay_Infos.get(selected);
	}

	public PayType setFirstChose() {
		return this.selected(0);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.my_paytype_item, null);
		}
		ImageView payImg = (ImageView) convertView.findViewById(R.id.pay_type_item_img);
		ImageView paySelect = (ImageView) convertView.findViewById(R.id.pay_type_item_select);
		paySelect.setImageResource(R.drawable.ic_radio_btn_off);
		TextView payName = (TextView) convertView.findViewById(R.id.pay_type_item_name);
		TextView payNamedes = (TextView) convertView.findViewById(R.id.pay_type_item_namedes);
		TextView payId = (TextView) convertView.findViewById(R.id.pay_type_item_id);
		payName.setText(pay_Infos.get(position).getUname());
		payId.setText(pay_Infos.get(position).getUvalue()+"");
		System.out.println("payName"+pay_Infos.get(position).getUname());
		System.out.println("payId"+pay_Infos.get(position).getUvalue());
		
		switch(pay_Infos.get(position).getUvalue()){
			case 1://银联
				payImg.setImageResource(R.drawable.ic_uppay_plugin_enabled);
				payNamedes.setText(R.string.paylist_yinlian);
				break;
			case 2://支付宝
				payImg.setImageResource(R.drawable.ic_alipay_plugin_enabled);
				payNamedes.setText(R.string.paylist_ali);
				break;
			case 3://卡密
				payImg.setImageResource(R.drawable.ic_card_plugin_enabled);
				payNamedes.setText(R.string.paylist_card);
				break;
			default:
				payImg.setImageResource(R.drawable.ic_card_plugin_enabled);
				payNamedes.setText(R.string.paylist_card);
				break;
		}

		if (position == selected) {
			paySelect.setImageResource(R.drawable.ic_radio_btn_on);
		} else {
			paySelect.setImageResource(R.drawable.ic_radio_btn_off);
		}

		return convertView;
	}

}
