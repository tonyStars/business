package com.club.business.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * @description:日期工具类
 *
 * @author Tom
 * @date 2019-12-12
 */
public class DateUtils {
	
	public static Date addDate(Date date, long day) throws ParseException {
		long time = date.getTime();
		day = day * 24*60*60*1000;
		time += day;
		return new Date(time);
	}
	
	/** 
	 * 把 date日期转为字符串日期
	 * @param type 日期格式  
	 * @param date 日期
	 * @return 字符串 日期格式
	 */
	public static String date2String(int type, Date date) {
		String format = "";
		if (type == 1) {
			format = "yyyy-MM-dd HH:mm:ss";
		} else if (type == 2) {
			format = "yyyy-MM-dd";
		}else if (type == 3) {
			format = "yyyyMMddHHmmss";
		}else if (type == 4) {
			format = "yyyyMMdd";
		}else if (type == 5) {
			format = "yyyy-MM-dd HH";
		}else if (type == 6) {
			format = "yyyy-MM";
		}else if (type == 7) {
			format = "yyyy/MM/dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	
	/** 把字符串格式日期转为date日期
	 * @param dateStr 字符串日期
	 * @param type 为:
	 *  1:yyyy-MM-dd HH:mm:ss 
	 *  2:yyyy-MM-dd
	 *  3:yyyy-MM-dd HH:mm
	 *  
	 * @return date 日期
	 */
	public static Date string2date(String dateStr,int type){
		String dateFormat="";  
		if (type==1) {
			dateFormat="yyyy-MM-dd HH:mm:ss";
		}else if(type==2){
			dateFormat="yyyy-MM-dd";
		}else if (type==3) {
			dateFormat="yyyy-MM-dd HH:mm";
		}else if (type==4) {
			dateFormat="yyyyMMddHHmmss";
		}else if (type==5) {
			dateFormat="yyyy-MM";
		}else if (type==6) {
			dateFormat="yyyy/MM/dd";
		}
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 2个日期是字符串的比较
	 * @param date1  日期1
	 * @param date2  日期2
	 * @param type  类型
	 * @return   1：date1大于date2, -1:date1小于date2, 0:date1等于date2
	 */
	public static int compareDateBy2Str(String date1, String date2, int type) {
		SimpleDateFormat df = null;
		if (type == 0) {
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else if (type == 1) {
			df = new SimpleDateFormat("yyyy-MM-dd");
		}
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 2个日期是日期类型的比较
	 * @param date1  日期1
	 * @param date2  日期2
	 * @return   1：date1大于date2, -1:date1小于date2, 0:date1等于date2
	 */
	public static int compareDateBy2Date(Date date1, Date date2) {
		try {
			Date dt1 = date1;
			Date dt2 = date2;
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 根据日期判断本月有多少天
	 * @author tony
	 */
	public static int dayByMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DATE,-1);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获取两个日期之间的时间间隔(天)
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 */
	public static int getTimeDistanceDays(Date beginDate, Date endDate) {
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(beginDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		long beginTime = beginCalendar.getTime().getTime();
		long endTime = endCalendar.getTime().getTime();
		int betweenDays = (int)((endTime - beginTime) / (1000 * 60 * 60 *24));//先算出两时间的毫秒数之差大于一天的天数
		endCalendar.add(Calendar.DAY_OF_MONTH, -betweenDays);//使endCalendar减去这些天数，将问题转换为两时间的毫秒数之差不足一天的情况
		endCalendar.add(Calendar.DAY_OF_MONTH, -1);//再使endCalendar减去1天
		if(beginCalendar.get(Calendar.DAY_OF_MONTH)==endCalendar.get(Calendar.DAY_OF_MONTH)) {//比较两日期的DAY_OF_MONTH是否相等
			return betweenDays + 1;	//相等说明确实跨天了
		}else {
			return betweenDays + 0;	//不相等说明确实未跨天
		}
	}

	/**
	 * 获取两个日期之间的时间间隔(小时)
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 */
	public static int getTimeDistance(Date beginDate, Date endDate) {
		long begin = beginDate.getTime();
		long end = endDate.getTime();
		int diff = (int)((end - begin) / (1000 * 60 * 60));
		return diff;
	}

	/**
	 * 指定日期的前n天或后n天
	 * @param date 指定的日期
	 * @param day 前或后n天(day为1则：date指定的时间加一天)
	 * @return
	 */
	public static Date getNextDay(Date date,Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);//+1今天的时间加一天
        date = calendar.getTime();
        return date;
    }

	/**
	 * 返回指定日期的前一天(字符串)
	 * @param dateStr 指定日期
	 * @return
	 */
	public static String getNextDayStr(String dateStr) {
    	String result = "";
    	Date date2 = string2date(dateStr,2);
    	Date date = getNextDay(date2,-1);
    	result = date2String(2, date);
        return result;
    }

	/**
	 * 设置当前时间为00:00:00 或者 23:59:59
	 * @param date 传入修改时间的日期
	 * @param flag 0 返回yyyy-MM-dd 00:00:00日期
	 *             1 返回yyyy-MM-dd 23:59:59日期
	 * @return
	 */
	public static Date weeHours(Date date, int flag) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (flag == 0) {
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		} else if (flag == 1) {
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		}
		return cal.getTime();
	}
	
	/**
     * 获取指定年月的第一天
     * @param year 年份
     * @param month 月份
     * @return  当前月份的第一天
     */
    public static Date getFirstDayOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month - 1);  
        cal.set(Calendar.DAY_OF_MONTH, 1);  
        return cal.getTime();
    }
	
	 /**
     * 获取指定年月的最后一天
     * @param year 年份
     * @param month 月份
     * @return  当前月份的最后一天
     */
    public static Date getLastDayOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month - 1);     
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));  
        return cal.getTime();
    }

	/**
	 * 获取当月第一天日期
	 * @return
	 */
	public static Date getMonthFirstDay(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
		return c.getTime();
	}

	/**
	 * 获取当前日期是星期几
	 *
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static int getWeekOfDate(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0){
			w = 0;
		}
		return w == 0 ? 7 : w;
	}

	/**
	 * 获得 当前 或者参数时间是今年的第几周
	 *
	 * @param dt
	 * @return
	 */
	public static int getWeekIndex(Date dt) {
		if (dt == null) {
			dt = new Date();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		long time = 0;
		try {
			/**今年 1月1号 0点*/
			time = sdf.parse(sdf.format(dt)).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (time == 0) {
			return -1;
		}
		/**从今年1月1号到现在过去的时间*/
		long lapsed = System.currentTimeMillis() - time + getWeekOfDate(new Date(time)) * 86400000;
		/**这个星期已经过去的时间*/
		long remainder = lapsed % (86400000 * 7);
		return (int) ((lapsed - remainder) / (86400000 * 7) + (remainder == 0 ? 0 : 1));
	}

	/**
	 * 从开始时间往后增加时间数量
	 * @param date 开始时间
	 * @param addTime 增加时间数量
	 * @param unit 时间单位 时 分 秒 日 月 年 等
	 *
	 * @return
	 */
	public static Date addTime(Date date, int addTime, int unit) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(unit, addTime);
		return cal.getTime();
	}

	/**
	 * 获得当前时间最近一周的第一天时间
	 */
	public static String latestWeekFirstDay(){
		Calendar cal = Calendar.getInstance();
		cal.add(cal.DATE,-6);
		Date date = cal.getTime();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		return sdFormat.format(date);
	}

	/**
	 * 获得当前时间 的 本周周一的时间 字符串
	 */
	public static String getWeekMondayFromNow() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String beginTime = sdf.format(calendar.getTime());
		return beginTime;
	}

	/**
	 * 把时间戳 转为 Date日期
	 * @param timestamp	时间戳
	 * @return date 日期
	 */
	public static Date timestamp2DateTime(Long timestamp) {
		Date date = null;
		if (timestamp != null) {
			date = new Date();
			date.setTime(timestamp);
			return date;
		}
		return date;
	}

	/**
	 * 把 Date日期 转为 时间戳
	 * @param dateTime	日期
	 * @return long型的 时间戳
	 */
	public static Long dateTime2timestamp(Date dateTime) {
		Long longDate = null;
		if (dateTime != null) {
			return dateTime.getTime();
		}
		return longDate;
	}

	/**
     * 判断时间是否在时间段内
     * 
     * @param nowTime 需要判断的时间
     * @param beginTime 开始的时间区间
     * @param endTime 结束的时间区间
     * @return
     */
	public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);
		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		if (date.after(begin) && date.before(end)) {
			return true;
		} else if (nowTime.compareTo(beginTime) == 0 || nowTime.compareTo(endTime) == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取传入日期所在月的第一天
	 * @param date
	 * @return
	 */
	public static Date getFirstDayDateOfMonth(final Date date) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		final int last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, last);
		return cal.getTime();
	}

	/**
	 * 判断两个日期是否为同一天
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		LocalDate dateOne = date2LocalDate(date1);
		LocalDate dateTwo = date2LocalDate(date2);
		return dateOne.isEqual(dateTwo);
	}

	/**
	 * 授信周期时间计算公式 - 返回开始时间和结束时间计算的最后一个周期的开始日期
	 * @param firstDt 计算开始时间
	 * @param secentDt 计算结束时间
	 * @param interval 周期间隔(可以传天,可以传月,根据下个参数 unit 做计算)
	 * @param unit 周期类型(天,月...)(ChronoUnit.DAYS,ChronoUnit.MONTHS)
	 * @return
	 */
	public static LocalDate calcCycleNum(Date firstDt, Date secentDt, long interval, TemporalUnit unit){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String firstDtStr = sdf.format(firstDt);
		String secentDtStr = sdf.format(secentDt);
		LocalDate start = LocalDate.parse(firstDtStr);
		LocalDate end = LocalDate.parse(secentDtStr);
		/**计算开始时间到结束时间的总共周期数(unit为天则是天数,unit为月则是月数)*/
		long space = start.until(end, unit);
		LocalDate plus = start.plus((space / interval) * interval, unit);
		return plus;
	}

	/**
	 * LocalDate转Date
	 * @param localDate
	 * @return
	 */
	public static Date localDate2Date(LocalDate localDate) {
		if(null == localDate) {
			return null;
		}
		ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
		return Date.from(zonedDateTime.toInstant());
	}

	/**
	 * Date转换成LocalDate
	 * @param date
	 * @return
	 */
	public static LocalDate date2LocalDate(Date date) {
		if(null == date) {
			return null;
		}
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/**
	 * LocalDateTime转换成Date
	 * @param localDateTime
	 * @return
	 */
	public static Date localDateTime2Date(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 *  Date转换成LocalDateTime
	 */
	public static LocalDateTime date2LocalDateTime() {
		java.util.Date date = new java.util.Date();
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone);
	}

}
