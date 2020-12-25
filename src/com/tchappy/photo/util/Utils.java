package com.tchappy.photo.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


















import com.tchappy.photo.thread.SspanApplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;




public class Utils {
	
	public static final int POST_RECEIVE = 100;
	public static final int POST_UPDATA = 101;
	public static final int THREAD_DOWN_APK_START = 102;
	public static final int THREAD_DOWN_APK_LOADING = 103;
	public static final int THREAD_DOWN_APK_END = 104;
	public static final int THREAD_DOWN_APK_ERROR = 105;
	public static final int THREAD_HTTP_ERROR = 106;
	public static final int THREAD_UTF8_ERROR = 107;
	public static final int THREAD_IO_ERROR = 108;
	public static final int THREAD_ERROR = 109;
	public static LocationInfo locaInfo =  new LocationInfo();
	
	/** Base Http */
	public static final String URL_Base = "http://pwy.youmsj.cn/";
	
	/** 检测更新接口 */
	public static final String URL_UPDATA = URL_Base + "yyzs/update.html";
	/** 登陆接口 */
	public static final String URL_LOGIN = URL_Base + "zs/login.html";
	/** 验证码接口*/
	public static final String URL_GETCODE = URL_Base + "zs/getcode.html";
	/** 注册账号接口 */
	public static final String URL_REGISTER = URL_Base + "zs/register.html";
	/** 重置密码接口 arr10 - arr 11 arr 12*/
	public static final String URL_FORGETPWD = URL_Base + "zs/forgetPassword.html";
	/** 查询今日办理记录 */
	public static final String URL_TODAYORDER = URL_Base + "zs/todayOrder.html";
	/** 查询历史办理记录*/
	public static final String URL_HISTORYORDER = URL_Base + "zs/hisOrder.html";
	/**通知查询接口**/
	public final static String URL_GET_NOTICEINFO = "http://app.youmsj.cn/" + "zs/notice.html"; //      yyzs/cms/notice.html
	/** 上送图片接口 */
	public static final String URL_SENDPIC = URL_Base + "yyzs/cb/pz.html";
	
	/** 协议接口 */
	public static final String URL_XIEYI = URL_Base + "yyzs/cms/xy.html";
	
	/** 应用信息 */
	public static APP_Info app_Info = new APP_Info();
	/** 版本检测**/
	public static Updata_Info updata_Info = new Updata_Info();
	/** SD卡位置 **/
	public final static String SD_PATH_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
	public final static String APP_PATH = SD_PATH_ROOT + "SSPAZS/";
	public final static String APP_APP_PATH = APP_PATH + "app/";
	public final static String APP_DOWN_PATH = APP_PATH + "down/";
	
	
	public static User_Info user_info;
	
	/** 屏幕信息 **/
	public static DisplayMetrics dm;
	
	/**
	 * 打印日志
	 */
	public static void Log(String clas, String info) {
			System.out.println("SPB---" + clas + ":" + info);
	}
	
	
	/**
	 * 通过post请求,MAP集合可以存放多项数据去请求
	 * 
	 * @param url丶POST地址
	 * @param map丶需要POST的MAP对象
	 *            （键值对）
	 * @return String丶字符串
	 * @throws Exception
	 */
	public static String sendPost(HttpClient hc, String url, Map<String, String> map) throws Exception {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		Set<String> keys = map.keySet();

		for (String key : keys) {// 对set里面的键进行遍历
			String value = map.get(key);// 访问每个键的值
			Log("key...value=", key + "..." + value);
			// System.out.println("key:" + key + "...value:" + value);
			pairs.add(new BasicNameValuePair(key, value));// 向list里面添加BasicNameValuePair
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, "UTF-8");
		post.setEntity(entity);// 将数据和请求绑定。
		HttpResponse response = hc.execute(post);
		HttpEntity httpEntity = response.getEntity();
		String result = EntityUtils.toString(httpEntity, "UTF-8");
		return result;
	}
	
	/**
	 * 获取需要POST的参数MAP
	 * 
	 * @param type
	 *            1、检测更新，2、主界面 ，3、用户登录， 4、获取单个服务 ，5、生成订单 ，6、用户反馈，7、订单查询
	 * @return
	 */
	public static HashMap<String, String> Get_Info_Map(String name,String phone) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("phone", phone);
		return map;
	}
	
	public static HashMap<String, File> Get_Img_Map(File img1,File img2) {
		HashMap<String, File> map = new HashMap<String, File>();
		map.put("img1", img1);
		map.put("img2", img2);
		return map;
	}
	
	/**
     * 通过拼接的方式构造请求内容，实现参数传输以及文件传输
     * 
     * @param url Service net address
     * @param params text content
     * @param files pictures
     * @return String result of Service response
     * @throws IOException
     */
    public static String post(String url, Map<String, String> params, Map<String, File> files)
            throws IOException {
        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";


        URL uri = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setReadTimeout(10 * 1000); // 缓存的最长时间
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestMethod("POST");
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);


        // 首先组拼文本类型的参数
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);
            System.out.println("entry.getKey():"+entry.getKey());
            System.out.println("entry.getValue():"+entry.getValue());
            sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
            sb.append(LINEND);
            sb.append(entry.getValue());
            sb.append(LINEND);
        }


        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        outStream.write(sb.toString().getBytes());
        // 发送文件数据
        if (files != null)
            for (Map.Entry<String, File> file : files.entrySet()) {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(PREFIX);
                sb1.append(BOUNDARY);
                sb1.append(LINEND);
                System.out.println("file.getKey():"+file.getKey());
                System.out.println("file.getValue():"+file.getValue().getName());
                sb1.append("Content-Disposition: form-data; name=\"" + file.getKey() + "\"; filename=\""
                        + file.getValue().getName() + "\"" + LINEND);
                sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
                sb1.append(LINEND);
                outStream.write(sb1.toString().getBytes());


                InputStream is = new FileInputStream(file.getValue());
                byte[] buffer = new byte[1024*8];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }


                is.close();
                outStream.write(LINEND.getBytes());
            }


        // 请求结束标志
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        outStream.write(end_data);
        outStream.flush();
        // 得到响应码
        int res = conn.getResponseCode();
        InputStream in = conn.getInputStream();
        StringBuilder sb2 = new StringBuilder();
        if (res == 200) {
            int ch;
            while ((ch = in.read()) != -1) {
                sb2.append((char) ch);
            }
        }
        System.out.println("outStream:"+outStream);
        outStream.close();
        conn.disconnect();
        System.out.println("sb2.toString():"+sb2.toString());
        return sb2.toString();
    }
    
    
    /** 
     * 从本地读取图片,通过路径，获得bitmap 
     * @param path 图片路径 
     * @return Bitmap 
     */  
    public static Bitmap getBitmapFromLocal(String pathName){  
//        Bitmap bitmap = BitmapFactory.decodeFile(pathName);  
//        return bitmap;
    	final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);

        // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, 480, 800);
	
	        // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	
	    return BitmapFactory.decodeFile(pathName, options);
    }  
    
  //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 4;

        if (height > reqHeight || width > reqWidth) {
                 final int heightRatio = Math.round((float) height/ (float) reqHeight);
                 final int widthRatio = Math.round((float) width / (float) reqWidth);
                 inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
            return inSampleSize;
    }
    
    /** 
     * 通过给出的bitmap进行质量压缩 
     * @param bitmap 
     * @return 
     */  
    @SuppressLint("NewApi") 
    public static Bitmap compressBitmap(Bitmap bitmap){  
        System.out.println("bitmap=="+bitmap.getByteCount());  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        //通过这里改变压缩类型，其有不同的结果  
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bos);  
        System.out.println("bos====="+bos.size());  
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());  
        System.out.println("bis====="+bis.available());  
        return BitmapFactory.decodeStream(bis);  
    }  
    /** 
     * 通过给出的图片路径进行图片压缩 
     * @param pathName 
     * @return 
     */  
    public static Bitmap compressBitmap(String pathName){  
        return compressBitmap(getBitmapFromLocal(pathName));  
    }  
     
    /** 
     * 通过bitmap得到输出流（无压缩形式） 
     * @param bitmap bitmap对象 
     * @return OutputStream 
     */  
    public static ByteArrayOutputStream getOutStreamFromBitmap(Bitmap bitmap){  
         return getOutStreamFromBitmap( bitmap,100);  
    }  
    /** 
     * 通过bitmap得到输出流（质量压缩） 
     * @param bitmap bitmap对象 
     * @param quality 要压缩到的质量（0-100） 
     * @return OutputStream 
     */  
    public static ByteArrayOutputStream getOutStreamFromBitmap(Bitmap bitmap,int quality){  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);  
        if(bos != null){  
            try {  
                bos.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return bos;  
    }  
    /** 
     * 通过流获得bitmap 
     * @param os 输出流 
     * @return Bitmap 
     */  
    public static Bitmap getBitmapFromOutStream(ByteArrayOutputStream os){  
        ByteArrayInputStream bis = new ByteArrayInputStream(os.toByteArray());  
            if(bis !=null){  
                try {  
                    bis.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }         
            }  
        return  BitmapFactory.decodeStream(bis);  
    }  
    /** 
     * 通过路径得到图片并对图片进行压缩，并再生成图片（质量压缩） 
     * @param imagePath 图片的路径 
     * @param savePath 新图片的保存路径 
     * @param quality 要压缩到的质量 
     * @return Boolean true 成功false失败 
     */  
    public static boolean writeCompressImage2File(String imagePath ,String savePath,int quality){  
//        if(TextUtils.isEmpty(imagePath)){  
//            return false;  
//        }  
        String imageName  = imagePath.substring(imagePath.lastIndexOf("/")+1,imagePath.lastIndexOf("."));  
        return writeImage2File(getOutStreamFromBitmap(getBitmapFromLocal(imagePath), quality),savePath ,imageName);  
    }  
    /** 
     * 把bitmap写入指定目录下，重新生成图片 
     * @param bitmap bitmap对象 
     * @param savePath 新图片保存路径 
     * @param imageName 新图片的名字，会根据时间来命名 
     * @return Boolean true 成功false失败 
     */  
    public static boolean writeImage2File(Bitmap bitmap ,String savePath,String imageName){  
        return writeImage2File(getOutStreamFromBitmap(bitmap),savePath ,imageName);  
    }  
    /** 
     * 通过输出流，重组图片，并保存带指定目录下 
     * @param bos 图片输入流 
     * @param savePath 新图片的保存路径 
     * @param imageName 新图片的名字，字段为空时，会根据时间来命名 
     * @return Boolean true 成功false失败 
     */  
    public static boolean writeImage2File(ByteArrayOutputStream bos,String savePath,String imageName){  
//        if(TextUtils.isEmpty(savePath)){  
//            return false;  
//        }  
        File file =new File(savePath);  
        if(!file.exists()){  
            file.mkdirs();  
        }  
        FileOutputStream fos;  
        try {  
//            if(TextUtils.isEmpty(imageName)){  
//                imageName = System.currentTimeMillis()+"";  
//            }  
            File f = new File(file,imageName+".jpg");  
            fos = new FileOutputStream(f);  
            fos.write(bos.toByteArray());  
            fos.flush();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return false;  
    } 
    
    
    /**
	 * 应用信息对象
	 */
	@SuppressWarnings("serial")
	public static class APP_Info implements Serializable {
		private String vcode;// 版本号
		private String vname;// 版本代号
		private String pname;// 包名
		private String appname;// 应用名称

		/**
		 * 获取-----版本号
		 */
		public String getVcode() {
			return vcode;
		}

		/**
		 * 设置-----版本号
		 */
		public void setVcode(String vcode) {
			this.vcode = vcode;
		}

		/**
		 * 获取----- 版本代号
		 */
		public String getVname() {
			return vname;
		}

		/**
		 * 设置----- 版本代号
		 */
		public void setVname(String vname) {
			this.vname = vname;
		}

		/**
		 * 获取-----包名
		 */
		public String getPname() {
			return pname;
		}

		/**
		 * 设置-----包名
		 */
		public void setPname(String pname) {
			this.pname = pname;
		}

		/**
		 * 获取-----应用名称
		 */
		public String getAppname() {
			return appname;
		}

		/**
		 * 设置-----应用名称
		 */
		public void setAppname(String appname) {
			this.appname = appname;
		}
	}
	
	/**
	 * 更新信息对象
	 */
	@SuppressWarnings("serial")
	public static class Updata_Info implements Serializable {
		private String id;// 设备类型mach
		private String minver;// 最低版本
		private String maxver;// 最高版本
		private String uprand;// 兼容更新概率
		private String appname;// 应用包名
		private String vername;// 更新版本
		private String url;// 下载包地址
		private String url_img;// 二维码下载地址
		private String invite;// 短信分享内容
		private String mall_pack;// 商城包名
		private String mall_class;// 商城启动类名
		private String mall_url;// 商城下载地址
		private String dinfo;// 更新说明
		private String upstate;// 更新类型（0:不更新1：强制更新）
		private String info;// 订单返回信息
		private String status;// 订单返回状态码
		private String insur_info;// 用户须知

		/**
		 * 获取-----设备类型mach
		 */
		public String getId() {
			return id;
		}

		/**
		 * 设置-----设备类型mach
		 */
		public void setId(String id) {
			this.id = id;
		}

		/**
		 * 获取-----最低版本
		 */
		public String getMinver() {
			return minver;
		}

		/**
		 * 设置-----最低版本
		 */
		public void setMinver(String minver) {
			this.minver = minver;
		}

		/**
		 * 获取-----最高版本
		 */
		public String getMaxver() {
			return maxver;
		}

		/**
		 * 设置-----最高版本
		 */
		public void setMaxver(String maxver) {
			this.maxver = maxver;
		}

		/**
		 * 获取-----兼容更新概率
		 */
		public String getUprand() {
			return uprand;
		}

		/**
		 * 设置-----兼容更新概率
		 */
		public void setUprand(String uprand) {
			this.uprand = uprand;
		}

		/**
		 * 获取-----应用包名
		 */
		public String getAppname() {
			return appname;
		}

		/**
		 * 设置-----应用包名
		 */
		public void setAppname(String appname) {
			this.appname = appname;
		}

		/**
		 * 获取-----更新版本
		 */
		public String getVername() {
			return vername;
		}

		/**
		 * 设置-----更新版本
		 */
		public void setVername(String vername) {
			this.vername = vername;
		}

		/**
		 * 获取-----下载地址
		 */
		public String getUrl() {
			return url;
		}

		/**
		 * 设置-----下载地址
		 */
		public void setUrl(String url) {
			this.url = url;
		}

		/**
		 * 获取-----更新说明
		 */
		public String getDinfo() {
			return dinfo;
		}

		/**
		 * 设置-----更新说明
		 */
		public void setDinfo(String dinfo) {
			this.dinfo = dinfo;
		}

		/**
		 * 获取-----更新类型（0:不更新1：强制更新）
		 */
		public String getUpstate() {
			return upstate;
		}

		/**
		 * 设置-----更新类型（0:不更新1：强制更新）
		 */
		public void setUpstate(String upstate) {
			this.upstate = upstate;
		}

		/**
		 * 获取-----更新返回信息
		 */
		public String getInfo() {
			return info;
		}

		/**
		 * 设置-----更新返回信息
		 */
		public void setInfo(String info) {
			this.info = info;
		}

		/**
		 * 获取-----更新返回状态码
		 */
		public String getStatus() {
			return status;
		}

		/**
		 * 设置-----更新返回状态码
		 */
		public void setStatus(String status) {
			this.status = status;
		}

		/**
		 * 获取-----二维码下载地址
		 */
		public String getUrl_img() {
			return url_img;
		}

		/**
		 * 设置-----二维码下载地址
		 */
		public void setUrl_img(String url_img) {
			this.url_img = url_img;
		}

		/**
		 * 获取-----短信分享内容
		 */
		public String getInvite() {
			return invite;
		}

		/**
		 * 设置-----二维码下载地址
		 */
		public void setInvite(String invite) {
			this.invite = invite;
		}

		public String getMall_pack() {
			return mall_pack;
		}

		public void setMall_pack(String mall_pack) {
			this.mall_pack = mall_pack;
		}

		public String getMall_class() {
			return mall_class;
		}

		public void setMall_class(String mall_class) {
			this.mall_class = mall_class;
		}

		public String getMall_url() {
			return mall_url;
		}

		public void setMall_url(String mall_url) {
			this.mall_url = mall_url;
		}

		public String getInsur_info() {
			return insur_info;
		}

		public void setInsur_info(String insur_info) {
			this.insur_info = insur_info;
		}

	}
	
	/**
	 * 更新
	 * 
	 * @return Service_Info
	 */
	public static Updata_Info ParseJson_Updata(String jsonString) {
		Updata_Info updata_Info = new Updata_Info();
		if (!TextUtils.isEmpty(jsonString)) {
			JSONObject mainJson = null;
			try {
				mainJson = new JSONObject(jsonString);
				updata_Info.setInfo(mainJson.optString("info"));// 设置info
				updata_Info.setStatus(mainJson.optString("status"));// 设置
				// status
				JSONObject dataObject = mainJson.getJSONObject("data");
				updata_Info.setId(dataObject.optString("id"));
				updata_Info.setMinver(dataObject.optString("minver"));
				updata_Info.setMaxver(dataObject.optString("maxver"));
				updata_Info.setUprand(dataObject.optString("uprand"));
				updata_Info.setAppname(dataObject.optString("appname"));
				updata_Info.setInvite(dataObject.optString("invite"));
				updata_Info.setUrl_img(dataObject.optString("ewm"));
				updata_Info.setVername(dataObject.optString("vername"));
				updata_Info.setMall_pack(dataObject.optString("mall_pack"));
				updata_Info.setMall_class(dataObject.optString("mall_class"));
				updata_Info.setMall_url(dataObject.optString("mall_url"));
				updata_Info.setUrl(dataObject.optString("url"));
				updata_Info.setDinfo(dataObject.optString("info"));
				updata_Info.setUpstate(dataObject.optString("upstate"));
				updata_Info.setInsur_info(dataObject.optString("insur_info"));
			} catch (JSONException e) {
				e.printStackTrace();
				Utils.Log("ParseJson_Main", "解析【更新】出错");
			}

		}
		return updata_Info;
	}
	
	public static class Pay_Info implements Serializable {
		private String id;
		private String name;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	/**
	 * 设置Dialog为全屏显示
	 */
	public static void setDialogFullScreen(Dialog dialog) {
		try {
			WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
			params.width = dm.widthPixels;
			params.height = dm.heightPixels;
			dialog.getWindow().setAttributes(params);
		} catch (Exception e) {
			Utils.Log("setDialogFullScreen", "ERROR");
		}

	}
	
	/**
	 * 设置应用信息
	 * 
	 * @param activity
	 */
	public static void setAppInfo(Activity activity) {
		String packageName = activity.getPackageName();
		try {
			PackageManager packageManager = activity.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
			Utils.app_Info.setVcode(packageInfo.versionCode + "");
			Utils.app_Info.setVname(packageInfo.versionName);
			Utils.app_Info.setPname(packageName);
			Utils.app_Info.setAppname((String) packageManager.getApplicationLabel(activity.getApplicationInfo()));
		} catch (NameNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * 创建目录/mnt/sdcard/xxx
	 */
	public static void creatDir() {
		// 当下载到SD卡的目录不存在的时候，就创建 （/mnt/sdcard/xxx）
		File SDRoot = new File(APP_PATH);
		if (!SDRoot.exists()) {
			SDRoot.mkdir();
		} else {
			SDRoot = null;
		}
		// 创建SP文件夹
		File SDDir = new File(APP_DOWN_PATH);
		if (!SDDir.exists()) {
			SDDir.mkdir();
		} else {
			SDDir = null;
		}
		File SDApp = new File(APP_APP_PATH);
		if (!SDApp.exists()) {
			SDApp.mkdir();
		} else {
			SDApp = null;
		}

	}
	
	/**
	 * sdcard是否可读写
	 * @return
	 */
	public boolean IsCanUseSdCard() { 
	    try { 
	        return Environment.getExternalStorageState().equals( 
	                Environment.MEDIA_MOUNTED); 
	    } catch (Exception e) { 
	        e.printStackTrace(); 
	    } 
	    return false; 
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getFormatNowDate() {
		Date nowTime = new Date(System.currentTimeMillis());
		SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String retStrFormatNowDate = sdFormatter.format(nowTime);
		return retStrFormatNowDate;
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
	
	public static String md5(String string) {
	    byte[] hash;
	    
	    try {
	        hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException("Huh, MD5 should be supported?", e);
	    } catch (UnsupportedEncodingException e) {
	        throw new RuntimeException("Huh, UTF-8 should be supported?", e);
	    }

	    StringBuilder hex = new StringBuilder(hash.length * 2);
	    for (byte b : hash) {
	        if ((b & 0xFF) < 0x10) hex.append("0");
	        hex.append(Integer.toHexString(b & 0xFF));
	    }
	    return hex.toString();
	}
	
	// 获取IMEI
	public static String GetIMEIStr()
	{
		String Sspan_IMEI = null;
		
		if (Sspan_IMEI == null){
			TelephonyManager tm = (TelephonyManager) SspanApplication.mContext.getSystemService(Context.TELEPHONY_SERVICE);
			Sspan_IMEI = tm.getDeviceId();
		}
		return Sspan_IMEI;
	}
	
	// 获取手机号码
	public static String GetLineNumber()
	{
		String Sspan_PhoneNumber = null;
		
		if (Sspan_PhoneNumber == null)
		{
			TelephonyManager tm = (TelephonyManager) SspanApplication.mContext.getSystemService(Context.TELEPHONY_SERVICE);
			Sspan_PhoneNumber = tm.getLine1Number();
		}
		return Sspan_PhoneNumber;
	}
	
	public static void setDisplay_TextSize(TextView v, float size) {
		v.setTextSize(size);
	}
}
