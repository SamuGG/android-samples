package com.samugg.example.sqlite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ActivityEdit extends Activity implements OnClickListener {

	public static final String RECORD_ID = "id";
	public static final String RECORD_NAME = "name";
	public static final String RECORD_QUALIFICATION = "qualification";
	
	private long record_id;
	private TableAdapter table_adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		Button btn = (Button)findViewById(android.R.id.button1);
		btn.setOnClickListener(this);
		
		btn = (Button)findViewById(android.R.id.button2);
		btn.setOnClickListener(this);
		
		btn = (Button)findViewById(android.R.id.button3);
		btn.setOnClickListener(this);
		
		table_adapter = new TableAdapter(this);
		table_adapter.Open();
		
		handleIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		handleIntent(intent);
	}
	
	@Override
	protected void onDestroy() {
		table_adapter.Close();
		super.onDestroy();
	}

	private void handleIntent(Intent intent) {
		Button btn;
		EditText edit;

		record_id = intent.getLongExtra(RECORD_ID, 0);
		
		if (0 == record_id) {
			btn = (Button)findViewById(android.R.id.button3);
			btn.setEnabled(false);
		} else {
			edit = (EditText)findViewById(R.id.edit1);
			edit.setText(intent.getStringExtra(RECORD_NAME));

			edit = (EditText)findViewById(R.id.edit2);
			edit.setText(String.valueOf(intent.getIntExtra(RECORD_QUALIFICATION, 0)));
		}
	}

	@Override
	public void onClick(View view) {
		final int id = view.getId();
		switch (id) {
			case android.R.id.button1: // Cancel
				finish();
				break;
			case android.R.id.button2: // Save
				saveRecord();
				break;
			case android.R.id.button3: // Delete
				deleteRecord();
				break;
		}
	}

	private void saveRecord() {
		EditText edit;
		final String name;
		final int qualification;
		final String strQualification;
		
		edit = (EditText)findViewById(R.id.edit1);
		name = edit.getText().toString();
		
		edit = (EditText)findViewById(R.id.edit2);
		strQualification = edit.getText().toString();
		qualification = (!TextUtils.isEmpty(strQualification)) ? Integer.parseInt(strQualification) : 0;
		
		// check required fields
		if (TextUtils.isEmpty(name) || 0 == qualification) {
			
			final AlertDialog.Builder builder = new AlertDialog.Builder(this)
				.setMessage(R.string.msg_required_fields)
				.setNeutralButton(android.R.string.ok, null);
			final AlertDialog dialog = builder.create();
			dialog.show();
			
			return;
		}
		
		if (0 == record_id) {
			table_adapter.CreateNewRecord(name, qualification);
		} else {
			table_adapter.UpdateExistingRecord(record_id, name, qualification);
		}
		finish();
	}
	
	private void deleteRecord() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this)
			.setMessage(R.string.msg_confirm_delete)
			.setNegativeButton(android.R.string.no, null)
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				table_adapter.DeleteRecord(record_id);
				finish();
			}
		});
		
		final AlertDialog dialog = builder.create();
		dialog.show();
	}

}
