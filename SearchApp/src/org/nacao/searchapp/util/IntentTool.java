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
 * Intent���߿�
 * 
 * @author hutao
 * 
 */
public class IntentTool {
	/**
	 * �ж����Ƿ���ϵͳ�д���
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param packageName
	 *            ����
	 * @return ����true��ʾ������,����false
	 */
	public static boolean isPackage(Context context, String packageName) {
		boolean flag = false;
		if (context == null || packageName == null)
			return flag;
		final PackageManager packageManager = context.getPackageManager();// ��ȡpackagemanager
		List<PackageInfo> list = packageManager.getInstalledPackages(0);// ��ȡ�����Ѱ�װ����İ���Ϣ
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
	 * �ж�Intent�Ƿ���ϵͳ��Activity��ִ�����д���
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param intent
	 *            Intent����
	 * @return ����true��ʾ������,����false
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
	 * �ж�Intent�Ƿ���ϵͳ��Activity��ִ�����д���
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param intent
	 *            Intent����
	 * @param flags
	 *            ���ҷ�ʽ ����ΪPackageManager.MATCH_DEFAULT_ONLY|GET_INTENT_FILTERS|
	 *            GET_RESOLVED_FILTER
	 * @return ����true��ʾ������,����false
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
	 * �ж�Intent�Ƿ���ϵͳ��Service��ִ�����д���
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param intent
	 *            Intent����
	 * @return ����true��ʾ������,����false
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
	 * �ж�Intent�Ƿ���ϵͳ��Service��ִ�����д���
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param intent
	 *            Intent����
	 * @param flags
	 *            ���ҷ�ʽ ����ΪPackageManager.GET_INTENT_FILTERS|GET_RESOLVED_FILTER
	 * @return ����true��ʾ������,����false
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
	 * �ж�Intent�Ƿ���ϵͳ��BroadcastReceiver��ִ�����д���
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param intent
	 *            Intent����
	 * @return ����true��ʾ������,����false
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
	 * �ж�Intent�Ƿ���ϵͳ��BroadcastReceiver��ִ�����д���
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param intent
	 *            Intent����
	 * @param flags
	 *            ���ҷ�ʽ ����ΪPackageManager.MATCH_DEFAULT_ONLY|GET_INTENT_FILTERS|
	 *            GET_RESOLVED_FILTER
	 * @return ����true��ʾ������,����false
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
	 * ����Activity
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param intent
	 *            Intent����
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
	 * ����Activity
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param intent
	 *            Intent����
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
	 *            ���������
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
	 * ����Service
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param intent
	 *            Intent����
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
	 * ֹͣService
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param intent
	 *            Intent����
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
	 * ��Service ע�����tableActivity�е� tab��bindService����ֱ�Ӱ�
	 * ��ҪgetApplicationContext()�����Ļ����ſ��԰���
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param intent
	 *            Intent����
	 * @param conn
	 *            ServiceConnection����
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
	 * ������Service
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param intent
	 *            Intent����
	 * @param conn
	 *            ServiceConnection����
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
	 * ִ��֪ͨ
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param intent
	 *            Intent����
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
	 * ��ô��ݹ���ָ����������
	 * 
	 * @param activity
	 *            Activity����
	 * @param key
	 *            key��
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
	 * ��ô��ݹ���������
	 * 
	 * @param activity
	 *            Activity����
	 * @param key
	 *            key��
	 * @return
	 */
	public static Bundle getBundle(Activity activity) {
		Bundle extras = null;
		if (activity != null)
			extras = getBundle(activity.getIntent());
		return extras;
	}

	/**
	 * ��ô��ݹ���������
	 * 
	 * @param intent
	 *            Intent����
	 * @return
	 */
	public static Bundle getBundle(Intent intent) {
		Bundle extras = null;
		if (intent != null)
			extras = intent.getExtras();
		return extras;
	}

	/**
	 * ��ô��ݹ���ָ����������
	 * 
	 * @param intent
	 *            Intent����
	 * @param key
	 *            key��
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
	 * ��ô�����GPS�Ľ���
	 * 
	 * @return
	 */
	public static Intent getGpsIntent() {
		// ACTION_LOCATION_SOURCE_SETTINGS�������Ҳ����
		Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
		return intent;
	}

	/**
	 * ��ô�����WIFI�Ľ���
	 * 
	 * @return
	 */
	public static Intent getWifiIntent() {
		Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
		return intent;
	}

	/**
	 * ��ô����������Ľ���
	 * 
	 * @return
	 */
	public static Intent getBlueToolhIntent() {
		Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
		return intent;
	}

	/**
	 * ��ô���������ʱ��Ľ���
	 * 
	 * @return
	 */
	public static Intent getDateTimeIntent() {
		Intent intent = new Intent(Settings.ACTION_DATE_SETTINGS);
		return intent;
	}

	/**
	 * ��ô������ƶ�����Ľ���
	 * 
	 * @return
	 */
	public static Intent getNetworkIntent() {
		Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
		return intent;
	}
}
