package com.liu.nyxs.utils;

import io.netty.handler.codec.DateFormatter;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间工具类
 * 
 * @author ake
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	public static String YYYY = "yyyy";

	public static String YYYY_MM = "yyyy-MM";

	public static String YYYY_MM_DD = "yyyy-MM-dd";

	public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 年
	 */
	public static final String YEAR_PERIOD = "year";
	/**
	 * 月
	 */
	public static final String MONTH_PERIOD = "month";
	/**
	 * 日
	 */
	public static final String DAY_PERIOD = "day";
	/**
	 * 时
	 */
	public static final String HOUR_PERIOD = "hour";
	/**
	 * 23时.
	 */
	private static final int HOUR_23 = 23;
	/**
	 * 59分.
	 */
	private static final int MINUTE_59 = 59;
	/**
	 * 59秒.
	 */
	private static final int SECOND_59 = 59;
	/**
	 * 999毫秒.
	 */
	private static final int MILLISECOND_999 = 999;
	/**
	 * 一天为1000 * 60 * 60 * 24毫秒.
	 */
	public static final long DAY_MILLISECOND = 1000 * 60 * 60 * 24;

	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
			"yyyy.MM.dd HH:mm", "yyyy.MM","yyyyMMddHHmmss" };

	/**
	 * 获取当前Date型日期
	 * 
	 * @return Date() 当前日期
	 */
	public static Date getNowDate() {
		return new Date();
	}

	/**
	 * 获取当前日期, 默认格式为yyyy-MM-dd
	 * 
	 * @return String
	 */
	public static String getDate() {
		return dateTimeNow(YYYY_MM_DD);
	}

	public static final String getTime() {
		return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
	}

	public static final String dateTimeNow() {
		return dateTimeNow(YYYYMMDDHHMMSS);
	}

	public static final String dateTimeNow(final String format) {
		return parseDateToStr(format, new Date());
	}

	public static final String dateTime(final Date date) {
		return parseDateToStr(YYYY_MM_DD, date);
	}

	public static final String parseDateToStr(final String format, final Date date) {
		return new SimpleDateFormat(format).format(date);
	}

	public static final Date dateTime(final String format, final String ts) {
		try {
			return new SimpleDateFormat(format).parse(ts);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 日期路径 即年/月/日 如2018/08/08
	 */
	public static final String datePath() {
		Date now = new Date();
		return DateFormatUtils.format(now, "yyyy/MM/dd");
	}

	/**
	 * 日期路径 即年/月/日 如20180808
	 */
	public static final String dateTime() {
		Date now = new Date();
		return DateFormatUtils.format(now, "yyyyMMdd");
	}

	/**
	 * 日期型字符串转化为日期 格式
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取服务器启动时间
	 */
	public static Date getServerStartDate() {
		long time = ManagementFactory.getRuntimeMXBean().getStartTime();
		return new Date(time);
	}

	/**
	 * 计算两个时间差
	 */
	public static String getDatePoor(Date endDate, Date nowDate) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - nowDate.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		// long sec = diff % nd % nh % nm / ns;
		return day + "天" + hour + "小时" + min + "分钟";
	}

	public static long getDatePoorHour(Date endDate, Date nowDate) {
		long nm = 1000 * 60;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - nowDate.getTime();
		// 计算差多少分钟
		long min = diff / nm;
		return min;
	}

	/*
	 * 获取某年某月第一天
	 * 
	 */
	public static String getFisrtDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最小天数
		int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最小天数
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String firstDayOfMonth = sdf.format(cal.getTime());

		return firstDayOfMonth;
	}

	/*
	 * 获取某年某月最后一天
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime());
		return lastDayOfMonth;
	}

	/*
	 * 获取某年某月的天数集合，xxxx-xx-xx
	 */
	public static List<String> getMonthFullDay(int year, int month) {
		SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
		List<String> fullDayList = new ArrayList<>(32);
		// 获得当前日期对象
		Calendar cal = Calendar.getInstance();
		cal.clear();// 清除信息
		cal.set(Calendar.YEAR, year);
		// 1月从0开始
		cal.set(Calendar.MONTH, month - 1);
		// 当月1号
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int j = 1; j <= count; j++) {
			fullDayList.add(dateFormatYYYYMMDD.format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		return fullDayList;
	}

	/*
	 * 获取某年某月的天数集合，1日，2日，3日
	 */
	public static List<String> getMonthFullDay1(int year, int month) {

		SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("dd");
		List<String> fullDayList = new ArrayList<>(32);
		// 获得当前日期对象
		Calendar cal = Calendar.getInstance();
		cal.clear();// 清除信息
		cal.set(Calendar.YEAR, year);
		// 1月从0开始
		cal.set(Calendar.MONTH, month - 1);
		// 当月1号
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int j = 1; j <= count; j++) {
			fullDayList.add(dateFormatYYYYMMDD.format(cal.getTime()) + "日");
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		return fullDayList;
	}
	
	/**
	 * @param lastDealTime,上次处理的时间
	 * @param intervalTime,时间间隔(毫秒)
	 * @return 是否大于时间间隔,大于true,小于false
	 * @throws Exception
	 */
	public static boolean dealTimeInterval(Object lastDealTime, int intervalTime) throws Exception {
		Date now = new Date();
		Date lastDealTimeDate = stringToDate(lastDealTime.toString());
		// 时间间隔大于一分钟则进入队列
		if (now.getTime() - lastDealTimeDate.getTime() > intervalTime) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * String的日期类型转Date
	 */
	public static Date stringToDate(String date) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateString = formatter.parse(date);
		return dateString;
	}
	
	public static String getSummaryTime(Date readingTime, String format){
		FastDateFormat fastFormatter = FastDateFormat.getInstance(format);
		String readingTimeStr = fastFormatter.format(readingTime);

		if("yyyy".equals(format)){
			readingTimeStr += "-01-01 00:00:00";
		}else if("yyyy-MM".equals(format)){
			readingTimeStr += "-01 00:00:00";
		}else if("yyyy-MM-dd".equals(format)){
			readingTimeStr += " 00:00:00";
		}else if("yyyy-MM-dd HH".equals(format)){
			readingTimeStr += ":00:00";
		}
		return readingTimeStr;
	}

	/**
	 * 每月的第一天
	 * @param date
	 * @return java.util.Date
	 */
	public static Date getFirstDateOfMonth(Date date) {
		int[] ymd = getYMD(date);
		return buildDate(ymd[0], ymd[1], 1, 0, 0, 0);
	}

	/**
	 * 每年的第一天
	 * @param date
	 * @return java.util.Date
	 */
	public static Date getFirstDateOfYear(Date date) {
		int year = getYear(date);
		return getFirstDateOfYear(year).getTime();
	}

	/**
	 * 每年的第一天
	 * @param year
	 * @return java.util.Calendar
	 */
	public static Calendar getFirstDateOfYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		// 月份从0开始
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar;
	}

	/**
	 * 获取年份
	 * @param date
	 * @return int
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 构建日期
	 * @param year
	 * @param month
	 * @param date
	 * @param hourOfDay
	 * @param minute
	 * @param second
	 * @return java.util.Date
	 */
	public static Date buildDate(int year, int month, int date, int hourOfDay,
								 int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, date, hourOfDay, minute, second);
		// 注意: 这里要清空毫秒
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 格式化
	 * @param date
	 * @return int[]
	 */
	public static int[] getYMD(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return new int[] { calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE) };

	}

	/**
	 * 获取一天的开始时间
	 * @param date
	 * @return java.util.Date
	 */
	public static Date getFirstTimeInDay(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取date的最后时间,即23时59分59秒999毫秒.
	 *
	 * @param date Date日期.
	 * @return date的最后时间.
	 */
	public static Date getLastTimeInDay(Date date) {
		ParamChecker.checkNull(date, "date");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, HOUR_23);
		calendar.set(Calendar.MINUTE, MINUTE_59);
		calendar.set(Calendar.SECOND, SECOND_59);
		calendar.set(Calendar.MILLISECOND, MILLISECOND_999);
		return calendar.getTime();
	}

	/**
	 * 获取日期date所在月的最后一天的日期,不会改变时间部分.
	 * 例如：2008.2.15-20:53:40 ,则返回 2008.2.29-20:53:40
	 * @param date 日期.
	 * @return 日期所在月的最后一天的日期.
	 */
	public static Date getLastDayInMonth(Date date) {
		ParamChecker.checkNull(date, "date");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, 1);
		c.roll(Calendar.DATE, -1);
		return c.getTime();
	}



	/**
	 *将时间加一秒
	 * @param dtStr
	 * @return dtStr
	 */
	public static String addOneSecond(String dtStr) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c=Calendar.getInstance();
		Date dt=sdf.parse(dtStr);
		c.setTime(dt);
		c.add(Calendar.SECOND, 1);
		return sdf.format(c.getTime());
	}

	/**
	 * 获取开始日期结束日期之间的天数.
	 * <p>
	 * 注意：计算之前会先截去日期的时间部分
	 * <p>
	 * 例如：计算 2008.2.3-23:59:59 与 2008.2.4-00:00:00 之间的天数,
	 * 则先将起止日期处理为 2008.2.3 与 2008.2.4 ,他们之间相差一天,所以最后返回的结果为1.
	 *
	 * @param startDate 开始日期.
	 * @param endDate 结束日期.
	 * @return 开始日期结束日期之间的天数.
	 * @throws IllegalArgumentException
	 *             如果开始日期为null或者结束日期为null.
	 * @throws ParseException 如果开始日期或者结束日期格式化错误.
	 */
	public static long daysBetweenDates(Date startDate, Date endDate) throws ParseException {
		ParamChecker.checkNull(startDate, "startDate");
		ParamChecker.checkNull(endDate, "endDate");
		/*
		 * 不能直接用时间相减再除,因为夏令时会让有些天只有23小时有些25小时导致错误。
		 * 所以先把日期转换成字符串,再生成那个字符串的GMT时间,再用GMT去计算天数. GMT没有夏令时.
		 */
		DateFormat format = new SimpleDateFormat("YYYY_MM_DD");
		String startDateString = format.format(getFirstTimeInDay(startDate));
		String endDateString = format.format(getFirstTimeInDay(endDate));
		DateFormat gmtFormat = new SimpleDateFormat("YYYY_MM_DD");
		Calendar gmtCal = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
		gmtFormat.setCalendar(gmtCal);
		Date sDate = gmtFormat.parse(startDateString);
		Date eDate = gmtFormat.parse(endDateString);

		return (eDate.getTime() - sDate.getTime()) / DAY_MILLISECOND;
	}

	public static boolean isSameDay(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) && (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));

	}
}
