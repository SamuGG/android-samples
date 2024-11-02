package com.samugg.example.json;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentSingleObject extends Fragment {

	public static final String DATA_NAME = "name"; 
	public static final String DATA_SURNAME = "surname"; 
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		TextView tv;
		final View v = inflater.inflate(R.layout.fragment_single_object, container, false);
		final Bundle data = getArguments();
		String name = "";
		String surname = "";
		
		if (null != data) {
		
			name = data.getString(DATA_NAME);
			surname = data.getString(DATA_SURNAME);
			
		}
		
		tv = (TextView)v.findViewById(android.R.id.text1);
		tv.setText(name);
		
		tv = (TextView)v.findViewById(android.R.id.text2);
		tv.setText(surname);
		
		return v;
    }
}
