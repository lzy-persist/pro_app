package org.nacao.searchapp.manage;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;
import org.nacao.searchapp.R;
import org.nacao.searchapp.httpclient.CustomerHttpClient;
import org.nacao.searchapp.httpclient.HttpConnectionCallback;
import org.nacao.searchapp.httpclient.HttpConnectionUtil;
import org.nacao.searchapp.httpclient.UriAPI;
import org.nacao.searchapp.model.User;
import org.nacao.searchapp.util.ExitApplication;
import org.nacao.searchapp.util.Variable;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.QuickContactBadge;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class UserinfoActivity extends Activity {

	// UI references.
	private TextView userChinesename;
	private TextView username;
	private TextView userMobile;
	private TextView userEmail;
	private TextView userProvince;
	private TextView lastLogin;
	
	private QuickContactBadge quickContactBadge;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.userinfo);
		
		initView();
		showInfo();
		ExitApplication.getInstance().addActivity(this);
	}

	private void initView(){
		userChinesename = (TextView)findViewById(R.id.userChinesename);
		username = (TextView)findViewById(R.id.username);
		userMobile = (TextView)findViewById(R.id.userMobile);
		userEmail = (TextView)findViewById(R.id.userEmail);
		userProvince = (TextView)findViewById(R.id.userProvince);
		lastLogin = (TextView)findViewById(R.id.lastLogin);
		quickContactBadge = (QuickContactBadge) findViewById(R.id.quickContactBadge1);
	}

	private void showInfo(){
		userChinesename.setText("中文名字："+Variable.user.getUserChinesename());
		username.setText(Variable.user.getUserName());
		userMobile.setText("Tel:"+Variable.user.getUserMobile());
		userEmail.setText("Email:"+Variable.user.getUserEmail());
		userProvince.setText("所属省份："+Variable.user.getUserProvince());
		lastLogin.setText("最后一次登录时间："+Variable.user.getLastlogin());
	}

	public class UserinfoTask extends AsyncTask<String, String, String> implements HttpConnectionCallback{
		private User user = null;
		@Override
		protected String doInBackground(String... strParams) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return "";
			}

			HttpConnectionUtil util = new HttpConnectionUtil();
			DefaultHttpClient httpClient = CustomerHttpClient.getHttpClient();
			Map<String, String> params = new HashMap<String, String>();
			util.setClient(httpClient);
			util.syncConnect(UriAPI.serviceHttp, params,
					HttpConnectionUtil.HttpMethod.POST, this, 0);
			
			return null;
		}

		@Override
		protected void onPostExecute(final String success) {
		}

		@Override
		protected void onCancelled() {
		}
		
		public void execute(String response, int kind) {
			try {

			} catch (Exception e) {

			}
		}
	}

}
