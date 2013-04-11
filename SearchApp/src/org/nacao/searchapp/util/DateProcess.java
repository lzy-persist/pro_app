package org.nacao.searchapp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * date��������
 * 
 */
public class DateProcess {

	/**
	 * �õ���ǰϵͳʱ��yyyy-MM-dd hh:mm:ss�ַ���
	 * 
	 * @return
	 */
	public static String getSysTime() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(java.util.Calendar.getInstance().getTime());
		return date;
	}

	/**
	 * �õ���ǰϵͳʱ��yyyy-MM-dd hh:mm:ss.S�ַ���
	 * 
	 * @return
	 */
	public static String getSysMilTime() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		String date = df.format(java.util.Calendar.getInstance().getTime());
		return date;
	}

	/**
	 * �õ���ǰϵͳʱ��yyyy-MM-dd�ַ���
	 * 
	 * @return
	 */
	public static String getSysDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(java.util.Calendar.getInstance().getTime());
		return date;
	}

	/**
	 * �õ���ǰϵͳʱ��yyyyMMdd�ַ���
	 * 
	 * @return
	 */
	public static String getDate() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String date = df.format(java.util.Calendar.getInstance().getTime());
		return date;
	}

	public static String getNum() {
		DateFormat df = new SimpleDateFormat("HH:mm:ss.S");
		String date = df.format(java.util.Calendar.getInstance().getTime());
		return date;
	}

	/**
	 * ���㵱ǰ����ǰ������Ǻ���
	 */
	public static String afterNDay(int n) {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		c.setTime(new Date());
		c.add(Calendar.DATE, n);
		Date d2 = c.getTime();
		String s = df.format(d2);

		return s;
	}

	/**
	 * ���㵱ǰ����ǰ���»��Ǻ���
	 */
	public static String afterMon(int n) {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		c.setTime(new Date());
		c.add(Calendar.MONTH, n);
		Date d2 = c.getTime();
		String s = df.format(d2);

		return s;
	}

	/**
	 * ����ĳһ�����һ��
	 * 
	 * @param d
	 * @return
	 */
	public static Date afterDay(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}

	public static String changeFormat(String date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat df = new SimpleDateFormat(format);
		return df.format(d);
	}
	/**
	 * ���������ʱ���
	 * @param beforeTime
	 * @param endTime
	 * @return
	 */
	public static String timeDiffrence(String beforeTime,String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date d = null;
		try {
			d = sdf.parse(beforeTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.util.Date d1 = null;
		try {
			d1 = sdf.parse(endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		long timethis = calendar.getTimeInMillis();
		calendar.setTime(d1);
		long timeend = calendar.getTimeInMillis();
		long theday = (timeend - timethis) / (1000 * 60 * 60 * 24);
		return theday+"";
	}

}
