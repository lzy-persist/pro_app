package org.nacao.searchapp.util;

import java.util.ArrayList;
import java.util.List;

import org.nacao.searchapp.manage.LoginActivity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

public class ExitApplication extends Application {

	private List<Activity> acts = new ArrayList<Activity>();
	private static ExitApplication instance=null;
	private ExitApplication(){
		
	}
	public static ExitApplication getInstance(){
		if(instance==null){
			instance = new ExitApplication();
		}
		return instance;
	}
	
	public void addActivity(Activity act){
		acts.add(act);
	}
	
	public void exit(){
		if(acts!=null && acts.size()>0){
			for(Activity act : acts){
				if(act!=null){
					act.finish();
				}
			}
		}
		Intent intent = new Intent();
		intent.setAction("org.searchapp.APP_LOG_SERVICE");
		DisplayTool.stopService(getApplicationContext(), intent);
		
		System.exit(0);
	}
}
