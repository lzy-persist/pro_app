package org.nacao.searchapp.manage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nacao.searchapp.R;
import org.nacao.searchapp.util.ExitApplication;
import org.nacao.searchapp.util.FileUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class SettingsActivity extends Activity implements OnItemClickListener{

	private ListView lv;
	private String path;
	private ListView logLv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appsetting);
		lv = (ListView)findViewById(R.id.setlist);
		lv.setAdapter(new SimpleAdapter(this, getData(), R.layout.list_info, new String[]{"setname"}, new int[]{R.id.setName}));
		lv.setOnItemClickListener(this);
		ExitApplication.getInstance().addActivity(this);
	}
	  private List<Map<String,String>> getData(){
	    	List<Map<String,String>> datas = new ArrayList<Map<String,String>>();
	    	Map<String, String> mp1 = new HashMap<String, String>();
	    	mp1.put("setname", "网络设置");
	    	Map<String, String> mp2 = new HashMap<String, String>();
	    	mp2.put("setname", "系统设置");
	    	Map<String, String> mp3 = new HashMap<String, String>();
	    	mp3.put("setname", "用户设置");
	    	Map<String, String> mp4 = new HashMap<String, String>();
	    	mp4.put("setname", "切换用户");
	    	Map<String, String> mp5 = new HashMap<String, String>();
	    	mp5.put("setname", "意见反馈");

	    	Map<String, String> mp8 = new HashMap<String, String>();
	    	mp8.put("setname", "用户操作日志");

	    	Map<String, String> mp6 = new HashMap<String, String>();
	    	mp6.put("setname", "关于");
	    	Map<String, String> mp7 = new HashMap<String, String>();
	    	mp7.put("setname", "退出");
	    	
	    	datas.add(mp1);datas.add(mp2);datas.add(mp3);datas.add(mp4);datas.add(mp5);datas.add(mp8);datas.add(mp6);datas.add(mp7);
	    	
	    	return datas;
	    }
	
	public void netSet(View view){
        startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), 1);
	}
	
	public void workunitSet(View view){
		Intent sysSeting = new Intent(android.provider.Settings.ACTION_SETTINGS);
		startActivity(sysSeting);
	}

	public void changeUser(View view){
		Intent inte = new Intent(this,LoginActivity.class);
		startActivityForResult(inte, 1);
		finish();
	}
	
	public void showLogs(View view){
		path = getFilesDir().getAbsolutePath() + File.separator+ "log";
		File[] files = FileUtil.getLogFiles(path);
		
		logLv = new ListView(SettingsActivity.this);
		SimpleAdapter adapter = new SimpleAdapter(this, getLoginfos(files), R.layout.list_info, new String[]{"setname"}, new int[]{R.id.setName});
		logLv.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		logLv.setOnItemClickListener(new LogitemLisener());
		AlertDialog builder = new AlertDialog.Builder(this).setTitle("操作日志").setIcon(android.R.drawable.ic_dialog_info).setView(logLv).setPositiveButton("确定", null).show();
		
	}
	private class LogitemLisener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			Map<String,String> item = (Map<String,String>)arg0.getAdapter().getItem(position);
			String filename = item.get("setname");
			String filePath = path+File.separator+filename;
			List<String> loginfo = FileUtil.showFiles(filePath);
			if(logLv!=null){
				ArrayAdapter<String> ada = new ArrayAdapter<String>(getApplicationContext(),R.layout.log_info,R.id.log, loginfo);
				
				logLv.setAdapter(ada);
				ada.notifyDataSetChanged();
			}
		}
		
	}
	
	private List<Map<String,String>> getLoginfos(File[] files){
    	List<Map<String,String>> datas = new ArrayList<Map<String,String>>();
    	if(files!=null && files.length>0){
    		for(int i=0;i<files.length;i++){
    			File f = files[i];
    			Map<String, String> mp1 = new HashMap<String, String>();
    			mp1.put("setname", f.getName());
    			datas.add(mp1);
    		}
    	}
    	
    	return datas;
    }

	public void appExit(View view){
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

	@Override
	public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
		String selItem = lv.getAdapter().getItem(position).toString();
		
		if(selItem.contains("退出")){
			appExit(arg1);
		}else if(selItem.contains("网络设置")){
			netSet(arg1);
		}else if(selItem.contains("系统设置")){
			workunitSet(arg1);
		}else if(selItem.contains("用户设置")){
			
		}else if(selItem.contains("切换用户")){
			changeUser(arg1);
		}else if(selItem.contains("意见反馈")){
//			LayoutInflater inflat = (LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
//			View v = inflat.inflate(R.layout., null);
			EditText infos = new EditText(this);
			infos.setHeight(120);
			AlertDialog builder = new AlertDialog.Builder(this).setTitle("意见").setIcon(android.R.drawable.ic_dialog_info).setView(infos).setPositiveButton("确定", null).setNegativeButton("取消", null).show();
		}else if(selItem.contains("日志")){
			showLogs(arg1);
		}else if(selItem.contains("关于")){
			TextView infos = new TextView(this);
			infos.setText("   版本1.1         手机端机构信息检索应用！");
			infos.setHeight(80);
			AlertDialog builder = new AlertDialog.Builder(this).setTitle("关于").setIcon(android.R.drawable.ic_dialog_info).setView(infos).setPositiveButton("确定", null).show();
		}
	}
}
