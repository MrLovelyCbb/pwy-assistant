package com.tchappy.photo.db;

public class SspanContentBase {

	public static class UserTable {
		public static final String TABLE_NAME = "t_user";

		public static class Columns {
			public static final String ID = "_id";// 唯一id顺序ID
			public static final String UID = "uid";// 用户唯一ID
			public static final String UPHONE = "uphone";// 电话号码
			public static final String UNAME = "uname";// 名字
			public static final String UINVITED_PHONE = "uinvited_phone"; // 推荐人电话号码
		}
		//用户表
		public static String getCreateSQL() {
			String createString = TABLE_NAME + "(" + Columns.ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + Columns.UID
					+ " TEXT, " + Columns.UPHONE
					+ " TEXT, " + Columns.UNAME
					+ " TEXT, " + Columns.UINVITED_PHONE
					+ " TEXT"   + ");";
			System.out.println("/////////////////"+"CREATE TABLE "+createString);
			return "CREATE TABLE "+createString;
		}
		//删除用户表
		public static String getDropSQL() {
			return "DROP TABLE IF EXISTS " + TABLE_NAME;
		}
		
		public static String[] getIndexColumns(){
			return new String[] {
					Columns.ID,
					Columns.UID,
					Columns.UPHONE,
					Columns.UNAME,
					Columns.UINVITED_PHONE
			};
		}
	}
	// 佣金账号列表
	public static class YJingTable{
		public static final String TABLE_NAME = "t_yjlist";
		public static class Columns{
			public static final String ID = "_id";					// 自增ID
			public static final String SID = "sid";					// 销售员ID
			public static final String UNAME = "uname";				// 姓名
			public static final String ACCOUNT_PHONE = "account_phone";		// 用户电话账号
			public static final String IS_DEFAULT = "is_default";	// 是否是默认账号
		}
		
		public static String getCreateSQL() {
			String createString = TABLE_NAME + "("+Columns.ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + Columns.SID
					+ " TEXT, " + Columns.UNAME
					+ " TEXT, " + Columns.ACCOUNT_PHONE
					+ " TEXT, " + Columns.IS_DEFAULT
					+ " TEXT " + ");";
			return "CREATE TABLE "+createString;
		}
		
		public static String getDropSQL() {
			return "DROP TABLE IF EXISTS " + TABLE_NAME;
		}
		
		public static String[] getIndexColumns() {
			return new String[] {
					Columns.ID,
					Columns.SID,
					Columns.UNAME,
					Columns.ACCOUNT_PHONE,
					Columns.IS_DEFAULT
			};
		}
	}
	// 支付服务列表
	public static class ServicePayTable{
		public static final String TABLE_NAME = "t_service_pay";
		public static class Columns{
			public static final String ID = "_id";				//自增ID
			public static final String SID = "sid";				// ID
			public static final String SNAME = "sname";			// 服务器名
			public static final String PRICE = "price";			// 价格
			public static final String XB_PRICE = "xb_price";	// 续保价格
			public static final String PAY_TYPE = "type";		// 支付方式  1.银联 2.支付宝 3.Appstore支付
		}
		
		public static String getCreateSQL() {
			String createString = TABLE_NAME + "("+ Columns.ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + Columns.SID
					+ " TEXT, " + Columns.SNAME
					+ " TEXT, " + Columns.PRICE
					+ " TEXT, " + Columns.XB_PRICE
					+ " TEXT, " + Columns.PAY_TYPE
					+ " TEXT " + ");";
			return "CREATE TABLE "+createString;
		}
		
		public static String getDropSQL() {
			return "DROP TABLE IF EXISTS " + TABLE_NAME;
		}
		
		public static String[] getIndexColumns() {
			return new String[] {
					Columns.ID,
					Columns.SID,
					Columns.SNAME,
					Columns.PRICE,
					Columns.XB_PRICE,
					Columns.PAY_TYPE
			};
		}
		
	}
	
	//搜索  关键字
	public static class SearchKeyWordTable{
		public static final String TABLE_NAME = "t_searchkey";
		public static class Columns{
			public static final String ID = "_id";//关键字自增ID
			public static final String KeyWord = "keyword";//关键字
		}
		public static String getCreateSQL() {
			String createString = TABLE_NAME + "(" + Columns.ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT , "+Columns.KeyWord
					+ " TEXT "
					+ ");";
			return "CREATE TABLE "+createString;
		}
		
		public static String getDropSQL(){
			return "DROP TABLE IF EXISTS " + TABLE_NAME;
		}
		public static String[] getIndexColumns() {
			return new String[] {
					Columns.ID,
					Columns.KeyWord
			};
		}
	}
	
	// 营销员子集孙集列表
	public static class SalesListTable{
		public static final String TABLE_NAME = "t_saleslist";
		public static class Columns{
			public static final String ID = "_id";
			public static final String SID = "sid";
			public static final String UNAME = "uname";
			public static final String UPHONE = "uphone";
			public static final String MONEY = "money";
		}
		
		public static String getCreateSQL() {
			String createString = TABLE_NAME+"("+Columns.ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ Columns.SID+" TEXT, "
					+Columns.UNAME
					+" TEXT, "+Columns.UPHONE
					+" TEXT, "+Columns.MONEY
					+" TEXT "+");";
			return "CREATE TABLE "+createString;
		}
		public static String getDropSQL(){
			return "DROP TABLE IF EXISTS " + TABLE_NAME;
		}
		public static String[] getIndexColumns() {
			return new String[] {
					Columns.ID,
					Columns.SID,
					Columns.UNAME,
					Columns.UPHONE,
					Columns.MONEY
			};
		}
	}
	
	// 今日佣金列表
	public static class TodayListTable {
		public static final String TABLE_NAME = "t_todaylist";
		public static class Columns {
			public static final String ID = "_id";
			public static final String TID = "tid";
		}
	}
	
	// 奖励佣金列表 /ReWorad
	public static class RewardListTable {
		public static final String TABLE_NAME = "t_rewardlist";
		public static class Columns {
			public static final String ID = "_id";
			public static final String MONTH = "month";
			public static final String MONEY = "money";
			public static final String CB_NUM = "cb_num";
			public static final String STATUS = "status";
			
		}
		
		public static String getCreateSQL(){
			String createString = TABLE_NAME+"("+Columns.ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ Columns.MONTH+" TEXT, "
					+Columns.MONEY
					+" TEXT, "+Columns.CB_NUM
					+" TEXT, "+Columns.STATUS
					+" TEXT "+");";
			return "CREATE TABLE "+createString;
		}
		public static String getDropSQL(){
			return "DROP TABLE IF EXISTS " + TABLE_NAME;
		}
		public static String[] getIndexColumns() {
			return new String[] {
					Columns.ID,
					Columns.MONTH,
					Columns.MONEY,
					Columns.CB_NUM,
					Columns.STATUS
			};
		}
	}
	// 佣金结算历史表
	public static class YjingHistoryListTable {
		public static final String TABLE_NAME = "t_yj_histroylist";
		public static class Columns {
			public static final String ID = "_id";
			public static final String MONTH = "month";
			public static final String MONEY = "money";
			public static final String STATUS = "status";
			
		}
		public static String getCreateSQL(){
			String createString = TABLE_NAME+"("+Columns.ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ Columns.MONTH+" TEXT, "
					+Columns.MONEY
					+" TEXT, "+Columns.STATUS
					+" TEXT "+");";
			return "CREATE TABLE "+createString;
		}
		public static String getDropSQL(){
			return "DROP TABLE IF EXISTS " + TABLE_NAME;
		}
		public static String[] getIndexColumns() {
			return new String[] {
					Columns.ID,
					Columns.MONTH,
					Columns.MONEY,
					Columns.STATUS
			};
		}
	}
	
	// 报修历史查询
	public static class RepairHistoryListTable {
		public static final String TABLE_NAME = "t_repair_histroylist";
		public static class Columns {
			public static final String ID = "_id";
			public static final String UNAME = "uname";
			public static final String UPHONE = "uphone";
			public static final String ADD_TIME = "add_time";
			public static final String KD_TIME = "kd_time";
			
		}
		public static String getCreateSQL(){
			String createString = TABLE_NAME+"("+Columns.ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ Columns.UNAME+" TEXT, "
					+Columns.UPHONE
					+" TEXT, "+Columns.ADD_TIME
					+" TEXT, "+Columns.KD_TIME
					+" TEXT "+");";
			return "CREATE TABLE "+createString;
		}
		public static String getDropSQL(){
			return "DROP TABLE IF EXISTS " + TABLE_NAME;
		}
		public static String[] getIndexColumns() {
			return new String[] {
					Columns.ID,
					Columns.UNAME,
					Columns.UPHONE,
					Columns.ADD_TIME,
					Columns.KD_TIME
			};
		}
	}
	
	//个人资料
	public static class ProfileTable{
		public static final String TABLE_NAME = "t_profile";
		public static class Columns{
			public static final String ID = "_id";//个人资料id
			public static final String UID = "uid";//用户唯一id
			public static final String Username = "username";//用户名
			public static final String User_Level = "level";//用户等级即用户组
			public static final String User_Integral = "integral";//用户积分
			public static final String User_Posts_count = "posts_count";//帖子数量
			public static final String User_Gold = "gold";//用户金币
			public static final String User_Popular = "popular";//用户人气值
			public static final String User_OnLine = "online";//在线时间
			public static final String User_LastLine = "lastline";//最后在线
		}
		
		public static String getCreateSQL() {
			String createString = TABLE_NAME+"("+Columns.ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ Columns.UID+" TEXT, "
					+Columns.Username
					+" TEXT, "+Columns.User_Level
					+" TEXT, "+Columns.User_Integral
					+" TEXT, "+Columns.User_Posts_count
					+" TEXT, "+Columns.User_Gold
					+" TEXT, "+Columns.User_Popular
					+" TEXT, "+Columns.User_OnLine
					+" TEXT, "+Columns.User_LastLine
					+" TEXT "+");";
			return "CREATE TABLE "+createString;
		}
		public static String getDropSQL(){
			return "DROP TABLE IF EXISTS " + TABLE_NAME;
		}
		public static String[] getIndexColumns() {
			return new String[] {
					Columns.ID,
					Columns.UID,
					Columns.Username,
					Columns.User_Level,
					Columns.User_Integral,
					Columns.User_Posts_count,
					Columns.User_Gold,
					Columns.User_Popular,
					Columns.User_OnLine,
					Columns.User_LastLine
			};
		}
	}
}
