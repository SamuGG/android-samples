package com.samugg.example.json;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ActivityMain extends FragmentActivity implements OnClickListener {

	private FragmentManager fm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		fm = getFragmentManager();
		
		Button btn = (Button)findViewById(android.R.id.button1);
		btn.setOnClickListener(this);
		
		btn = (Button)findViewById(android.R.id.button2);
		btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		final View view = v;
		final int id = v.getId();
		
		if (android.R.id.button1 == id) {
			
			// Disable the button clicked
			v.setEnabled(false);
			
			// Make our async http call
			RestClient.getSingleObject(new JsonHttpResponseHandler() {
			
				@Override
				public void onSuccess(JSONObject jsObj) {
				
					final Bundle data = new Bundle();
					try
					{
						// Retrieve the fields from the returned JSON object
						// and send them to the Fragment, so it can show the 
						// downloaded data.
						data.putString(FragmentSingleObject.DATA_NAME, jsObj.getString(FragmentSingleObject.DATA_NAME));
						data.putString(FragmentSingleObject.DATA_SURNAME, jsObj.getString(FragmentSingleObject.DATA_SURNAME));
						
					} catch (Exception ex) {
						ex.printStackTrace();
					}
						
					// Create the new Fragment and set the data to show
					final Fragment f = new FragmentSingleObject();
					f.setArguments(data);
					
					final FragmentTransaction ft = fm.beginTransaction();
					ft.replace(R.id.fragment_container, f);
					ft.commit();
					
					// Enable the button clicked
					view.setEnabled(true);
				}

				@Override
				public void onFailure(Throwable e, JSONObject errorResponse) {
					super.onFailure(e, errorResponse);
					
					// Enable the button clicked
					view.setEnabled(true);
				}
			
			});
			
		} else if (android.R.id.button2 == id) {
			
			// Disable the button clicked
			v.setEnabled(false);
			
			// Make our async http call
			RestClient.getArrayOfObjects(new JsonHttpResponseHandler() {
			
				@Override
				public void onSuccess(int statusCode, Header[] headers, String responseBody) {
					super.onSuccess(statusCode, headers, responseBody);
					
					//Log.d("Parse", responseBody);
					
					//ArrayList<Country> countries = new ArrayList<Country>();
					final Gson gson = new Gson();
					final Type listType = new TypeToken<List<Country>>(){}.getType();
					final List<Country> countries = gson.fromJson(responseBody, listType);
					
					final CountryListAdapter countriesAdapter = new CountryListAdapter(ActivityMain.this, 0, countries);
					final ListFragment f = new ListFragment();
					f.setListAdapter(countriesAdapter);

					final FragmentTransaction ft = fm.beginTransaction();
					ft.replace(R.id.fragment_container, f);
					ft.commit();
					
					// Enable the button clicked
					view.setEnabled(true);
				}

				@Override
				public void onFailure(Throwable e, JSONObject errorResponse) {
					super.onFailure(e, errorResponse);
					
					// Enable the button clicked
					view.setEnabled(true);
				}
			
			});
			
		} else {
			
			Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show();

		}
	}

}
