package org.nacao.app.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

/**Display工具库
 *
 */
public class DisplayTool {
	/**获得Display对象
	 * @param activity Activity对象
	 * @return
	 */
	public static Display getDisplay(Activity activity){
		Display display=null;
		if(activity!=null)display=activity.getWindowManager().getDefaultDisplay();
		return display;
	}
	/**获得屏幕分辨率大小
	 * @param activity Activity对象
	 * @return
	 */
	public static int[] getScreen(Activity activity){
		if(activity!=null){
			Display display=activity.getWindowManager().getDefaultDisplay();
			return getScreen(display);
		}
		return null;
	}
	/**获得屏幕DPI大小
	 * @param activity Activity对象
	 * @return
	 */
	public static float[] getScreenDPI(Activity activity){
		if(activity!=null){
			Display display=activity.getWindowManager().getDefaultDisplay();
			return getScreenDPI(display);
		}
		return null;
	}
	/**获得屏幕分辨率大小
	 * @param display Display显示对象
	 * @return 返回[width,height]
	 */
	public static int[] getScreen(Display display){
        DisplayMetrics dm=new DisplayMetrics();
        display.getMetrics(dm);
        int[] px=new int[]{dm.widthPixels,dm.heightPixels};
        dm=null;
        return px;
	}
	/**获得屏幕DPI大小
	 * @param display Display显示对象
	 * @return 返回[x,y]
	 */
	public static float[] getScreenDPI(Display display){
        DisplayMetrics dm=new DisplayMetrics();
        display.getMetrics(dm);
        float[] px=new float[]{dm.xdpi,dm.ydpi};
        dm=null;
        return px;
	}
	/**隐藏Activity的title
	 * @param activity Activity对象
	 */
	public static void hideTitle(Activity activity){
		if(activity!=null)activity.requestWindowFeature(Window.FEATURE_NO_TITLE);       
	}
	/**全屏Activity
	 * @param activity Activity对象
	 */
	public static void setFullScreen(Activity activity){
		if(activity!=null){
			activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
	}
	/**取消全屏
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
	/**获得当前屏幕屏幕方向
	 * @param activity
	 * @return　返回0表示横屏,1表示竖屏,2表示未知
	 */
	public static int getOrientation(Activity activity){
		if(activity==null)return 0;
		Configuration config = activity.getResources().getConfiguration();   
		return getOrientation(config);
	}
	/**获得当前屏幕屏幕方向
	 * @param config
	 * @return　返回0表示未知,1表示竖屏,2表示横屏
	 */
	public static int getOrientation(Configuration config){ 
		//Log.w("getOrientation","w="+config.orientation);
		if(config.orientation == Configuration.ORIENTATION_LANDSCAPE){//横屏
	    	return 2;
	    }else if(config.orientation == Configuration.ORIENTATION_PORTRAIT){//竖屏 
	    	return 1;
	    }else return 0;
		
		/*if(config.orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){//横屏
	    	return 0;
	    }else if(config.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){//竖屏 
	    	return 1;
	    }else return 2;*/
	}
	public static void setOrientation(Activity activity,int orientation){
		if(orientation==1)activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏 
		else activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
	}
}
