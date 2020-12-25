package com.tchappy.photo.dao;

import java.util.ArrayList;
import java.util.List;

import com.tchappy.photo.db.SspanDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class LogicDao {
	private SQLiteTemplate mSqlTemplate;

	public LogicDao() {

	}

	public LogicDao(Context context) {
		super();
		mSqlTemplate = new SQLiteTemplate(SspanDatabase.getInstance(context)
				.getSQLiteOpenHelper());
	}

	/**
	 * 查询表
	 * 
	 * @param Selection
	 *            : 作为查询符合条件的过滤参数
	 * @param selectionArgs
	 *            :查询条件参数，配合 selection 参数使用
	 * @param table
	 * @param columns
	 * @return 返回cursor
	 */
	private final Cursor GetQueryTable(String table, String[] columns,
			String selection, String[] selectionArgs) {
		final Cursor c = mSqlTemplate.getDb(false).query(table, columns,
				selection, selectionArgs, null, null, null);
		return c;
	}

	/**
	 * 查询表,对其排序
	 * 
	 * @param table
	 * @param columns
	 * @param seleciton
	 * @param selectionArgs
	 * @param orderBy
	 * @return Cursor
	 */
	private final Cursor getQueryTable(String table, String[] columns,
			String selection, String[] selectionArgs, String orderBy) {
		final Cursor c = mSqlTemplate.getDb(false).query(table, columns,
				selection, selectionArgs, null, null, orderBy);
		return c;
	}
}
