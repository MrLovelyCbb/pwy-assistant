package com.tchappy.photo.thread;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.SharedPreferences;

import com.tchappy.photo.http.JsonHttpResponseHandler;
import com.tchappy.photo.http.RequestHandle;
import com.tchappy.photo.http.RequestParams;
import com.tchappy.photo.http.ResponseHandlerInterface;
import com.tchappy.photo.util.Utils;

public class HttpAsyncLogic extends AsyncHttpSupportBase{

	private static final String HTTP_URLBASE= "http://pwy.youmsj.cn";             //     http://pwy.youmsj.cn
	
	public HttpAsyncLogic(){
		super();
	}
	
	// 创建通用参数加密
	private RequestParams createBaseParams(){
		RequestParams ReqParams = new RequestParams();
		ReqParams.put("arr1", 1);
		ReqParams.put("arr2", (int)(System.currentTimeMillis() * 0.001));
		ReqParams.put("arr3", Utils.GetIMEIStr());
		ReqParams.put("arr4", 0);
		ReqParams.put("arr5", 0);
		ReqParams.put("arr6", "18086485676");
		String arr7 = Utils.md5(ReqParams.toDesString().replaceAll("[(&)|(=)|(STREAM)|(FILE)]+", ""));
		ReqParams.put("arr7", arr7);
		ReqParams.put("arr8", 0);
		return ReqParams;
	}
	
	/**
	 * Start arr10 begin Parameters
	 * @param params
	 * @return
	 */
	public RequestParams createParams(String... params){
		RequestParams ReqParams = createBaseParams();
		for (int i = 0; i< params.length; i++)
		{
			ReqParams.put("arr"+"1"+i, params[i]);
		}
		return ReqParams;
	}
	
	// Login HTTP
	public void ReqLogin(String username,String password,ResponseHandlerInterface responseHandler)
	{
		String loginUrl = HTTP_URLBASE + "/yyzs/account/login.html";
		RequestParams reqParams = createParams(username,password);
		System.out.println("Login Url================"+loginUrl);
		System.out.println("Params------------------"+reqParams);
		http.get(loginUrl,reqParams,responseHandler);
	}
	
		
	/**
	 * Req Register 
	 * @param createParams <br>
	 *  arr10	手机号码	<br>	
		arr11	密码	</br>
		arr12	短信验证码	</br>
		arr13	lng	经度	</br>
		arr14	lat	纬度	</br>
		arr15	true_name	真实姓名	</br>
		arr16	invite	介绍人手机号	</br>
	 */
	public void ReqRegister(String account,String password,String smscode,String lng,String lat,String trueName,String invite,ResponseHandlerInterface responseHandler)
	{
		String regUrl = HTTP_URLBASE + "/yyzs/account/register.html";// 117.963984 //28.420003
		RequestParams reParams = createParams(account,password,smscode,lng,lat,trueName,invite);
		System.out.println("RequestParams ReqRegister----------"+reParams.toString());
		http.post(regUrl, reParams, responseHandler);
	}
	
	/**
	 * Get ValiCode
	 * @param createParams  </br>
	 * arr10	手机号码		</br>
	   arr11	类型	1：注册账号 2：找回密码
	 */
	public void ReqGetValiCode(String account,int type,ResponseHandlerInterface responseHandler)
	{
		String codeUrl = HTTP_URLBASE + "/yyzs/account/getcode.html";
		RequestParams repParams = createParams(account,type+"");
		System.out.println("RequestParams GetValiCode----------"+repParams.toString());
		http.get(codeUrl, repParams, responseHandler);
	}
	
	
	/**
	 * 检查手机号是否已经被注册
	 * @param phone
	 */
	public void ReqCheckPhone(String phone,ResponseHandlerInterface responseHandler)
	{
		String url = HTTP_URLBASE + "/yyzs/account/checkPhone.html";
		RequestParams repParams = createParams(phone);
		http.post(url,repParams,responseHandler);
	}
	
	
	/**
	 * 找回密码密码
	 * @param phone
	 * @param newPwd
	 * @param smsCode
	 * @param responseHandler
	 */
	public void ReqFindPwd(String phone,String newPwd,String smsCode,ResponseHandlerInterface responseHandler)
	{
		String url = HTTP_URLBASE + "/yyzs/account/forgetPassword.html";
		RequestParams repParams = createParams(phone,newPwd,smsCode);
		http.post(url,repParams,responseHandler);
	}
	
	/**
	 * 修改推荐人
	 * @param sealerid
	 * @param phone
	 * @param responseHandler
	 */
	public void ReqModifyInvite(String sealerid,String phone,ResponseHandlerInterface responseHandler)
	{
		String url = HTTP_URLBASE + "/yyzs/account/setinvite.html";
		RequestParams repParams = createParams(sealerid,phone);
		http.post(url,repParams,responseHandler);
	}
	
	/**
	 * 修改密码
	 * @param sealerid
	 * @param oldPwd
	 * @param newPwd
	 * @param responseHandler
	 */
	public void ReqModifyPwd(String sealerid,String oldPwd,String newPwd,ResponseHandlerInterface responseHandler)
	{
		String url = HTTP_URLBASE + "/yyzs/account/resetPassword.html";
		RequestParams repParams = createParams(sealerid,oldPwd,newPwd);
		System.out.println("ReqModifyPwd-------------------"+repParams.toString());
		http.post(url,repParams,responseHandler);
	}
	
	/**
	 * 佣金账号列表
	 * @param sealerid
	 */
	public void ReqYJAccountList(String sealerid,ResponseHandlerInterface responseHandler)
	{
		String url = HTTP_URLBASE + "/yyzs/account/yjAccountList.html";
		RequestParams repParams = createParams(sealerid);
		http.get(url,repParams,responseHandler);
	}
	
	/**
	 * 添加佣金账号
	 * @param sealerid
	 * @param Uname
	 * @param Account
	 */
	public void ReqAddAccount(String sealerid,String Uname,String Account,ResponseHandlerInterface responseHandler)
	{
		String url = HTTP_URLBASE + "/yyzs/account/yjAccountAdd.html";
		RequestParams repParams = createParams(sealerid,Uname,Account);
		http.post(url,repParams,responseHandler);
	}
	
	/**
	 * 修改佣金账号
	 * @param sealerid
	 * @param yjmoneyid
	 * @param Uname
	 * @param Account
	 * @param isDefault
	 * @param responseHandler
	 */
	public void ReqModifyAccount(String sealerid,String yjmoneyid,String Uname,String Account,String isDefault,ResponseHandlerInterface responseHandler)
	{
		String url = HTTP_URLBASE + "/yyzs/account/yjAccountDel.html";
		RequestParams repParams = createParams(sealerid,yjmoneyid,Uname,Account,isDefault);
		http.post(url,repParams,responseHandler);
	}
	
	/**
	 * 删除佣金账号
	 * @param sealerid
	 * @param yjmoneyid
	 */
	public void ReqDelAccount(String sealerid,String yjmoneyid,ResponseHandlerInterface responseHandler)
	{
		String url = HTTP_URLBASE + "/yyzs/account/yjAccountDel.html";
		RequestParams repParams = createParams(sealerid,yjmoneyid);
		http.post(url,repParams,responseHandler);
	}
	
	/**
	 * 用户拍照上传
	 * @param sealerid
	 * @param Uname
	 * @param uPhone
	 * @param pic
	 * @param pic1
	 * @param responseHandler
	 */
	public void ReqUploadPhoto(String sealerid,String Uname,String uPhone,String pic,String pic1,ResponseHandlerInterface responseHandler)
	{
		String url = HTTP_URLBASE + "/yyzs/cb/pz.html";
		RequestParams repParams = createParams(sealerid,Uname,uPhone,pic,pic1);
		http.post(url,repParams, responseHandler);
	}
	
	/**
	 * 服务列表
	 * @param responseHandler
	 */
	public void ReqServiceList(ResponseHandlerInterface responseHandler)
	{
		String url = HTTP_URLBASE + "/yyzs/cb/serviceList.html";
		http.get(url,createBaseParams(), responseHandler);
	}
	
	/**
	 * 生成订单
	 * @param sealerid
	 * @param userid
	 * @param serviceid
	 * @param money
	 * @param sp
	 */
	public void ReqGeneralOrder(String sealerid,String userid,String serviceid,String money,String sp,ResponseHandlerInterface responseHandler)
	{
		String url = HTTP_URLBASE + "/yyzs/order/generalOrder.html";
		RequestParams repParams = createParams(sealerid,userid,serviceid,money,sp);
		http.get(url,repParams,responseHandler);
	}
	
	/**
	 * 查询订单
	 * @param orderid
	 * @param responseHandler
	 */
	public void ReqQueryOrder(String orderid,ResponseHandlerInterface responseHandler)
	{
		String url = HTTP_URLBASE + "/yyzs/order/searchorder.html";
		RequestParams repParams = createParams(orderid);
		http.get(url,repParams,responseHandler);
	}
	
	
	/**
	 * 请求查询参保人
	 * @param Uname
	 * @param Uphone
	 */
	public void ReqQueryCanBao(String Uname,String Uphone,ResponseHandlerInterface responseHandler)
	{
		String reqQueryUrl = HTTP_URLBASE + "/yyzs/bx/search.html";
		RequestParams repParams = createParams(Uname,Uphone);
		http.get(reqQueryUrl,repParams,responseHandler);
	}
	
	/**
	 * 为客户保修
	 * @param seallerid  		用户id
	 * @param userid 	   		客户id
	 * @param userPhone  		客户联系电话
	 * @param userAdress 		客户联系地址
	 * @param userDescription	问题描述
	 * @param responseHandler
	 */
	public void ReqReportRepair(String seallerid,String userid,String userPhone,String userAdress,String userDescription,ResponseHandlerInterface responseHandler) 
	{
		String reqReportUrl = HTTP_URLBASE + "/yyzs/bx/bxpost.html";
		RequestParams repParams = createParams(seallerid,userid,userPhone,userAdress,userDescription);
		http.get(reqReportUrl, repParams, responseHandler);
	}
	
	/**
	 * 直销员列表
	 * @param seallerid
	 * @param phone
	 * @param pages
	 */
	public void ReqQuerySalerList(String seallerid,String phone,String pages,ResponseHandlerInterface responseHandler) 
	{
		String reqSalerListUrl = HTTP_URLBASE + "/yyzs/tj/subSaleList.html";
		RequestParams repParams = createParams(seallerid,phone,pages);
		http.get(reqSalerListUrl, repParams, responseHandler);
	}
	
	/**
	 * 今日办理查询 
	 * @param sealerid
	 * @param responseHandler
	 */
	public void ReqQueryTodayList(String sealerid,ResponseHandlerInterface responseHandler)
	{
		String reqTodayList = HTTP_URLBASE + "/yyzs/tj/todaylist.html";
		RequestParams repParams = createParams(sealerid);
		http.get(reqTodayList, repParams, responseHandler);
	}
	
	/**
	 * 历史办理查询
	 * @param sealerid
	 * @param startTime
	 * @param endTime
	 * @param uName
	 * @param uPhone
	 * @param pages
	 * @param responseHandler
	 */
	public void ReqHistoryList(String sealerid,String startTime,String endTime,String uName,String uPhone,String pages,ResponseHandlerInterface responseHandler)
	{
		String reqHistoryList = HTTP_URLBASE + "/yyzs/tj/historylist.html";
		RequestParams repParams = createParams(sealerid,startTime,endTime,uName,uPhone,pages);
		http.get(reqHistoryList, repParams, responseHandler);
	}
	
	/**
	 * 已结算佣金结算历史查询
	 * @param sealerid
	 * @param startTime
	 * @param endTime
	 * @param lowMoney
	 * @param bigMoney
	 * @param types
	 * @param responseHandler
	 */
	public void ReqQueryResultHistory(String sealerid,String startTime,String endTime,String lowMoney,String bigMoney,String types,ResponseHandlerInterface responseHandler)
	{
		String reqResultHistoryList = HTTP_URLBASE + "/yyzs/tj/jxlist.html";
		RequestParams repParams = createParams(sealerid,startTime,endTime,lowMoney,bigMoney,types);
		http.get(reqResultHistoryList, repParams, responseHandler);
	}
	
	/**
	 * 报修历史查询
	 * @param sealerid
	 * @param startTime
	 * @param endTime
	 * @param uName
	 * @param uPhone
	 * @param pages
	 * @param responseHandler
	 */
	public void ReqRepairHistoryList(String sealerid,String startTime,String endTime,String uName,String uPhone,String pages,ResponseHandlerInterface responseHandler)
	{
		String reqRepairHistoryList = HTTP_URLBASE + "/yyzs/tj/bxlist.html";
		RequestParams repParams = createParams(sealerid,startTime,endTime,uName,uPhone,pages);
		http.get(reqRepairHistoryList, repParams, responseHandler);
	}
	
	/**
	 * 银联支付回调地址
	 * /yyzs/order/bankNotify.html
	 */
	public void ReqBankCallBack(){
		
	}
	
	/**
	 * 支付宝回调地址
	 * /yyzs/order/alipayNotify.html
	 */
	public void ReqAlipayCallBack(){
		
	}
	
	/**
	 * 微信回调地址
	 * /yyzs/order/wxNotify.html
	 */
	public void ReqWeixinCallBack(){
		
	}
	
	/**
	 * 苹果回调地址
	 * /yyzs/order/iosNotify.html
	 */
	public void ReqAppleCallBack(){
		
	}
	
	/**
	 * 用户协议
	 * /yyzs/cms/xy.html
	 */
	public void ReqUserXY(){
		
	}
	
	/**
	 * 关于岁屏保
	 * /yyzs/cms/about.html
	 */
	public void ReqUserAbout(){
		
	}
	
	/**
	 * 公告展示部分
	 * @param responseHandler
	 */
	public void ReqContentShow(ResponseHandlerInterface responseHandler)
	{
		String reqContentUrl = HTTP_URLBASE + "/yyzs/cms/notice.html";
		http.get(reqContentUrl, responseHandler);
	}
	
	/**
	 * 检查更新
	 * @param versionCode
	 */
	public void ReqUpdateInfo(String versionCode,ResponseHandlerInterface responseHandler)
	{
		String url = HTTP_URLBASE + "/yyzs/update.html";
		RequestParams repParams = createParams(versionCode);
		http.get(url, responseHandler);
	}
	
	/**
	 * 获取图片
	 */
	public void ReqGetImg(String url,ResponseHandlerInterface responseHandler)
	{
		http.get(url,responseHandler);
	}
}
