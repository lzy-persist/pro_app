package org.nacao.searchapp.util;

import org.nacao.searchapp.model.User;

import android.app.Activity;

public class Variable {
	
	/** WIFI当前状态,1表示已连接，2表示未连接 */
	public static int wifi_state = 2;
	/** 数据网络当前状态,1表示已连接，2表示未连接 */
	public static int network_state = 2;
	/** M2M连接状态,1表示已连接，2表示未连接 */
	public static int m2m_state = 2;
	/** 连接系统状态,1表示已连接，2表示未连接 */
	public static int sys_state = 2;
	/** 新消息状态，1表示有消息，2表示没消息 */
	public static int txt_state = 2;
	/** 检查链接请求超时时间 */
	public static long timeOut = 120 * 1000;
	/**请求服务器操作的时间*/
	public static long qequestTime=0l;
	public static int intervalSpeed = 1000;
	
	public static int total=0;//总数据
	public static User user = new User();
	public static Boolean closeFlag = false;
	public static int offset = 10;
	public static boolean savePasswd=false; //是否保存密码
	public static String username="";
	public static String password="";

	public static void setScreenOffset(Activity activity) {
		int[] dis = DisplayTool.getScreen(DisplayTool.getDisplay(activity)); // windth
																				// height
		if (dis[0] == 480 && dis[1] == 800) {
			offset = 25;
		} else if (dis[0] == 320 && dis[1] == 480) {
			offset = 12;
		} else if (dis[0] == 720 && dis[1] == 1280) {
			offset = 20;
		}
	}
}
