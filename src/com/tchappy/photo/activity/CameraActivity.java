package com.tchappy.photo.activity;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

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
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.tchappy.photo.util.Bimp;
import com.tchappy.photo.util.FileUtils;
import com.tchappy.photo.util.ImageItem;
import com.tchappy.photo.util.MyProgressBar;
import com.tchappy.photo.util.PublicWay;
import com.tchappy.photo.util.Res;
import com.tchappy.photo.util.Utils;
import com.tchappy.photo.view.MyButton;
import com.tchappy.photo.thread.Thread_DownApk;
import com.tchappy.photo.thread.Thread_Post;
import com.tchappy.pwy.R;


/**
 * 首页面activity
 *
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日  下午11:48:34
 */
public class CameraActivity extends Activity implements View.OnClickListener{

	private GridView noScrollgridview;
	private GridAdapter adapter;
	private View parentView;
	public static Bitmap bimap ;
	private String name;
	private String phone;
	private EditText et_name;
	private EditText et_phone;
	private Button btn_send;
	private int picNum = 0;
	public static HashMap<String, File> file_map = new HashMap<String, File>();
	public static HashMap<String, String> info_map = new HashMap<String, String>();
	public static HashMap<String, String> file_name = new HashMap<String, String>();
	public static Handler main_Handler = null;
	private String sdPath;
	private MyProgressBar myProgressBar;
	private Activity activity = null;
	private Dialog alertDialog;
	
	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
	public GeofenceClient mGeofenceClient;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Res.init(this);
		activity = this;
		Utils.setAppInfo(activity);
		Utils.dm = new DisplayMetrics();
		Utils.dm = this.getResources().getDisplayMetrics();
		Utils.creatDir();
		bimap = BitmapFactory.decodeResource(
				getResources(),
				R.drawable.icon_addpic_unfocused);
		PublicWay.activityList.add(this);
		parentView = getLayoutInflater().inflate(R.layout.activity_selectimg, null);
		setContentView(parentView);
		Init();
		InitLocation();
//		mLocationClient.start();
//		tipsDialog();
		
		main_Handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				String res = null;
				switch (msg.what) {
				case Utils.POST_RECEIVE:
					res = msg.obj.toString();
					try {
						JSONObject mainJson = new JSONObject(res);
						if(mainJson.optString("status").equals("0")) {
							Toast.makeText(CameraActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
							clearAll();
//							Thread.sleep(100);
//							finish();
//							exit();
						} else if (mainJson.optString("status").equals("2")) {
							Toast.makeText(CameraActivity.this, "该用户信息已存在，请勿重复上传!", Toast.LENGTH_SHORT).show();
							clearAll();
//							Thread.sleep(100);
//							exit();
						} else {
							Toast.makeText(CameraActivity.this, "上传失败，请重试", Toast.LENGTH_SHORT).show();
//							exit();
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
								doUpdate(CameraActivity.this, 1);// 强制
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
					Toast.makeText(activity, getString(R.string.post_ERROR) + Utils.THREAD_DOWN_APK_ERROR, Toast.LENGTH_SHORT).show();
					break;
				case Utils.THREAD_ERROR:
					Toast.makeText(activity, getString(R.string.post_ERROR) + Utils.THREAD_DOWN_APK_ERROR, Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
			}
			
		};
		upData();
	}
	

	public void Init() {

		MyButton menu_1 = (MyButton) findViewById(R.id.menu_1);
		MyButton menu_2 = (MyButton) findViewById(R.id.menu_2);
		MyButton menu_3 = (MyButton) findViewById(R.id.menu_3);
		MyButton menu_4 = (MyButton) findViewById(R.id.menu_4);
		
		menu_1.setText(getString(R.string.menu_btn1_str));
		menu_2.setText(getString(R.string.menu_btn2_str));
		menu_3.setText(getString(R.string.menu_btn3_str));
		menu_4.setText(getString(R.string.menu_btn4_str));
		
		menu_1.setImage(R.drawable.camera_icon);
		menu_2.setImage(R.drawable.repair_icon);
		menu_3.setImage(R.drawable.search_icon);
		menu_4.setImage(R.drawable.my_icon);
		
		menu_1.setOnClickListener(this);
		menu_2.setOnClickListener(this);
		menu_3.setOnClickListener(this);
		menu_4.setOnClickListener(this);
		
		
		btn_send = (Button) findViewById(R.id.btn_send);
		et_name = (EditText) findViewById(R.id.regist_name);
		et_phone = (EditText) findViewById(R.id.regist_phone);

		btn_send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				name = et_name.getText().toString();
				phone = et_phone.getText().toString();
				if (picNum < 2) {
					Toast.makeText(CameraActivity.this, "必须上传2张照片", Toast.LENGTH_SHORT).show();
				} else if (name.length() < 2) {
					Toast.makeText(CameraActivity.this, "请输入正确的姓名", Toast.LENGTH_SHORT).show();
				} else if (phone.length() < 11) {
					Toast.makeText(CameraActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
				} else if (!isMobile(phone)) {
					Toast.makeText(CameraActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
				} else {
					
					if (!Utils.locaInfo.getLatitude().equals("")) {
						info_map.put("name", name);
						info_map.put("phone", phone);
						//位置信息
//						info_map.put("province", Utils.locaInfo.getProvince());
//						info_map.put("city", Utils.locaInfo.getCity());
//						info_map.put("district", Utils.locaInfo.getDistrict());
//						info_map.put("street", Utils.locaInfo.getStreet());
//						info_map.put("cityCode", Utils.locaInfo.getCityCode());
						info_map.put("latitude", Utils.locaInfo.getLatitude());
						info_map.put("longitude", Utils.locaInfo.getLongitude());
						
						if(Bimp.tempSelectBitmap.size() != 0 ) {
							new Thread_Post(CameraActivity.this, main_Handler, Utils.URL_SENDPIC, info_map, file_map, Utils.POST_RECEIVE).start();
						}
					} else {
						Toast.makeText(CameraActivity.this, "请等待GPS获取信号", Toast.LENGTH_SHORT).show();
					}
					
				}
				
			}
		});
		
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);	
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				if (arg2 == Bimp.tempSelectBitmap.size()) {
					photo();
				} else {
					Intent intent = new Intent(CameraActivity.this,
							GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
		
		

	}
	
	private void exit() {
		this.finish();
		mLocationClient.stop();
//		android.os.Process.killProcess(android.os.Process.myPid());
//		System.exit(0);
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
				Bimp.tempSelectBitmap.remove(i);
				adapter.notifyDataSetChanged();
			}
		}

		public int getCount() {
			if(Bimp.tempSelectBitmap.size() == 2){
				return 2;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
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
				break;
			case 1:
				holder.text.setText("串号特写");
				break;

			default:
				break;
			}

			if (position ==Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 2) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
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
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
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

	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}
	
	
	
	@Override
	protected void onResume() {
		adapter.update();
		super.onResume();
	}


	@Override
	protected void onStop() {
		mLocationClient.stop();
		super.onStop();
	}


	private static final int TAKE_PICTURE = 0x000001;

	public void photo() {
		if (android.os.Environment.getExternalStorageState().equals(  
				android.os.Environment.MEDIA_MOUNTED)) {  
			System.out.println("------------------------------yes");
		} else  {
			Toast.makeText(CameraActivity.this, "SD card 不可用...", Toast.LENGTH_SHORT).show();
			return;
		}
		
		
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg"));
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
				
				String fileName = String.valueOf(System.currentTimeMillis());
//				Bitmap bm = (Bitmap) data.getExtras().get("data");
//				Bitmap bm = Utils.getBitmapFromLocal(Environment.getExternalStorageDirectory()+"/temp.jpg");
				System.out.println("filepath:"+Environment.getExternalStorageDirectory()+"/temp.jpg");
				Bitmap bm = Utils.compressBitmap(Environment.getExternalStorageDirectory()+"/temp.jpg");
				FileUtils.saveBitmap(bm, fileName);
				
				ImageItem takePhoto = new ImageItem();
				takePhoto.setBitmap(bm);
				Bimp.tempSelectBitmap.add(takePhoto);
				picNum ++;	//照片计数器
				System.out.println("picNum:"+picNum);
				
			}
			break;
		}
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
	
	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return [0-9]{5,9}
	 */
	public static boolean isMobile(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^1(([3][0123456789])|([4][0123456789])|([5][0123456789])|([7][0123456789])|([8][0123456789]))[0-9]{8}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	private void tipsDialog() {
		alertDialog = new AlertDialog.Builder(this). 
                setTitle("拍照要求"). 
                setMessage("1.拍摄的手机需完整；\n2.手机串号清晰可辨。"). 
                setIcon(R.drawable.ic_launcher). 
                setPositiveButton("确定", new DialogInterface.OnClickListener() { 
                     
                    @Override 
                    public void onClick(DialogInterface dialog, int which) { 
                    	mLocationClient.start();
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
			Bimp.tempSelectBitmap.remove(0);
//			Bimp.max--;
			adapter.notifyDataSetChanged();
		}
		Bimp.max = 0;
		picNum = 0;
	}
	
	public void upData() {
		HashMap<String, String> map = new HashMap<String, String>();
		System.out.println("getVcode:"+Utils.app_Info.getVcode());
		map.put("ver", Utils.app_Info.getVcode());// 当前安卓版本数字
		map.put("mach", "1");
		map.put("sp", "0");
		new Thread_Post(activity, main_Handler, Utils.URL_UPDATA, map, Utils.POST_UPDATA).start();
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

//			System.out.println("locaInfo.province"+Utils.locaInfo.getProvince());
//			System.out.println("locaInfo.city"+Utils.locaInfo.getCity());
//			System.out.println("locaInfo.district"+Utils.locaInfo.getDistrict());
//			System.out.println("locaInfo.street"+Utils.locaInfo.getStreet());
//			System.out.println("locaInfo.cityCode"+Utils.locaInfo.getCityCode());
//			System.out.println("locaInfo.latitude"+Utils.locaInfo.getLatitude());
//			System.out.println("locaInfo.longitude"+Utils.locaInfo.getLongitude());
//			System.out.println("location.getLatitude()"+location.getLatitude());
//			System.out.println("location.getLongitude()"+location.getLongitude());
//			System.out.println("location.getProvince()"+location.getProvince());
//			System.out.println("location.getCity()"+location.getCity());
//			System.out.println("location.getDistrict()"+location.getDistrict());
//			System.out.println("location.getStreet()"+location.getStreet());
//			System.out.println("location.getCityCode()"+location.getCityCode());
		}
		
	}
	
	private void InitLocation(){
		mLocationClient = new LocationClient(this);
		mMyLocationListener = new MyLocationListener();
		mGeofenceClient = new GeofenceClient(this);
		
		LocationClientOption option = new LocationClientOption();
		mLocationClient.registerLocationListener(mMyLocationListener);
		option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
		option.setCoorType("gcj02");//返回的定位结果是百度经纬度，默认值gcj02
		int span=10000;
		option.setScanSpan(span);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_1:
			setContentView(R.layout.getpassword);
			break;
		case R.id.menu_2:
			setContentView(R.layout.register);
			break;
		case R.id.menu_3:
			setContentView(R.layout.login);
			break;
		case R.id.menu_4:
			setContentView(R.layout.view_pay);
			break;
		default:
			break;
		}
	}
	
}

