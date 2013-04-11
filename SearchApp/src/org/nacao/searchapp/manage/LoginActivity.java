package org.nacao.searchapp.manage;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.nacao.searchapp.R;
import org.nacao.searchapp.httpclient.CustomerHttpClient;
import org.nacao.searchapp.httpclient.HttpConnectionCallback;
import org.nacao.searchapp.httpclient.HttpConnectionUtil;
import org.nacao.searchapp.httpclient.UriAPI;
import org.nacao.searchapp.model.User;
import org.nacao.searchapp.util.DateProcess;
import org.nacao.searchapp.util.DisplayTool;
import org.nacao.searchapp.util.ExitApplication;
import org.nacao.searchapp.util.Variable;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for username and password at the time of the login attempt.
	private String mUsername;
	private String mPassword;

	// UI references.
	private EditText mUsernameView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private CheckBox remPasswd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		mUsernameView = (EditText) findViewById(R.id.login_edit_account);
		mPasswordView = (EditText) findViewById(R.id.login_edit_pwd);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login_edit_account
								|| id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.login_btn_login).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						InputMethodManager imm = ( InputMethodManager ) view.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );     
						if (imm.isActive( ) ) {     
							imm.hideSoftInputFromWindow(view.getApplicationWindowToken( ) , 0 );    
						} //隐藏键盘
						if(checkNetwork()){
							attemptLogin();
						}
					}
				});
		
		ExitApplication.getInstance().addActivity(this);
		//---
		if(Variable.savePasswd){
			mUsernameView.setText(Variable.username);
			mPasswordView.setText(Variable.password);
			if(remPasswd==null){remPasswd=(CheckBox)findViewById(R.id.rem_passwd);}
			remPasswd.setChecked(true);
		}
	}


	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		// mEmailView.setError(null);
		mUsernameView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		// mEmail = mEmailView.getText().toString();
		mUsername = mUsernameView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid username
		if (TextUtils.isEmpty(mUsername)) {
			mUsernameView.setError(getString(R.string.error_invalid_username));
			cancel = true;
		}

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			
			startCheckService();
			mAuthTask = new UserLoginTask();
			mAuthTask.execute("0");
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<String, String, String> implements HttpConnectionCallback{
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
			params.put("username", mUsername);
			params.put("password", mPassword);
			params.put("method", "login");
			util.setClient(httpClient);
			util.syncConnect(UriAPI.serviceHttp, params,
					HttpConnectionUtil.HttpMethod.POST, this, 0);
			
			return null;
		}

		@Override
		protected void onPostExecute(final String success) {
			mAuthTask = null;
			showProgress(false);

			if (user!=null) {
				finish();
			} else {
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
		
		public void execute(String response, int kind) {
			try {
				JSONObject jsonobj = new JSONObject(response);
				user = new User();
				user.setUserId(jsonobj.getInt("userId"));
				user.setUserName(jsonobj.getString("userName"));
				user.setUserPassword(jsonobj.getString("userPassword"));
				user.setUserChinesename(jsonobj.getString("userChinesename"));
				user.setBzjgdm(jsonobj.getString("bzjgdm"));
				user.setUserMobile(jsonobj.getString("userMobile"));
				user.setUserEmail(jsonobj.getString("userEmail"));
				user.setUserProvince(jsonobj.getString("userProvince"));
				user.setLastlogin(jsonobj.getString("lastlogin"));
				Variable.user=user;
				if (user!=null) {
					
					Log.v("searchapp", "手机号码为"+DisplayTool.getPhoneNum(LoginActivity.this)+"的用户,"+DateProcess.getSysTime()+"：成功登录代码信息查询试验系统，登录用户为："+user.getUserName()+"！");
				
					Variable.username=mUsername;
					Variable.password=mPassword;
					Intent inte = new Intent(LoginActivity.this, AppTabWidge.class);
					inte.putExtra("select", "1");
					startActivity(inte);
				}else{
					Log.v("searchapp", "手机号码为"+DisplayTool.getPhoneNum(LoginActivity.this)+"的用户,"+DateProcess.getSysTime()+"：通过用户"+user.getUserName()+"登录系统失败！");
					Toast.makeText(getApplicationContext(), "登录失败", 3000).show();
				}
			} catch (Exception e) {

			}
		}
	}

	public void appExit(View view){
		ImageView appExit = (ImageView)findViewById(R.id.appExit);
		
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("确认退出当前应用吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//????
				ExitApplication.getInstance().exit();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		builder.create().show();
	}
	
	private boolean checkNetwork() {
        boolean flag = false;
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getActiveNetworkInfo() != null){
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        if (!flag) {
            Builder b = new AlertDialog.Builder(this).setTitle("没有可用的网络").setMessage(
                    "请开启GPRS或WIFI网络连接");
            b.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                	startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), 1);
                }
            }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.cancel();
                }
            }).create();
            b.show();
        }

        return flag;
    }

	public void savePasswd(View view){
		remPasswd = (CheckBox)findViewById(R.id.rem_passwd);

		if(remPasswd.isChecked()){
			Variable.savePasswd=true;
			remPasswd.setChecked(true);
		}else{
			Variable.savePasswd=false;
		}
	}

	
	private void startCheckService() {
		Intent intent = new Intent();
		intent.setAction("org.searchapp.APP_LOG_SERVICE");
		DisplayTool.startService(LoginActivity.this, intent);
	}

	private void stopCheckService() {
		Intent intent = new Intent();
		intent.setAction("org.searchapp.APP_LOG_SERVICE");
		DisplayTool.stopService(LoginActivity.this, intent);
	}
}
