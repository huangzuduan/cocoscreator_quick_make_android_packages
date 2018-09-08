package config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public final class DateTime {

	private DateTime() {
	}

	/**
	 * 默认日期格式
	 */
	private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 默认日期格式
	 */
	private static final String DEFAULT_FORMAT1 = "yyyyMMdd";
	
	
	/**
	 * 获取系统当前时间戳
	 * 
	 * @return int
	 */
	public static int time() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	/**
	 * 获取系统当前时间
	 * 
	 * @return long
	 */
	public static long millisTime() {
		return System.currentTimeMillis();
	}

	/**
	 * 把当前时间格式化成yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String
	 */
	public static String date() {
		return date(DEFAULT_FORMAT);
	}

	/**
	 * 把当前时间格式化
	 * 
	 * @param format
	 * @return String
	 */
	public static String date(String format) {
		return date(format, System.currentTimeMillis());
	}

	/**
	 * 把时间戳（秒）格式化成yyyy-MM-dd HH:mm:ss
	 * 
	 * @param timestamp
	 * @return String
	 */
	public static String date(int timestamp) {
		return date(DEFAULT_FORMAT, timestamp);
	}

	/**
	 * 把时间戳（秒）格式化成yyyy-MM-dd HH:mm:ss
	 * 
	 * @param timestamp
	 * @return String
	 */
	public static int timeToYmd(int timestamp) {
		return Integer.valueOf(date(DEFAULT_FORMAT1, timestamp));
	}
	
	/**
	 * 把时间戳（毫秒）格式化成yyyy-MM-dd HH:mm:ss
	 * 
	 * @param timestamp
	 * @return String
	 */
	public static String date(long timestamp) {
		return date(DEFAULT_FORMAT, timestamp);
	}

	/**
	 * 把时间戳格式化
	 * 
	 * @param format
	 * @param timestamp
	 *            秒
	 * @return String
	 */
	public static String date(String format, int timestamp) {
		long l = (long) timestamp * 1000;
		return date(format, l);
	}

	/**
	 * 把时间戳格式化
	 * 
	 * @param format
	 * @param timestamp
	 *            毫秒
	 * @return String
	 */
	public static String date(String format, long timestamp) {
		return new SimpleDateFormat(format).format(timestamp);
	}

	/**
	 * 时间转化成时间戳
	 * 
	 * @param time
	 * @return int
	 */
	public static int strToTime(String time) {
		return strToTime(time, DEFAULT_FORMAT);
	}

	/**
	 * 时间转化成时间戳
	 * 
	 * @param time
	 * @param format
	 * @return int
	 */
	public static int strToTime(String time, String format) {
		Date date = str2Date(time, format);
		return (int) (date.getTime() / 1000);
	}

	/**
	 * 字符串转换成日期 如果转换格式为空，则利用默认格式进行转换操作
	 * 
	 * @param str
	 *            字符串
	 * @param format
	 *            日期格式
	 * @return 日期
	 */
	public static Date str2Date(String str, String format) {
		if (null == str || "".equals(str)) {
			return new Date();
		}
		// 如果没有指定字符串转换的格式，则用默认格式进行转换
		if (null == format || "".equals(format)) {
			format = DEFAULT_FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(str);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Date();
	}

	/**
	 * 获得今天凌晨时间戳
	 * 
	 * @return int
	 */
	public static int dayBreak() {
		String nowTime = DateTime.date("yyyy-MM-dd") + " 00:00:00"; // 获取今天日期时间
		return DateTime.strToTime(nowTime); // 转化时间戳
	}

	/**
	 * 获得当天凌晨 格式:yyyy-MM-dd
	 * 
	 * @param day
	 *            : "2011-12-14"
	 * @return int
	 */
	public static int dayBreak(String day) {
		String nowTime = day + " 00:00:00"; // 获取当天日期时间
		return DateTime.strToTime(nowTime); // 转化时间戳
	}

	/**
	 * 当天凌晨 格式:yyyyMMdd
	 * 
	 * @param ymd
	 * @return
	 * @author shen 2012-6-9
	 */
	public static int dayBreak(int ymd) {
		String nowTime = ymd + " 00:00:00"; // 获取当天日期时间
		return strToTime(nowTime, "yyyyMMdd HH:mm:ss");// 转化时间戳
	}

	/**
	 * 今天某个时间点
	 * 
	 * @param clock
	 * @return int
	 */
	public static int getTimeByClock(String clock) {
		String time = DateTime.date("yyyy-MM-dd") + " " + clock;
		return DateTime.strToTime(time);
	}

	/**
	 * 获取今天星期几
	 * 
	 * @return int
	 */
	public static int getDayOfWeek() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 获取今天星期几
	 * 
	 * @param time
	 * @return int
	 */
	public static int getDayOfWeek(int time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis((long) time * 1000);
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 获取输入的时间戳是当年的第几周 2012-3-13
	 * 
	 * @param timestamp
	 *            时间戳
	 * @return weekOfYear
	 * @author ruan
	 */
	public static int getWeekOfYear(int timestamp) {
		int week = Integer.parseInt(date("ww", timestamp));
		int year = Integer.parseInt(date("yyyy", timestamp));
		int tmp = Integer.parseInt(date("MM", timestamp));
		if (week >= 5 && tmp <= 1) {
			year -= 1;
		}
		if (week == 1 && tmp == 12) {
			year += 1;
		}
		return year * 100 + week;
	}

	/**
	 * 获取输入的日期戳是当年的第几周 2012-3-14
	 * 
	 * @param str
	 *            日期字符串
	 * @return weekOfYear
	 * @author ruan
	 */
	public static int getWeekOfYear(String str) {
		return getWeekOfYear(strToTime(str));
	}

	/**
	 * 获取当前时间是当年的第几周 2012-3-14
	 * 
	 * @return weekOfYear
	 * @author ruan
	 */
	public static int getWeekOfYear() {
		return getWeekOfYear(time());
	}

	/**
	 * 把输入的日期转成指定的格式
	 * 
	 * @author chenjy 2012-3-28
	 * @param format1
	 *            源日期格式
	 * @param date
	 *            时间
	 * @param formate2
	 *            新日期格式
	 * @return String
	 */
	public static String dateChangeFormat(String format1, int date,
			String formate2) {
		SimpleDateFormat sdf = new SimpleDateFormat(format1);
		Date fact = null;
		try {
			fact = sdf.parse(String.valueOf(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat sdfnew = new SimpleDateFormat(formate2);
		return sdfnew.format(fact);
	}

	/**
	 * 获取两个日期相隔天数
	 * 
	 * @param day1
	 *            例:2012-04-10
	 * @param day2
	 *            例:2012-04-15
	 * @return 相隔天数
	 */
	public static int apartDay(String day1, String day2) {
		int apartSecond = DateTime.strToTime(day1, "yyyyMMdd")
				- DateTime.strToTime(day2, "yyyyMMdd");
		return Math.abs(apartSecond / (3600 * 24));
	}

	/**
	 * 获取两个时间相隔天数
	 * 
	 * @param timestamp1
	 *            时间戳
	 * @param timestamp2
	 * @return 相隔天数
	 */
	public static int apartDay(int timestamp1, int timestamp2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String day1 = DateTime.date("yyyy-MM-dd", timestamp1);
		String day2 = DateTime.date("yyyy-MM-dd", timestamp2);
		int days = -1;
		try {
			days = (int) ((sdf.parse(day1).getTime() - sdf.parse(day2)
					.getTime()) / (1000 * 3600 * 24));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Math.abs(days);
	}

	//
	/**
	 * 获取下一天 的日期
	 * 
	 * @param ymd
	 *            例:2012-04-10
	 * @return 20120411 错误则返回null
	 */
	public static Integer tomorrowDay(Integer ymd) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Integer tomorrowYmd = null;
		try {
			tomorrowYmd = parseInt(sdf.format(new Date(sdf
					.parse(ymd.toString()).getTime() + ((1000 * 3600 * 24))) // 加一天
					));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return tomorrowYmd;
	}

	/**
	 * 把当前时间格式化
	 * 
	 * @param format
	 * @return int (yyyyMMdd)
	 */
	public static int today() {
		String format = "yyyyMMdd";
		return parseInt(date(format, System.currentTimeMillis()));
	}

	/**
	 * 获得昨天凌晨00:00:00 时间戳
	 * 
	 * @return
	 * @author shen 2012-6-4
	 */
	public static int yesterdayBreak() {
		int today = dayBreak();
		return today - 86400;
	}

	/**
	 * 获取昨天的日期
	 * 
	 * @param day
	 *            格式yyyyMMdd
	 * @return
	 * @author shen 2012-5-29
	 */
	public static int yesterday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		long timestamp = (long) yesterdayBreak() * 1000;
		String yesterday = sdf.format(new Date(timestamp));
		return parseInt(yesterday);
	}

	/**
	 * 把字符串时间转成map，方便使用
	 * 
	 * @param s
	 * @return HashMap
	 */
	public static HashMap<String, Integer> strTimeToMap(String s) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		String[] arr = s.split(" ");
		if (arr.length > 1) {
			map.put("week", Integer.parseInt(arr[0]));
			String[] arrHms = arr[1].split(":");
			map.put("hour", Integer.parseInt(arrHms[0]));
			map.put("minute", Integer.parseInt(arrHms[1]));
			map.put("second", Integer.parseInt(arrHms[2]));
		} else if (arr.length == 1) {
			String[] arrHms = arr[0].split(":");
			map.put("hour", Integer.parseInt(arrHms[0]));
			map.put("minute", Integer.parseInt(arrHms[1]));
			map.put("second", Integer.parseInt(arrHms[2]));
		}
		return map;
	}

	/**
	 * 计算一个时间段相隔多少秒
	 * 
	 * @author ruan 2012-9-4
	 * @param hm
	 *            12:00-13:00
	 */
	public static int timeDiff(String hm) {
		// 思路:12:00-13:00，把它拆分成 小时*60 + 分钟 ，算出时间段内相差多少分钟
		// 然后再转化成秒
		String[] time = hm.split("-");
		String[] minArr1 = time[0].split(":");
		String[] minArr2 = time[1].split(":");
		return (Integer.parseInt(minArr2[0]) * 60
				+ Integer.parseInt(minArr2[1]) - Integer.parseInt(minArr1[0])
				* 60 + Integer.parseInt(minArr1[1])) * 60;
	}

	/**
	 * 转换
	 * 
	 * @param obj
	 * @return
	 * @throws Err
	 */
	private static int parseInt(Object obj) {
		int ret = (int) Double.parseDouble(obj.toString());
		return ret;
	}

}
