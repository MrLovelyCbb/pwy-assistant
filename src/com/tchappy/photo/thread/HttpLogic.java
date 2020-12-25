package com.tchappy.photo.thread;

import java.util.ArrayList;

import org.apache.http.HttpException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.text.TextUtils;

public class HttpLogic extends HttpSupportBase{

	private static final String URLBase = "http://app.youmsj.cn";
	
	private static final String URL_Login = URLBase + "/zs/login.html";
	
	public HttpLogic() {
		super();
	}
	

	/**
	 * @param nameValuePair
	 * @return
	 */
	public ArrayList<BasicNameValuePair> createParams(
			BasicNameValuePair... nameValuePair) {
		ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		for (BasicNameValuePair param : nameValuePair) {
			params.add(param);
		}
		return params;
	}
	
	/**
	 * 是否登录
	 * 
	 * @return
	 */
	public boolean isLoggedIn() {
		// HttpClient的userName&password是由TwitterApplication#onCreate
		// 从SharedPreferences中取出的，他们为空则表示尚未登录，因为他们只在验证
		// 账户成功后才会被储存，且注销时被清空。
		return isValidCredentials(http.getUserId(), http.getPassword());
	}
	
	/**
	 * 登陆请求
	 * @throws Exception 
	 */
	public void reqLoginPost() throws Exception {
			final HttpConnResponse connRes = http.post("http://app.youmsj.cn/zs/login.html", createParams(new BasicNameValuePair("arr10", "15927217486"),new BasicNameValuePair("arr11", "2")));
//			final HttpConnResponse connRes = http.get("https://httpbin.org/get");
			System.out.println("Login Response"+connRes.asJSONObject());
			
			JSONObject aa = connRes.asJSONObject().optJSONObject("data").optJSONObject("userInfo");
			
//			aa.get
			System.out.println("json-----"+aa.optString("id:::")+aa.optString("id")+"---true_name::"+aa.optString("true_name")+"---phone:::"+aa.optString("phone"));
			
	}
	
	
	/**
	 * 仅判断是否为空
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean isValidCredentials(String username, String password) {
		return !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password);
	}
	
	/**
	 * 是否注册
	 * @param username
	 * @return
	 */
	public void isRegisterCredentials(String username){
		
	}
	
	/**
	 * 登陆请求
	 * @param username
	 * @param password
	 * @return
	 */
	public void loginCredentials(String username,String password) {

	}
	
	/**
	 * 获取验证码
	 * @param username
	 * @param password
	 * @return
	 */
	public void loginValiNumber(String username,String password) {
	}
	
	/**
	 * 密码找回
	 * @param username
	 * @param valiCode
	 * @param newPwd
	 * @param confirmPwd
	 * @return
	 */
	public void findPassword(String username,String valiCode,String newPwd,String confirmPwd) {
	}
	
	/**
	 * 添加介绍人手机号
	 * @param username
	 * @return
	 */
	public void addFriendMobileNum(String username) {
		
	}
	
	/**
	 * 修改密码
	 * @param pwd
	 * @param newpwd
	 * @param newPwd2
	 * @return
	 */
	public void updatePwd(String pwd,String newPwd,String newPwd2) {

	}
	
	/**
	 * 佣金管理
	 * @return
	 */
	public void getCurrentCommManager() {

	}
	
	/**
	 * 手机拍照上传
	 */
	public void mobilePhotoUpload(String username,String password,String file,String file2) {
		
	}
	
	/**
	 * 获取订单号
	 * @param serverType
	 * @param price
	 * @param payType
	 */
	public void getPayOrderID(String serverType,String price,String payType){
		
	}
	
	/**
	 * 支付ID回调是否成功
	 * @param orderID
	 */
	public void payOrderIDCallBack(int orderID) {
		
	}
	
	/**
	 * 查询结果
	 * @param uname
	 * @param upwd
	 * @param imei
	 */
	public void repairQueryResult(String uname,String upwd,String imei) {
		
	}
	
	/**
	 * 发送报修申请
	 * @param ContentBody
	 * @param phoneNum
	 * @param adress
	 * @param sendAdress
	 */
	public void repairPost(String repairBody,String phoneNum,String adress,String sendAdress) {
		
	}
	
	/**
	 * 检查是否是参保用户
	 * @param uname
	 * @param upwd
	 */
	public void vlidateRepairUser(String uname,String upwd) {
		
	}
}
