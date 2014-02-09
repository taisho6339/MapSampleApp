package com.taishonet.mapsample.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.AsyncTask;

public class RequestTask extends AsyncTask<Void, Void, Integer> {

	private String mRequestAPI;
	private Map<String, String> mReqValues;
	private RequestCallBack mCallBack;
	private String resData;

	public RequestTask(String reqAPI, Map<String, String> values,
			RequestCallBack callback) {
		mRequestAPI = reqAPI;
		mReqValues = values;
		mCallBack = callback;
	}

	// サーバーにGETリクエスト
	private HttpResponse requestGet() {

		HttpClient httpClient = new DefaultHttpClient();

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		for (String key : mReqValues.keySet()) {
			params.add(new BasicNameValuePair(key, mReqValues.get(key)));
		}

		String query = URLEncodedUtils.format(params, "utf-8");
		HttpGet request = new HttpGet(mRequestAPI + "?" + query);

		HttpResponse res = null;
		try {
			res = httpClient.execute(request);
			return res;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected Integer doInBackground(Void... params) {
		HttpResponse result = requestGet();
		if (result != null) {
			int resultCode = result.getStatusLine().getStatusCode();
			switch (resultCode) {
			case HttpStatus.SC_OK:
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				try {
					result.getEntity().writeTo(outputStream);
					resData = outputStream.toString();
				} catch (IOException e) {
				}
				break;
			default:
				break;
			}
			return result.getStatusLine().getStatusCode();
		}
		return HttpStatus.SC_INTERNAL_SERVER_ERROR;
	}

	@Override
	protected void onPostExecute(Integer result) {
		if (result == HttpStatus.SC_OK) {
			try {
				mCallBack.doCallBack(new JSONObject(resData));
			} catch (JSONException e) {
			}
		}
	}
}
