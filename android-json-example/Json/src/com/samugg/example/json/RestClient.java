package com.samugg.example.json;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class RestClient {

	private static final String BASE_URL = "http://192.168.1.6/static/json/";
	
	private static AsyncHttpClient client = new AsyncHttpClient();
	
	public static void getSingleObject(AsyncHttpResponseHandler responseHandler) {
		client.get(BASE_URL + "singleObject.js", null, responseHandler);
	}
	
	public static void getArrayOfObjects(AsyncHttpResponseHandler responseHandler) {
		client.get(BASE_URL + "arrayOfObjects.js", null, responseHandler);
	}
	
}
