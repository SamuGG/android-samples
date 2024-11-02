package com.samugg.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TableAdapter {

	private SQLiteDatabase database;
	private final TableSQLiteHelper tableHelper;
	public final String[] all_columns = {
		TableSQLiteHelper.COLUMN_ID,
		TableSQLiteHelper.COLUMN_NAME,
		TableSQLiteHelper.COLUMN_QUALIFICATION};

	public TableAdapter(Context context) {
		tableHelper = new TableSQLiteHelper(context);
	}

	public void Open() {
		database = tableHelper.getWritableDatabase();
	}
	
	public void Close() {
		database.close();
	}
	
	public void DeleteRecord(long pId) {
		database.delete(TableSQLiteHelper.TABLE_NAME, TableSQLiteHelper.COLUMN_ID + " = ?", new String[] { String.valueOf(pId) });
	}
	
	public void CreateNewRecord(String pName, int pQualification) {
		ContentValues values = new ContentValues();
		values.put(TableSQLiteHelper.COLUMN_NAME, pName);
		values.put(TableSQLiteHelper.COLUMN_QUALIFICATION, pQualification);
		
		database.insert(TableSQLiteHelper.TABLE_NAME, null, values);
	}
	
	public void UpdateExistingRecord(long pId, String pName, int pQualification) {
		final String whereClause = TableSQLiteHelper.COLUMN_ID + " = ?";
		final String[] whereArgs = new String[] { String.valueOf(pId) };
		final Cursor cursor = database.query(
			TableSQLiteHelper.TABLE_NAME, all_columns, 
			whereClause, 
			whereArgs, 
			null, null, null);
		
		if (!cursor.moveToFirst()) {
			cursor.close();
			return;
		}
		cursor.close();
		
		final ContentValues values = new ContentValues();
		values.put(TableSQLiteHelper.COLUMN_NAME, pName);
		values.put(TableSQLiteHelper.COLUMN_QUALIFICATION, pQualification);
		
		database.update(TableSQLiteHelper.TABLE_NAME, values, whereClause, whereArgs);
	}
	
}
