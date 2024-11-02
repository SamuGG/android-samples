package com.samugg.example.json;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CountryListAdapter extends ArrayAdapter<Country> {

	private final Context context;
	private final List<Country> countries;
	private final LayoutInflater inflater;
	
	public CountryListAdapter(Context pContext, int resource, List<Country> objects) {
		super(pContext, resource, objects);
		
		context = pContext;
		countries = objects;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder;
		
		if (null == convertView) {
			
			convertView = inflater.inflate(R.layout.list_item, null, false);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.populateFrom(countries.get(position));
		
		return convertView;
	}

	private static class ViewHolder {
	
		private final TextView tvName;
		private final TextView tvCode;
		private final TextView tvPrefix;
		
		public ViewHolder(View row) {
			tvName = (TextView)row.findViewById(android.R.id.text1);
			tvCode = (TextView)row.findViewById(android.R.id.text2);
			tvPrefix = (TextView)row.findViewById(R.id.prefix);
		}
		
		public void populateFrom(Country c) {
			tvName.setText(c.name);
			tvCode.setText(c.code);
			tvPrefix.setText(String.valueOf(c.prefix));
		}
		
	}
	
}
