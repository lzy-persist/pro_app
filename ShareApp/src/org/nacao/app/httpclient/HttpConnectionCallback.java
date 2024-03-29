package org.nacao.app.httpclient;

import org.json.JSONArray;

public interface HttpConnectionCallback {
	/**
	 * Call back method will be execute after the http request return.
	 * @param response the response of http request. 
	 * The value will be null if any error occur.
	 */
	void execute(String response,int kind);
}
