package com.samugg.example.sqlite;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ActivityMain extends Activity 
	implements OnClickListener
{

	private ListView lv;
	private SQLiteDatabase database;
	private SimpleCursorAdapter adapter;
	private Cursor cursor;
	private TableSQLiteHelper tableHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		lv = (ListView)findViewById(android.R.id.list);
		lv.setEmptyView(findViewById(android.R.id.empty));
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		final Button btn1 = (Button)findViewById(android.R.id.button1);
		btn1.setOnClickListener(this);
		
		// Construct the ListView adapter from the TableSQLiteHelper database
		
		tableHelper = new TableSQLiteHelper(this);
		database = tableHelper.getWritableDatabase();
		
		final String[] from = { "name", "qualification" };
	    final int[] to = { android.R.id.text1, android.R.id.text2 };
	    cursor = NewCursor();
	    
	    adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to);

	    // On item click, open ActivityEdit for that item
	    lv.setOnItemClickListener(new OnItemClickListener() {
	    	   
	    	@Override
	    	public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
	    		final Intent intent;
	    		
	    		if (cursor.moveToPosition(position)) {
					intent = new Intent(ActivityMain.this, ActivityEdit.class);
					intent.putExtra(ActivityEdit.RECORD_ID, id);
					intent.putExtra(ActivityEdit.RECORD_NAME, cursor.getString(1));
					intent.putExtra(ActivityEdit.RECORD_QUALIFICATION, cursor.getInt(2));
					
					startActivity(intent);
	    		}
	    	}
	    });
	    
		// Set the new adapter to the ListView
	    lv.setAdapter(adapter);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		database = tableHelper.getWritableDatabase();
		cursor = NewCursor();
		adapter.changeCursor(cursor);
	}

	@Override
	protected void onDestroy() {
		if (null != database) database.close();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if (null != database) database.close();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		database = tableHelper.getWritableDatabase();
	}

	@Override
	public void onClick(View view) {
		final int id = view.getId();
		Intent intent;
		switch(id) {
			case android.R.id.button1: // Create new record
				intent = new Intent(this, ActivityEdit.class);
				intent.putExtra(ActivityEdit.RECORD_ID, 0);
				
				startActivity(intent);
				break;
		}			
	}

	private Cursor NewCursor() {
		final String[] columns = { 
				TableSQLiteHelper.COLUMN_ID + " AS _id",
				TableSQLiteHelper.COLUMN_NAME,
				TableSQLiteHelper.COLUMN_QUALIFICATION };
				//TableSQLiteHelper.COLUMN_NAME + " || ' (' || " + TableSQLiteHelper.COLUMN_QUALIFICATION + " || ')' AS itemText" };
			
		return database.query(TableSQLiteHelper.TABLE_NAME, columns, null, null, null, null, null);
	}
}
