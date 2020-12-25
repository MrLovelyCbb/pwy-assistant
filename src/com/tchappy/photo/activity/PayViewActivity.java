package com.tchappy.photo.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.tc.in.InfoMation;
import com.tc.myview.PayResult;
import com.tc.object.OBJ.AlixPay_Info;
import com.tc.pay.alipay.AlixPay;
import com.tchappy.photo.adapter.PayList_Adapter;
import com.tchappy.photo.data.BasesEntity;
import com.tchappy.photo.data.HistroyRepairEntity;
import com.tchappy.photo.data.OrderEntity;
import com.tchappy.photo.data.PayTypeEntity;
import com.tchappy.photo.data.PayTypeEntity.PayType;
import com.tchappy.photo.fragment.BaseFragmentActivity;
import com.tchappy.photo.http.JsonHttpResponseHandler;
import com.tchappy.photo.thread.SspanApplication;
import com.tchappy.photo.util.Utils;
import com.tchappy.photo.util.Utils.Pay_Info;
import com.tchappy.photo.util.View_Pay_Card;
import com.tchappy.photo.util.View_Web_Full;
import com.tchappy.pwy.R;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class PayViewActivity extends BaseFragmentActivity {

	private TextView pay_name;
	private TextView pay_price;
	private TextView pay_xieyi;
	private ListView pay_list;
	private Button btn_cancle;
	private Button btn_pay;
	private PayList_Adapter adapter;
//	private Pay_Info my_payInfo = null;
	private PayType my_payType = null;
	private ArrayList<Pay_Info> payinfos;// 支付类型
	private static Activity activity = null;
	
	private PayTypeEntity payTypeEntity = null;
	private OrderEntity orderEntity = null;
	
	private List<PayTypeEntity> m_data = new ArrayList<PayTypeEntity>();
	private List<PayType> m_TypeData = new ArrayList<PayTypeEntity.PayType>();
	
	private String PAY_UID = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		if (intent != null){
			PAY_UID = intent.getStringExtra("PAY_UID");
		}
		ReqPayTypeList();
		
		setContentView(R.layout.view_pay);
		
		activity = this;
		Pay_Info payInfo = new Pay_Info();
		payInfo.setId("3");
		payInfo.setName("岁岁屏安卡");
		payinfos = new ArrayList<Pay_Info>();
		payinfos.add(payInfo);
		initView();
	}
	
	/**
	 * 请求支付类型列表
	 */
	private void ReqPayTypeList(){
		SspanApplication.asyncApi.ReqServiceList(payTypeList);
	}
	
	private JsonHttpResponseHandler payTypeList = new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			PayTypeEntity payEntity = new PayTypeEntity(response);
			if (payEntity.isStatus()){
				m_data = payEntity.getList();
				if (m_data != null){
					payTypeEntity = m_data.get(0);
					pay_name.setText(payTypeEntity.getUname());
					pay_price.setText(payTypeEntity.getPrice());
				}
				m_TypeData = payEntity.getTypeList();
				if (m_TypeData != null){
					adapter = new PayList_Adapter(m_TypeData, activity);
					pay_list.setAdapter(adapter);
					my_payType = adapter.setFirstChose();
					
					adapter.notifyDataSetChanged();
				}
			}
		}
		
	};
	
	/**
	 * 生成订单
	 * @param uid
	 * @param money
	 * @param sp
	 */
	private void ReqGeneralOrder(String uid,int sp){
		if (uid.equals("") || payTypeEntity.getId()==0 || payTypeEntity.getPrice().equals("") || payTypeEntity.getPrice() == null || sp == 0)
			return;
		SspanApplication.asyncApi.ReqGeneralOrder(SspanApplication.SID, uid, payTypeEntity.getId()+"", payTypeEntity.getPrice(), sp+"", PayJsonResponseHandler);
	}
	
	private JsonHttpResponseHandler PayJsonResponseHandler = new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			OrderEntity order = new OrderEntity(response);
			switch (order.getStatusCode()) {
			case 0:
				orderEntity = order;
				if (order_Info.getStatus().equals("0")) {
					// 私有订单号，方便查询
					PayResult.privateOrderID = order_Info.getOrderid();
					// 启动银联支付
					if (order_Info.getUsp().equals("1")) {
						UPPayAssistEx.startPayByJAR(activity, PayActivity.class, null, null, order_Info.getOrderidrn(), "00");
						// 启动支付宝支付
					} else if (order_Info.getUsp().equals("2")) {
						AlixPay_Info alixPay_info = new AlixPay_Info();
						alixPay_info.setTitle(order_Info.getName());
						alixPay_info.setInfos(order_Info.getName());
						alixPay_info.setOrderid(order_Info.getOrderid());
						alixPay_info.setNotifyUrl(InfoMation.URL_ALI_NOTIFY);
						alixPay_info.setPrice(Integer.parseInt(order_Info.getPrize()) / 100 + "");
						// alixPay_info.setPrice(0.01 + "");
						AlixPay alixPay = new AlixPay(activity, alixPay_info);
						alixPay.pay();
					} else if (order_Info.getUsp().equals("3")) {
						View_Pay_Card.pay(activity, PayResult.privateOrderID);
					} 
				}
				break;
			case 1:
				showTxt("用户不存在，创建订单失败.");
				break;
			case 2:
				showTxt("客户不存在，创建订单失败.");
				break;
			case 3:
				showTxt("生成订单失败.");
				break;
			default:
				showTxt("未知错误");
				break;
			}
		}
	};
	
	/**
	 * 查询订单，验证是否充值成功
	 */
	private void ReqQueryOrder() {
		if (orderEntity == null)
			return;
		SspanApplication.asyncApi.ReqQueryOrder(orderEntity.getTradeNo(), new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				BasesEntity status = new BasesEntity(response);
				if (status.isStatus()){
					showTxt("充值成功----"+status.getData().optString("trade_no"));
				}
			}
			
		});
	}
	
	private void initView() {
		pay_name = (TextView) findViewById(R.id.pay_name);
		pay_price = (TextView) findViewById(R.id.pay_price);
		pay_xieyi = (TextView) findViewById(R.id.pay_xieyi);
		pay_list = (ListView) findViewById(R.id.pay_type_list);
		
		pay_name.setText("12月屏保流量服务");
		pay_price.setText("84元");
		
		pay_xieyi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				View_Web_Full.showWeb(activity, Utils.URL_XIEYI);
			}
		});
		
		pay_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				my_payType = adapter.selected(arg2);
			}
		});
		
		btn_cancle = (Button) findViewById(R.id.pay_cancelBTN);
		btn_cancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btn_pay = (Button) findViewById(R.id.pay_payBTN);
		btn_pay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
//				View_Pay_Card.pay(activity);
				ReqGeneralOrder(PAY_UID, my_payType.Uvalue);
//				finish();
			}
		});
		
	}

}
