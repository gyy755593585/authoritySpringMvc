/**
 *
 */
package com.wuji.authority.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yayun
 *
 */
public class DateUtil {

	public static final String DATE_PATTERN = "yyyy-MM-dd";

	public static final String TIME_PATTREN = "yyyy-MM-dd HH:mm:ss";

	public static final String TIME_PATTREN_FULL = "yyyy-MM-dd HH:mm:ss.S";

	public static final String TIME_PATTREN_COMPACTED = "yyyyMMddhhmmss";

	public static final String TIME_PATTREN_SHORT = "MMddhhmmss";// MMDDhhmmss

	public static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public static String getDateStr(Date date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		df.setLenient(false);
		return df.format(date);
	}

	public static Date getDate(String dateStr, String pattern) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			df.setLenient(false);
			return df.parse(dateStr);
		} catch (ParseException ex) {
			logger.error(ex.getMessage());
			return null;

		}
	}

	public static Date addDays(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, days);
		return c.getTime();
	}

	/**
	 * 获取一个日期某一部分的值
	 *
	 * @param date
	 * @param fiedNum
	 * @return
	 */
	public static int getValueFromDate(Date date, int fiedNum) {

		Calendar c = Calendar.getInstance();

		c.setTime(date);

		return c.get(fiedNum);
	}

	/**
	 * 把一个标准日期转化成formart格式的日期
	 *
	 * @param date
	 * @param formart
	 * @return
	 */
	public static Date getFormatDate(Date date, String formart) {

		String desc = DateUtil.getDateStr(date, formart);

		return DateUtil.getDate(desc, formart);

	}

	/**
	 * 对一个日期的某一部分加减操作
	 *
	 * @param date 日期
	 * @param partOfDate 年，月，日对应的整数
	 * @param increment 增量
	 * @return
	 */
	public static Date operationDate(Date date, int partOfDate, int increment) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		calendar.add(partOfDate, increment);

		Date currentDate = calendar.getTime();

		return currentDate;
	}

	/**
	 * 根据开始日期和结束日期求出时间范围的集合
	 *
	 * @param startTime 开始日期
	 * @param endTime 结束日期
	 * @return List<Date>
	 */
	public static List<Date> getThePeriodOfTwoDates(Date startTime, Date endTime) {

		if (!startTime.before(endTime) && !startTime.equals(endTime)) {

			throw new RuntimeException("开始日期不能大于结束日期");

		}

		Calendar start = Calendar.getInstance();

		start.setTime(startTime);

		Calendar end = Calendar.getInstance();

		end.setTime(endTime);

		int min = start.get(Calendar.DAY_OF_YEAR);

		int max = end.get(Calendar.DAY_OF_YEAR);

		List<Date> dates = new ArrayList<Date>();

		for (int i = 0; i <= max - min; i++) {

			start.add(Calendar.DAY_OF_YEAR, i);

			dates.add(start.getTime());

			start.setTime(startTime);
		}

		return dates;

	}

	/**
	 * 获取系统当前时间
	 *
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static Timestamp getNow() {
		DateFormat format = new SimpleDateFormat(TIME_PATTREN);
		String now = format.format(new Date());
		Timestamp ts = Timestamp.valueOf(now);
		return ts;
	}

	public static Timestamp getNowFull() {
		DateFormat format = new SimpleDateFormat(TIME_PATTREN_FULL);
		String now = format.format(new Date());
		Timestamp ts = Timestamp.valueOf(now);
		return ts;
	}
}
