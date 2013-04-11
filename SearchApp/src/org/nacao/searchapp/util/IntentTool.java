package org.nacao.searchapp.util;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

/**
 * Intent工具库
 * 
 * @author hutao
 * 
 */
public class IntentTool {
	/**
	 * 判定包是否在系统中存在
	 * 
	 * @param context
	 *            上下文对象
	 * @param packageName
	 *            包名
	 * @return 返回true表示包存在,否则false
	 */
	public static boolean isPackage(Context context, String packageName) {
		boolean flag = false;
		if (context == null || packageName == null)
			return flag;
		final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
		List<PackageInfo> list = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
		if (list == null || list.size() <= 0)
			return flag;
		PackageInfo pi = null;
		int size = list.size();
		for (int i = 0; i < size; i++) {
			pi = list.get(i);
			if (pi.packageName.equals(packageName)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 判定Intent是否在系统的Activity可执行组中存在
	 * 
	 * @param context
	 *            上下文对象
	 * @param intent
	 *            Intent对象
	 * @return 返回true表示包存在,否则false
	 */
	public static boolean isActivity(Context context, Intent intent) {
		boolean flag = false;
		if (context == null || intent == null)
			return flag;
		final PackageManager packageManager = context.getPackageManager();
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		if (list != null && list.size() > 0)
			flag = true;
		return flag;
	}

	/**
	 * 判定Intent是否在系统的Activity可执行组中存在
	 * 
	 * @param context
	 *            上下文对象
	 * @param intent
	 *            Intent对象
	 * @param flags
	 *            查找方式 参数为PackageManager.MATCH_DEFAULT_ONLY|GET_INTENT_FILTERS|
	 *            GET_RESOLVED_FILTER
	 * @return 返回true表示包存在,否则false
	 */
	public static boolean isActivity(Context context, Intent intent, int flags) {
		boolean flag = false;
		if (context == null || intent == null)
			return flag;
		final PackageManager packageManager = context.getPackageManager();
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				flags);
		if (list != null && list.size() > 0)
			flag = true;
		return flag;
	}

	/**
	 * 判定Intent是否在系统的Service可执行组中存在
	 * 
	 * @param context
	 *            上下文对象
	 * @param intent
	 *            Intent对象
	 * @return 返回true表示包存在,否则false
	 */
	public static boolean isService(Context context, Intent intent) {
		boolean flag = false;
		if (context == null || intent == null)
			return flag;
		final PackageManager packageManager = context.getPackageManager();
		List<ResolveInfo> list = packageManager.queryIntentServices(intent,
				PackageManager.GET_INTENT_FILTERS);
		if (list != null && list.size() > 0)
			flag = true;
		return flag;
	}

	/**
	 * 判定Intent是否在系统的Service可执行组中存在
	 * 
	 * @param context
	 *            上下文对象
	 * @param intent
	 *            Intent对象
	 * @param flags
	 *            查找方式 参数为PackageManager.GET_INTENT_FILTERS|GET_RESOLVED_FILTER
	 * @return 返回true表示包存在,否则false
	 */
	public static boolean isService(Context context, Intent intent, int flags) {
		boolean flag = false;
		if (context == null || intent == null)
			return flag;
		final PackageManager packageManager = context.getPackageManager();
		List<ResolveInfo> list = packageManager.queryIntentServices(intent,
				flags);
		if (list != null && list.size() > 0)
			flag = true;
		return flag;
	}

	/**
	 * 判定Intent是否在系统的BroadcastReceiver可执行组中存在
	 * 
	 * @param context
	 *            上下文对象
	 * @param intent
	 *            Intent对象
	 * @return 返回true表示包存在,否则false
	 */
	public static boolean isBroadcast(Context context, Intent intent) {
		boolean flag = false;
		if (context == null || intent == null)
			return flag;
		final PackageManager packageManager = context.getPackageManager();
		List<ResolveInfo> list = packageManager.queryBroadcastReceivers(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		if (list != null && list.size() > 0)
			flag = true;
		return flag;
	}

	/**
	 * 判定Intent是否在系统的BroadcastReceiver可执行组中存在
	 * 
	 * @param context
	 *            上下文对象
	 * @param intent
	 *            Intent对象
	 * @param flags
	 *            查找方式 参数为PackageManager.MATCH_DEFAULT_ONLY|GET_INTENT_FILTERS|
	 *            GET_RESOLVED_FILTER
	 * @return 返回true表示包存在,否则false
	 */
	public static boolean isBroadcast(Context context, Intent intent, int flags) {
		boolean flag = false;
		if (context == null || intent == null)
			return flag;
		final PackageManager packageManager = context.getPackageManager();
		List<ResolveInfo> list = packageManager.queryBroadcastReceivers(intent,
				flags);
		if (list != null && list.size() > 0)
			flag = true;
		return flag;
	}

	/**
	 * 启动Activity
	 * 
	 * @param context
	 *            上下文对象
	 * @param intent
	 *            Intent对象
	 * @return
	 */
	public static boolean startActivity(Context context, Intent intent, int i) {
		boolean flag = false;
		if (context == null || intent == null)
			return flag;
		if (intent.getClass() != null || intent.getComponent() != null)
			flag = true;
		else
			flag = isActivity(context, intent);
		if (flag)
			context.startActivity(intent);
		return flag;
	}

	/**
	 * 启动Activity
	 * 
	 * @param context
	 *            上下文对象
	 * @param intent
	 *            Intent对象
	 * @return
	 */
	public static boolean startActivity(Context context, Intent intent) {
		boolean flag = false;
		if (context == null || intent == null)
			return flag;
		if (intent.getClass() != null || intent.getComponent() != null)
			flag = true;
		else
			flag = isActivity(context, intent);
		if (flag)
			context.getApplicationContext().startActivity(intent);
		return flag;
	}

	/**
	 * @param context
	 * @param serviceName
	 *            服务的类名
	 * @return
	 */
	public static boolean isServiceRuning(Context context, String serviceName) {
		ActivityManager mager = (ActivityManager) context
				.getSystemService(context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : mager
				.getRunningServices(Integer.MAX_VALUE)) {
			Log.i("serviceName", service.service.getClassName());
			if (service.service.getClassName().equals(serviceName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 启动Service
	 * 
	 * @param context
	 *            上下文对象
	 * @param intent
	 *            Intent对象
	 * @return
	 */
	public static boolean startService(Context context, Intent intent) {
		boolean flag = false;
		if (context == null || intent == null)
			return flag;
		if (intent.getClass() != null || intent.getComponent() != null)
			flag = true;
		else
			flag = isService(context, intent);
		if (flag)
			context.startService(intent);
		return flag;
	}

	/**
	 * 停止Service
	 * 
	 * @param context
	 *            上下文对象
	 * @param intent
	 *            Intent对象
	 * @return
	 */
	public static boolean stopService(Context context, Intent intent) {
		boolean flag = false;
		if (context == null || intent == null)
			return flag;
		if (intent.getClass() != null || intent.getComponent() != null)
			flag = true;
		else
			flag = isService(context, intent);
		if (flag)
			context.stopService(intent);
		/*
		 * if(flag){ Log.i("stopService", "stopService");
		 * Toast.makeText(context, "isService", 3000).show(); }
		 */
		return flag;
	}

	/**
	 * 绑定Service 注意对于tableActivity中的 tab中bindService不能直接绑定
	 * 需要getApplicationContext()上下文环境才可以绑定上
	 * 
	 * @param context
	 *            上下文对象
	 * @param intent
	 *            Intent对象
	 * @param conn
	 *            ServiceConnection对象
	 * @return
	 */
	public static boolean bindService(Context context, Intent intent,
			ServiceConnection conn) {
		boolean flag = false;
		if (context == null || intent == null)
			return flag;
		if (intent.getClass() != null || intent.getComponent() != null)
			flag = true;
		else
			flag = isService(context, intent);
		if (flag)
			context.getApplicationContext().bindService(intent, conn,
					Context.BIND_AUTO_CREATE);
		return flag;

	}

	/**
	 * 结束绑定Service
	 * 
	 * @param context
	 *            上下文对象
	 * @param intent
	 *            Intent对象
	 * @param conn
	 *            ServiceConnection对象
	 * @return
	 */
	public static boolean unbindService(Context context, ServiceConnection conn) {
		boolean flag = false;
		if (context == null || conn == null)
			return flag;
		context.getApplicationContext().unbindService(conn);
		// context.bindService(intent, conn,Context.BIND_AUTO_CREATE);
		return flag;
	}

	/**
	 * 执行通知
	 * 
	 * @param context
	 *            上下文对象
	 * @param intent
	 *            Intent对象
	 * @return
	 */
	public static boolean sendBroadcast(Context context, Intent intent) {
		boolean flag = false;
		if (context == null || intent == null)
			return flag;
		if (intent.getClass() != null || intent.getComponent() != null)
			flag = true;
		else
			flag = isBroadcast(context, intent);
		if (flag)
			context.sendBroadcast(intent);
		return flag;
	}

	/**
	 * 获得传递过来指定键的数据
	 * 
	 * @param activity
	 *            Activity对象
	 * @param key
	 *            key键
	 * @return
	 */
	public static String getBundle(Activity activity, String key) {
		String str = null;
		Bundle extras = getBundle(activity);
		if (extras != null)
			str = extras.getString(key);
		return str;
	}

	/**
	 * 获得传递过来的数据
	 * 
	 * @param activity
	 *            Activity对象
	 * @param key
	 *            key键
	 * @return
	 */
	public static Bundle getBundle(Activity activity) {
		Bundle extras = null;
		if (activity != null)
			extras = getBundle(activity.getIntent());
		return extras;
	}

	/**
	 * 获得传递过来的数据
	 * 
	 * @param intent
	 *            Intent对象
	 * @return
	 */
	public static Bundle getBundle(Intent intent) {
		Bundle extras = null;
		if (intent != null)
			extras = intent.getExtras();
		return extras;
	}

	/**
	 * 获得传递过来指定键的数据
	 * 
	 * @param intent
	 *            Intent对象
	 * @param key
	 *            key键
	 * @return
	 */
	public static String getBundle(Intent intent, String key) {
		String str = null;
		Bundle extras = getBundle(intent);
		if (extras != null)
			str = extras.getString(key);
		return str;
	}

	/**
	 * 获得打开设置GPS的界面
	 * 
	 * @return
	 */
	public static Intent getGpsIntent() {
		// ACTION_LOCATION_SOURCE_SETTINGS这个参数也可以
		Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
		return intent;
	}

	/**
	 * 获得打开设置WIFI的界面
	 * 
	 * @return
	 */
	public static Intent getWifiIntent() {
		Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
		return intent;
	}

	/**
	 * 获得打开设置蓝牙的界面
	 * 
	 * @return
	 */
	public static Intent getBlueToolhIntent() {
		Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
		return intent;
	}

	/**
	 * 获得打开设置日期时间的界面
	 * 
	 * @return
	 */
	public static Intent getDateTimeIntent() {
		Intent intent = new Intent(Settings.ACTION_DATE_SETTINGS);
		return intent;
	}

	/**
	 * 获得打开设置移动网络的界面
	 * 
	 * @return
	 */
	public static Intent getNetworkIntent() {
		Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
		return intent;
	}
}
