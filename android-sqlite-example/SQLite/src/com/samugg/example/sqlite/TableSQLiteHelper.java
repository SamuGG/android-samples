package com.samugg.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TableSQLiteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "database.sqlite";
	private static final int VERSION = 1;
	public static final String TABLE_NAME = "table1";
	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_QUALIFICATION = "qualification";
	
	public TableSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME + "( " + 
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
			COLUMN_NAME + " TEXT NOT NULL, " + 
			COLUMN_QUALIFICATION + " INTEGER NOT NULL )");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

}
