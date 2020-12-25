package com.tchappy.photo.fragment;

import java.io.File;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.tchappy.photo.activity.MainActivity;
import com.tchappy.photo.activity.PayViewActivity;
import com.tchappy.photo.activity.ShowQRCodeAct;
import com.tchappy.photo.adapter.NoticeInfoAdapter;
import com.tchappy.photo.data.BasesEntity;
import com.tchappy.photo.thread.SspanApplication;
import com.tchappy.photo.thread.Thread_DownApk;
import com.tchappy.photo.thread.Thread_Post;
import com.tchappy.photo.util.Bimp;
import com.tchappy.photo.util.FileUtils;
import com.tchappy.photo.util.ImageItem;
import com.tchappy.photo.util.JSONParser;
import com.tchappy.photo.util.MessageType;
import com.tchappy.photo.util.MyProgressBar;
import com.tchappy.photo.util.PublicWay;
import com.tchappy.photo.util.Res;
import com.tchappy.photo.util.Utils;
import com.tchappy.photo.util.INFO.Notice_Server;
import com.tchappy.pwy.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author yangyu
 *	功能描述：首页fragment页面
 */
public class OneFragment extends BaseFragment implements View.OnClickListener{

	private View mParent;
	private FragmentActivity mActivity;
	
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	
	private TextView m_Title;
	private ImageView m_Image;
	private Button m_Button;
	
	public static Bitmap bimap ;
	private GridView noScrollgridview;
	private GridAdapter adapter;
	
	private String name;
	private String phone;
	private EditText et_name;
	private EditText et_phone;
	public static HashMap<String, File> file_map = new HashMap<String, File>();
	public static HashMap<String, String> info_map = new HashMap<String, String>();
	public static HashMap<String, String> file_name = new HashMap<String, String>();
	
	public static Handler main_Handler = null;
	private Handler handler = null;
	
	private String sdPath;
	private MyProgressBar myProgressBar;
	private Activity activity = null;
	private Dialog alertDialog;
	
	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
	public GeofenceClient mGeofenceClient;
	
	private int picNum = 0;
	
	private TextView tv_show_more;
	private ListView lv_show_notice;
	private Notice_Server sercer_notice = null;
	private NoticeInfoAdapter noticeAdapter;
	
	private static String PWY_UID = "";
	
	/**
	 * Create a new instance of DetailsFragment, initialized to show the text at
	 * 'index'.
	 */
	public static OneFragment newInstance(int index) {
		OneFragment f = new OneFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);

		return f;
	}

	public int getShownIndex() {
		return getArguments().getInt("index", 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_one, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActivity = getActivity();
		activity = getActivity();
		mParent = getView();
		
		Res.init(mActivity);
		Utils.setAppInfo(mActivity);
		Utils.dm = new DisplayMetrics();
		Utils.dm = this.getResources().getDisplayMetrics();
		Utils.creatDir();
		Bimp.tempSelectBitmap1 = null;
		Bimp.tempSelectBitmap2 = null;
		bimap = BitmapFactory.decodeResource(
				getResources(),
				R.drawable.icon_addpic_unfocused);
		PublicWay.activityList.add(mActivity);
		
		InitLocation();
//		mLocationClient.start();
		tipsDialog();
		
		m_Title = (TextView) mParent.findViewById(R.id.top_bar_title);
		m_Title.setText(R.string.login_item16_str);
		
		m_Image = (ImageView) mParent.findViewById(R.id.top_bar_left);
		
		m_Button = (Button) mParent.findViewById(R.id.top_bar_right);
		m_Button.setText(R.string.login_item17_str);
		
		m_Image.setVisibility(View.INVISIBLE);
		m_Button.setOnClickListener(this);
		
		et_name = (EditText) mParent.findViewById(R.id.regist_name);
		et_phone = (EditText) mParent.findViewById(R.id.regist_phone);
		
		
		// ListView
		lv_show_notice = (ListView)mParent.findViewById(R.id.lv_show_info);
		
		// 初始化 pop
		pop = new PopupWindow(getActivity());
		
		View view = getActivity().getLayoutInflater().inflate(R.layout.item_popupwindows, null);

		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		
		// 初始化选择图片组件
		noScrollgridview = (GridView) mParent.findViewById(R.id.noScrollgridview);	
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(mActivity);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 2) {
					Intent intent = new Intent(getActivity(), ShowQRCodeAct.class);
					startActivity(intent);
				} else {
					photo(arg2);
					pop.dismiss();
					ll_popup.clearAnimation();
				}
			}
		});
		
		main_Handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				String res = null;
				switch (msg.what) {
				case Utils.POST_RECEIVE:
					// 上传返回
					BasesEntity status = new BasesEntity(msg.obj.toString());
					if (status.isStatus()){
						PWY_UID = status.getData().optString("uid");
						showTxt("Debug...上传成功");
						
						if(Bimp.tempSelectBitmap.size() > 0 ) {
							Intent intent = new Intent(mActivity, PayViewActivity.class);
							intent.putExtra("PAY_UID", PWY_UID);
							startActivity(intent);
						}
						clearAll();
					}else if (status.getStatusCode() == 2){
						showTxt("该用户信息已存在，请勿重复上传!");
						clearAll();
					}else{
						showTxt(status.getInfo());
					}
					break;
				//卡密参保返回信息,与直接上传区分开来
				case MessageType.PAY_CARD_RESULT:
					res = msg.obj.toString();
					try {
						JSONObject mainJson = new JSONObject(res);
						if(mainJson.optString("status").equals("0")) {
							Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT).show();
							clearAll();
						}  else {
							Toast.makeText(getActivity(), mainJson.optString("info"), Toast.LENGTH_SHORT).show();
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("post result:"+res);
					break;
					// 更新信息
				case Utils.POST_UPDATA:
					try {
						res = msg.obj.toString();
						if (!TextUtils.isEmpty(res)) {
							Utils.updata_Info = Utils.ParseJson_Updata(res);
							if (Integer.parseInt(Utils.updata_Info.getMaxver()) > Integer.parseInt(Utils.app_Info.getVcode())) {
								doUpdate(getActivity(), 1);// 强制
							} else {
								tipsDialog();
							}
						}
					} catch (Exception e) {

					}
					break;
				case Utils.THREAD_DOWN_APK_START:
					myProgressBar = new MyProgressBar(activity);
					myProgressBar.show("建立下载链接");
					break;
				case Utils.THREAD_DOWN_APK_LOADING:
					int a = msg.arg1;// 当前进度
					myProgressBar.setText("下载:" + a + "%");
					break;
				case Utils.THREAD_DOWN_APK_END:
					myProgressBar.dismiss();
					String fileName = sdPath;
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
					activity.startActivity(intent);
					break;
				case Utils.THREAD_DOWN_APK_ERROR:
					showTxt(getString(R.string.post_ERROR) + Utils.THREAD_DOWN_APK_ERROR);
					break;
				case Utils.THREAD_ERROR:
					showTxt(getString(R.string.post_ERROR) + Utils.THREAD_DOWN_APK_ERROR);
					break;
				case MessageType.PAY_CARD_START:
					res = msg.obj.toString();
					System.out.println("res="+res);
					if (!TextUtils.isEmpty(res)) {
						info_map.put("name", et_name.getText().toString());
						info_map.put("phone", et_phone.getText().toString());
						info_map.put("saleId", Utils.user_info.id);
						info_map.put("cnum", res);
						//位置信息
						info_map.put("latitude", Utils.locaInfo.getLatitude());
						info_map.put("longitude", Utils.locaInfo.getLongitude());
						
						if(picNum == 2 ) {
							new Thread_Post(getActivity(), main_Handler, Utils.URL_SENDPIC, info_map, file_map, MessageType.PAY_CARD_RESULT).start();
						}
					}
					break;
				case MessageType.GET_NOTICE_INFO:
					res = msg.obj.toString();
					sercer_notice = JSONParser.ParseJeson_Notice(res);
					noticeAdapter = new NoticeInfoAdapter(sercer_notice.getNotice_info(), SspanApplication.mContext);
					lv_show_notice.setAdapter(noticeAdapter);
					break;
				default:
					break;
				}
			}
			
		};
		getInfo();
	}
	
	private void InitLocation(){
		mLocationClient = new LocationClient(mActivity);
		mMyLocationListener = new MyLocationListener();
		mGeofenceClient = new GeofenceClient(mActivity);
		
		LocationClientOption option = new LocationClientOption();
		mLocationClient.registerLocationListener(mMyLocationListener);
		option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
		option.setCoorType("gcj02");//返回的定位结果是百度经纬度，默认值gcj02
		int span=10000;
		option.setScanSpan(span);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
	
	private void getInfo() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("arr10", "1");
		map.put("arr11", "0");
		new Thread_Post(activity, main_Handler, Utils.URL_GET_NOTICEINFO, map, MessageType.GET_NOTICE_INFO).start();
	}
	
	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			Utils.locaInfo.setCity(location.getCity());
			Utils.locaInfo.setCityCode(location.getCityCode());
			Utils.locaInfo.setDistrict(location.getDistrict());
			Utils.locaInfo.setLatitude(location.getLatitude()+"");
			Utils.locaInfo.setLongitude(location.getLongitude()+"");
			Utils.locaInfo.setProvince(location.getProvince());
			Utils.locaInfo.setStreet(location.getStreet());
		}
	}
	
	private static final int TAKE_PICTURE = 0x000001;
	
	public void photo(int position) {
		if (android.os.Environment.getExternalStorageState().equals(  
				android.os.Environment.MEDIA_MOUNTED)) {  
			System.out.println("------------------------------yes");
		} else  {
			Toast.makeText(mActivity, "SD card 不可用...", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg"));
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(openCameraIntent, position);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 0:
			if (resultCode == getmActivity().RESULT_OK) {
			
			if(picNum < 2)
				picNum ++;	//照片计数器
			
			String fileName = String.valueOf(System.currentTimeMillis());
			Bitmap bm = Utils.compressBitmap(Environment.getExternalStorageDirectory()+"/temp.jpg");
			FileUtils.saveBitmap(bm, fileName);
			
			ImageItem takePhoto = new ImageItem();
			takePhoto.setBitmap(bm);
			Bimp.tempSelectBitmap1 = takePhoto;
			Bimp.tempSelectBitmap.add(Bimp.tempSelectBitmap1);
			
			System.out.println("picNum:"+picNum);
			
		}
			break;
		case 1:
			if (resultCode == getActivity().RESULT_OK) {
				
			if(picNum < 2)
				picNum ++;	//照片计数器
			String fileName = String.valueOf(System.currentTimeMillis());
			Bitmap bm = Utils.compressBitmap(Environment.getExternalStorageDirectory()+"/temp.jpg");
			FileUtils.saveBitmap(bm, fileName);
			
			ImageItem takePhoto = new ImageItem();
			takePhoto.setBitmap(bm);
			Bimp.tempSelectBitmap2 = takePhoto;
			Bimp.tempSelectBitmap.add(Bimp.tempSelectBitmap2);
			System.out.println("picNum:"+picNum);
			
		}
			break;
		}
	}
	
	@Override
	public void onClick(View v){
		goCanBaoActivity();
	}
	
	private void goCanBaoActivity() {
		name = getTxt(et_name);
		phone = getTxt(et_phone);
		
		if (!Validata()){
			return;
		}
	
		info_map.put("arr10", SspanApplication.SID);
		info_map.put("arr11", name);
		info_map.put("arr12", phone);
		
//			if (!Utils.locaInfo.getLatitude().equals("")) {
//				//位置信息
//				info_map.put("latitude", Utils.locaInfo.getLatitude());
//				info_map.put("longitude", Utils.locaInfo.getLongitude());
//			} else {
//				showTxt("请等待GPS获取信号");
//			}
			
			// 先传用户数据   获取uid，获取订单，
			new Thread_Post(getActivity(), main_Handler, Utils.URL_SENDPIC, info_map, file_map, Utils.POST_RECEIVE).start();
			
			//
			//Intent intent = new Intent(getActivity(), PayViewActivity.class);
			//startActivity(intent);
	}
	
	/**
	 * 验证信息
	 * @return
	 */
	private boolean Validata() {
		
		if (picNum < 2) {
			showTxt("必须上传2张照片");
			return false;
		}
		
		if (isEmptyByComponents(et_name)) {
			showTxt("请输入正确的姓名");
			return false;
		}
		
		if (isEmptyByComponents(et_phone) || !Utils.isMobile(getTxt(et_phone))) {
			showTxt("请输入正确的手机号码");
			return false;
		}
		
		if (!Utils.isMobile(phone)) {
			showTxt("请输入正确的手机号");
			return false;
		}
		
		return true;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			for(int i=0;i<PublicWay.activityList.size();i++){
				if (null != PublicWay.activityList.get(i)) {
					PublicWay.activityList.get(i).finish();
				}
			}
			System.exit(0);
		}
		return true;
	}
	
	private void tipsDialog() {
		alertDialog = new AlertDialog.Builder(mActivity). 
                setTitle("拍照要求"). 
                setMessage("1.拍摄时请点亮手机屏幕\n2.照片均须按出串号拍摄\n3.照片须清晰且串号可辨"). 
                setIcon(R.drawable.ic_launcher). 
                setPositiveButton("确定", new DialogInterface.OnClickListener() { 
                     
                    @Override 
                    public void onClick(DialogInterface dialog, int which) { 
                    	mLocationClient.start();
                    	alertDialog.dismiss();
                    } 
                }).create(); 
        alertDialog.show(); 
	}
	
	



	
	private void clearAll() {
		et_name.setText("");
		et_phone.setText("");
		bimap.recycle();
		for(int i=0;i<picNum;i++) {
			System.out.println("clearall i="+i);
			Bimp.tempSelectBitmap1 = null;
			Bimp.tempSelectBitmap2 = null;
//			Bimp.max--;
			adapter.notifyDataSetChanged();
		}
		
		// 清楚Bimp tem array 数据
		Bimp.tempSelectBitmap.clear();
		file_map.clear();
		info_map.clear();
		file_name.clear();
		
		Bimp.max = 0;
		picNum = 0;
	}
	
	/** 提示更新窗口 */
	private void doUpdate(final Activity activity, int type) {
		StringBuffer sb = new StringBuffer();
		sb.append("更新内容：");
		String des = Utils.updata_Info.getDinfo();
		// 切割字符添加更新内容
		String s1[] = des.split("\\+");
		for (String s : s1) {
			sb.append("\r\n" + s);
		}
		// sb.append("\r\n是否更新?");
		if (type == 1) {
			Dialog dialog = new AlertDialog.Builder(activity).setTitle("更新提示").setMessage(sb.toString())
			// 设置内容
					.setPositiveButton("退出",// 设置确定按钮
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									activity.finish();
									System.exit(0);
								}
							}).setNegativeButton("猛击更新", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							sdPath = Utils.APP_APP_PATH + Utils.updata_Info.getAppname() + ".apk";
							System.out.println("sdPath:"+sdPath);
							System.out.println("Utils.updata_Info.getUrl():"+Utils.updata_Info.getUrl());
							new Thread_DownApk(main_Handler, Utils.updata_Info.getUrl(), sdPath).start();

						}
					}).setCancelable(false).create();// 创建
			// 显示对话框
			dialog.show();
		} else if (type == 2) {
			Dialog dialog = new AlertDialog.Builder(activity).setTitle("更新提示").setMessage(sb.toString())
			// 设置内容
					.setPositiveButton("飘过", null).setNegativeButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							sdPath = Utils.APP_APP_PATH + Utils.updata_Info.getAppname() + ".apk";
							new Thread_DownApk(main_Handler, Utils.updata_Info.getUrl(), sdPath).start();
						}
					}).create();// 创建
			// 显示对话框
			dialog.show();
		} else if (type == 0) {
		} else {

		}
	}
	
	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}
		
		public void clearInfo() {
			for(int i=0;i<Bimp.max;i++) {
				System.out.println("i="+i);
				Bimp.tempSelectBitmap1 = null;
				Bimp.tempSelectBitmap2 = null;
				adapter.notifyDataSetChanged();
			}
		}

		public int getCount() {
			return 3;
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				holder.text = (TextView) convertView.findViewById(R.id.item_grida_text);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			//提示文字
			switch (position) {
			case 0:
				holder.text.setText("手机全景");
				holder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused));
				if(Bimp.tempSelectBitmap1 != null)
					holder.image.setImageBitmap(Bimp.tempSelectBitmap1.getBitmap());
				break;
			case 1:
				holder.text.setText("串号特写");
				holder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused));
				if(Bimp.tempSelectBitmap2 != null)
					holder.image.setImageBitmap(Bimp.tempSelectBitmap2.getBitmap());
				break;
			case 2:
				holder.text.setText("点击下载客户端");
				holder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_down_app));
				break;

			default:
				break;
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
			public TextView text;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == picNum) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}
	
	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}
	
	public void onRestart() {
		adapter.update();
		super.onStart();
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onResume() {
		adapter.update();
		super.onResume();
	}


	@Override
	public void onStop() {
		mLocationClient.stop();
		super.onStop();
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
