package org.nacao.app.manage;

import org.nacao.app.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appsetting);
	}
	
	public void netSet(View view){
		Button netSet = (Button)findViewById(R.id.net_set);;
        startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), 1);
	}
	
	public void workunitSet(View view){
		Intent sysSeting = new Intent(android.provider.Settings.ACTION_SETTINGS);
		startActivity(sysSeting);
	}

	public void changeUser(View view){
		Button changeUser = (Button)findViewById(R.id.change_user);
		Intent inte = new Intent(this,LoginActivity.class);
		startActivityForResult(inte, 1);
		finish();
	}

	public void appExit(View view){
		Button appExit = (Button)findViewById(R.id.app_exit);
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("确认退出当前应用吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//????
				finish();
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
}
