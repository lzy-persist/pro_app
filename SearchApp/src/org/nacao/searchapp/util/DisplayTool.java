package org.nacao.searchapp.util;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

/**Display���߿�
 *
 */
public class DisplayTool {
	/**���Display����
	 * @param activity Activity����
	 * @return
	 */
	public static Display getDisplay(Activity activity){
		Display display=null;
		if(activity!=null)display=activity.getWindowManager().getDefaultDisplay();
		return display;
	}
	/**�����Ļ�ֱ��ʴ�С
	 * @param activity Activity����
	 * @return
	 */
	public static int[] getScreen(Activity activity){
		if(activity!=null){
			Display display=activity.getWindowManager().getDefaultDisplay();
			return getScreen(display);
		}
		return null;
	}
	/**�����ĻDPI��С
	 * @param activity Activity����
	 * @return
	 */
	public static float[] getScreenDPI(Activity activity){
		if(activity!=null){
			Display display=activity.getWindowManager().getDefaultDisplay();
			return getScreenDPI(display);
		}
		return null;
	}
	/**�����Ļ�ֱ��ʴ�С
	 * @param display Display��ʾ����
	 * @return ����[width,height]
	 */
	public static int[] getScreen(Display display){
        DisplayMetrics dm=new DisplayMetrics();
        display.getMetrics(dm);
        int[] px=new int[]{dm.widthPixels,dm.heightPixels};
        dm=null;
        return px;
	}
	/**�����ĻDPI��С
	 * @param display Display��ʾ����
	 * @return ����[x,y]
	 */
	public static float[] getScreenDPI(Display display){
        DisplayMetrics dm=new DisplayMetrics();
        display.getMetrics(dm);
        float[] px=new float[]{dm.xdpi,dm.ydpi};
        dm=null;
        return px;
	}
	/**����Activity��title
	 * @param activity Activity����
	 */
	public static void hideTitle(Activity activity){
		if(activity!=null)activity.requestWindowFeature(Window.FEATURE_NO_TITLE);       
	}
	/**ȫ��Activity
	 * @param activity Activity����
	 */
	public static void setFullScreen(Activity activity){
		if(activity!=null){
			activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
	}
	/**ȡ��ȫ��
	 * @param activity
	 */
	public static void setNormalScreen(Activity activity){
		if(activity!=null){
			WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
			attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
			activity.getWindow().setAttributes(attrs); 
			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		}
	}
	/**��õ�ǰ��Ļ��Ļ����
	 * @param activity
	 * @return������0��ʾ����,1��ʾ����,2��ʾδ֪
	 */
	public static int getOrientation(Activity activity){
		if(activity==null)return 0;
		Configuration config = activity.getResources().getConfiguration();   
		return getOrientation(config);
	}
	/**��õ�ǰ��Ļ��Ļ����
	 * @param config
	 * @return������0��ʾδ֪,1��ʾ����,2��ʾ����
	 */
	public static int getOrientation(Configuration config){ 
		//Log.w("getOrientation","w="+config.orientation);
		if(config.orientation == Configuration.ORIENTATION_LANDSCAPE){//����
	    	return 2;
	    }else if(config.orientation == Configuration.ORIENTATION_PORTRAIT){//���� 
	    	return 1;
	    }else return 0;
		
		/*if(config.orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){//����
	    	return 0;
	    }else if(config.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){//���� 
	    	return 1;
	    }else return 2;*/
	}
	public static void setOrientation(Activity activity,int orientation){
		if(orientation==1)activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//���� 
		else activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//����
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

	
	//��ȡ��ǰӦ�õĲ����ֻ�����
	public static String getPhoneNum(Activity activity){
		TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getLine1Number();
	}
}
