package org.nacao.searchapp.util;

import org.nacao.searchapp.model.User;

import android.app.Activity;

public class Variable {
	
	/** WIFI��ǰ״̬,1��ʾ�����ӣ�2��ʾδ���� */
	public static int wifi_state = 2;
	/** �������統ǰ״̬,1��ʾ�����ӣ�2��ʾδ���� */
	public static int network_state = 2;
	/** M2M����״̬,1��ʾ�����ӣ�2��ʾδ���� */
	public static int m2m_state = 2;
	/** ����ϵͳ״̬,1��ʾ�����ӣ�2��ʾδ���� */
	public static int sys_state = 2;
	/** ����Ϣ״̬��1��ʾ����Ϣ��2��ʾû��Ϣ */
	public static int txt_state = 2;
	/** �����������ʱʱ�� */
	public static long timeOut = 120 * 1000;
	/**���������������ʱ��*/
	public static long qequestTime=0l;
	public static int intervalSpeed = 1000;
	
	public static int total=0;//������
	public static User user = new User();
	public static Boolean closeFlag = false;
	public static int offset = 10;
	public static boolean savePasswd=false; //�Ƿ񱣴�����
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
