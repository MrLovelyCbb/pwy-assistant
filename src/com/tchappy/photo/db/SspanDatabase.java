package com.tchappy.photo.db;


import java.io.File;

import com.tchappy.photo.db.SspanContentBase.RepairHistoryListTable;
import com.tchappy.photo.db.SspanContentBase.RewardListTable;
import com.tchappy.photo.db.SspanContentBase.SalesListTable;
import com.tchappy.photo.db.SspanContentBase.ServicePayTable;
import com.tchappy.photo.db.SspanContentBase.UserTable;
import com.tchappy.photo.db.SspanContentBase.YJingTable;
import com.tchappy.photo.db.SspanContentBase.YjingHistoryListTable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.database.sqlite.SQLiteDatabase.CursorFactory;




public class SspanDatabase {
	private static final String TAG = SspanDatabase.class.getSimpleName();
	private static final String DATABASE_NAME = "sspan_realse.db";
	
	public static final int DATABASE_VERSION = 1;
	
	private static SspanDatabase sInstance = null;
	
	private DatabaseHelper mOpenHelper = null;
	
	private Context mContext = null;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME,null, DATABASE_VERSION);
		}
		
		public DatabaseHelper(Context context,String name, CursorFactory factory, int version){
			super(context, name, factory, version);
		}
		
		@SuppressWarnings("unused")
		public DatabaseHelper(Context context, String name) {
			this(context, name, DATABASE_VERSION);
		}

		@SuppressWarnings("unused")
		public DatabaseHelper(Context context, int version) {
			this(context, DATABASE_NAME, null, version);
		}
		
		public DatabaseHelper(Context context, String name, int version) {
			this(context, name, null, version);
		}

		
		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(TAG, "Create Database.");
			// TODO: create tables
			createAllTables(db);   //创建所有表
		}
		
		@Override
		public synchronized void close() {
			Log.d(TAG, "Close Database.");
			super.close();
		}
		
		@Override
		public void onOpen(SQLiteDatabase db) {
			Log.d(TAG, "Open Database.");
			super.onOpen(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d(TAG, "Upgrade Database.");
			dropAllTables(db);
			upgradeAllTables(db);
//			onCreate(db);
		}
		
		/**
		 * 升级所有表结构
		 * @param db
		 */
		private static void upgradeAllTables(SQLiteDatabase db) {
			
		}
		
		/**
		 * 删除所有表结构
		 * @param db
		 */
		private static void dropAllTables(SQLiteDatabase db) {
//			db.execSQL(ForumTable.getDropSQL());
		}

		/**
		 * 创建所有表结构
		 * @param db
		 */
		private static void createAllTables(SQLiteDatabase db) {
			db.execSQL(UserTable.getCreateSQL());				// 板块类表
			db.execSQL(YJingTable.getCreateSQL());				// 佣金账号列表
//			db.execSQL(SearchKeyWordTable.getCreateSQL());		// 搜索关键字
			db.execSQL(ServicePayTable.getCreateSQL());			// 支付服务列表
			db.execSQL(SalesListTable.getCreateSQL());			// 营销员子集孙集列表
//			db.execSQL(TodayListTable.getCreateSQL());
			db.execSQL(RewardListTable.getCreateSQL());			// 奖励佣金列表 /ReWorad
			db.execSQL(YjingHistoryListTable.getCreateSQL());	// 佣金结算历史表
			db.execSQL(RepairHistoryListTable.getCreateSQL());  // 报修历史查询
		}
		
		/**
		 * 清空所有表数据
		 * @param db
		 */
		@SuppressWarnings("unused")
		public static void ClearAllTables(SQLiteDatabase db){
			db.execSQL("DELETE FROM " + UserTable.TABLE_NAME);
			db.execSQL("DELETE FROM " + YJingTable.TABLE_NAME);
//			db.execSQL("DELETE FROM " + SearchKeyWordTable.TABLE_NAME);
			db.execSQL("DELETE FROM " + ServicePayTable.TABLE_NAME);
			db.execSQL("DELETE FROM " + SalesListTable.TABLE_NAME);
			db.execSQL("DELETE FROM " + RewardListTable.TABLE_NAME);
			db.execSQL("DELETE FROM " + YjingHistoryListTable.TABLE_NAME);
			db.execSQL("DELETE FROM " + RepairHistoryListTable.TABLE_NAME);
		}

	}
	
	private SspanDatabase(Context context) {
		mContext = context;
		mOpenHelper = new DatabaseHelper(context);
	}
	
	public static synchronized SspanDatabase getInstance(Context context) {
		if (null == sInstance) {
			sInstance = new SspanDatabase(context);
		}
		return sInstance;
	}
	
	public SQLiteOpenHelper getSQLiteOpenHelper() {
		return mOpenHelper;
	}
	
	public SQLiteDatabase getDb(boolean writeable) {
		if (writeable) {
			return mOpenHelper.getWritableDatabase();
		} else {
			return mOpenHelper.getReadableDatabase();
		}
	}
	
	public void close() {
		if (null != sInstance) {
			mOpenHelper.close();
			sInstance = null;
		}
	}

	/**
	 * 删除数据库文件
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean deletedatabase(){
		File file = mContext.getDatabasePath(DATABASE_NAME);
		return file.delete();
	}
	/**
	 * 判断数据库文件是否存在
	 */
	public boolean DbIsExist(){
		File file = mContext.getDatabasePath(DATABASE_NAME);
		return file.exists();
	}
	
	/**
	 * 
	 */
	public String DbForumCount(){
		SQLiteDatabase db=this.getDb(false);
		String sql = "SELECT count(*) FROM t_sgforum";
		String dbforumcount = "";
		final Cursor cursor = db.rawQuery(sql, null);
		
		if(cursor.moveToFirst()){
			dbforumcount = cursor.getString(0);
		}
		
		return dbforumcount;
	}
}
