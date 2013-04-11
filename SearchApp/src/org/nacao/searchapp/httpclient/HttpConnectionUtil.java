package org.nacao.searchapp.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.nacao.searchapp.util.Variable;

import android.os.Handler;
import android.util.Log;

public class HttpConnectionUtil {
	public static enum HttpMethod {
		GET, POST
	}

	private DefaultHttpClient client = null;

	public DefaultHttpClient getClient() {
		return client;
	}

	public void setClient(DefaultHttpClient client) {
		this.client = client;
	}

	// 同步
	public void asyncConnect(final String url, final HttpMethod method,
			final HttpConnectionCallback callback, final int kind) {
		asyncConnect(url, null, method, callback, kind);
	}

	// 异步
	public void syncConnect(final String url, final HttpMethod method,
			final HttpConnectionCallback callback, final int kind) {
		syncConnect(url, null, method, callback, kind);
	}

	public void asyncConnect(final String url,
			final Map<String, String> params, final HttpMethod method,
			final HttpConnectionCallback callback, final int kind) {
		Handler handler = new Handler();
		Runnable runnable = new Runnable() {
			public void run() {
				syncConnect(url, params, method, callback, kind);
			}
		};
		handler.post(runnable);
	}

	public void syncConnect(final String url, final Map<String, String> params,
			final HttpMethod method, final HttpConnectionCallback callback,
			int kind) {
		String json = null;
		BufferedReader reader = null;
		InputStreamReader inReader = null;
		try {
			HttpUriRequest request = getRequest(url, params, method);
			HttpResponse response = null;
			if (client != null && request != null) {
				response = client.execute(request);
			} else {
				try {
					throw new Exception("httpClient为空！");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Log.i("getStatusCode", ""
					+ response.getStatusLine().getStatusCode());
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Variable.qequestTime = System.currentTimeMillis();
				inReader = new InputStreamReader(response.getEntity().getContent());
				reader = new BufferedReader(inReader);
				StringBuilder sb = new StringBuilder();
				for (String s = reader.readLine(); s != null; s = reader.readLine()) {
					sb.append(s);
				}
				json = sb.toString();
				callback.execute(json, kind);
			}
		} catch (ClientProtocolException e) {
			Log.e("HttpConnectionUtil", e.getMessage(), e);
			callback.execute("1", kind);
		} catch (IOException e) {
			callback.execute("2", kind);
			Log.e("HttpConnectionUtil", e.getMessage(), e);
		} finally {
			try {
				if(inReader!=null){
					inReader.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
			}
		}
	}
	

	private HttpUriRequest getRequest(String url, Map<String, String> params,
			HttpMethod method) {
		if (method.equals(HttpMethod.POST)) {
			List<NameValuePair> listParams = new ArrayList<NameValuePair>();
			if (params != null) {
				for (String name : params.keySet()) {
					listParams.add(new BasicNameValuePair(name, params
							.get(name)));
				}
			}
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
						listParams, "utf-8");
				HttpPost request = new HttpPost(url);
				request.setEntity(entity);
				
				return request;
			} catch (UnsupportedEncodingException e) {
				// Should not come here, ignore me.
				Log.i("warn", "Should not come here, ignore me");
				throw new java.lang.RuntimeException(e.getMessage(), e);
			}
		} else {
			try{
				if (url.indexOf("?") < 0) {
					url += "?";
				}
				if (params != null) {
					for (String name : params.keySet()) {
						url += "&" + name + "="
								+ URLEncoder.encode(params.get(name),"utf-8");
					}
				}
				HttpGet request = new HttpGet(url);
				return request;
			}catch(Exception e){
				Log.i("exception", e.getMessage());
			}
		}
		return null;
	}
}
