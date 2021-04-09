/**
 * $Id:$
 * Copyright 2014-2018 Hebei Sinounited Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * CalendarUtils
 *
 * @author Jades.He
 * @since 2014.11.20
 */
public class CalendarUtils extends DateUtils {

    private static String[] cnArray = new String[]{"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一",
            "十二"};

    private static final Logger logger = LoggerFactory.getLogger(CalendarUtils.class);

    public static final long SECOND = 1000L;

    public static final long MINUTE = 60 * SECOND;

    public static final long HOUR = 60 * MINUTE;

    public static final long DAY = 24 * HOUR;

    public static final long WEEK = 7 * DAY;

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String DATE_FORMAT_SLASH = "yyyy/MM/dd";

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATETIME_FORMAT_SLASH = "yyyy/MM/dd HH:mm:ss";

    public static final String DATETIME_FORMAT_NOSPLIT = "yyyyMMddHHmmss";

    public static final String DATETIME_LONG_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String DATETIME_LONG_FORMAT_SLASH = "yyyy/MM/dd HH:mm:ss.SSS";

    public static final String DATE_FORMAT_CHINESE = "yyyy年MM月dd日";

    public static final String YEAR_MONTH_DAY_FORMAT = "yyyyMMdd";

    public static final String YEAR_MONTH_FORMAT = "yyyyMM";

    public static final String YEAR_FORMAT = "yyyy";

    public static final String MONTH_DAY_FORMAT = "MMdd";

    public static final String MONTH_FORMAT = "MM";

    public static final String DAY_FORMAT = "dd";

    /**
     * Gets start of the day with time "00:00:00.000".
     *
     * @param date Date
     * @return Date
     */
    public static Date getDayBegin(final Date date) {
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.set(Calendar.HOUR, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MILLISECOND, 0);
        return new Date(gc.getTimeInMillis());
    }

    /**
     * Gets end of the day with time "23:59:59.999".
     *
     * @param date Date
     * @return Date
     */
    public static Date getDayEnd(final Date date) {
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.set(Calendar.HOUR_OF_DAY, 23);
        gc.set(Calendar.MINUTE, 59);
        gc.set(Calendar.SECOND, 59);
        gc.set(Calendar.MILLISECOND, 999);
        return new Date(gc.getTimeInMillis());
    }

    /**
     * Checks if two date objects are on the same day ignoring year.
     *
     * @param date1 the first date, not altered
     * @param date2 the second date, not altered
     * @return true if they represent the same day
     * @author jades.he 2014-9-4
     */
    public static boolean isSameDayIgnoreYear(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        return (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH))
                && (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Gets a date with giving milliseconds.
     *
     * @param milliseconds the milliseconds since January 1, 1970, 00:00:00 GMT.
     * @return Date
     */
    public static Date parseDate(long milliseconds) {
        return new Date(milliseconds);
    }

    /**
     * 将Date对象格式化为字符串，格式为：yyyy-MM-dd
     *
     * @param date Date
     * @return 日期字符串
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }

    /**
     * 将Date对象格式化为用户指定格式的字符串
     *
     * @param date
     * @param pattern
     * @return
     * @author Dong
     * @since 2017年3月7日 上午11:43:58
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null) return null;

        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 将Date对象格式化为字符串，格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param date Date
     * @return 日期字符串
     */
    public static String formatDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(DATETIME_FORMAT).format(date);
    }

    /**
     * 将Date对象格式化为字符串，格式为：yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param date Date
     * @return 日期字符串
     */
    public static String formatLongDateTime(Date date) {
        return formatDate(date, DATETIME_LONG_FORMAT);
    }

    /**
     * 将毫秒数格式化为日期字符串，格式为：yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param milliseconds the milliseconds since January 1, 1970, 00:00:00 GMT.
     * @return 日期字符串
     */
    public static String formatLongDateTime(long milliseconds) {
        return formatDate(new Date(milliseconds), DATETIME_LONG_FORMAT);
    }

    /**
     * 格式化为中文年月 二〇一七年三月
     *
     * @param date
     * @return
     * @author Leon.Dong
     * @since 2017年3月6日 下午5:03:04
     */
    public static String formatDateOfCnMonth(Date date) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String year = String.valueOf(cal.get(Calendar.YEAR));
        Integer month = cal.get(Calendar.MONTH) + 1;
        String resultStr = "";
        for (int i = 0; i < year.length(); i++) {
            resultStr += cnArray[Integer.valueOf(String.valueOf(year.charAt(i)))];
        }
        resultStr += "年";
        resultStr += cnArray[month];
        resultStr += "月";
        return resultStr;
    }

    /**
     * 尝试使用以下格式对日期字符串进行转换，转换失败返回null.
     * <ol>
     * <li>yyyy-MM-dd HH:mm:ss
     * <li>yyyy-MM-dd HH:mm:ss.SSS
     * <li>yyyyMMddHHmmss
     * <li>yyyy-MM-dd
     * <li>yyyy年MM月dd日
     * </ol>
     *
     * @param dateStr 日期字符串
     * @return Date
     */
    public static Date parseDateWithTry(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        String[] formats = {DATETIME_FORMAT, // NL
                DATETIME_LONG_FORMAT, // NL
                DATETIME_FORMAT_NOSPLIT, //NL
                DATE_FORMAT, // NL
                DATE_FORMAT_CHINESE, // NL
                DATE_FORMAT_SLASH, // NL
                DATETIME_FORMAT_SLASH, // NL
                DATETIME_LONG_FORMAT_SLASH};

        Date date = null;
        try {
            date = DateUtils.parseDate(dateStr, formats);
        } catch (ParseException e) {
            logger.error("Cannot parse {} as date.", dateStr);
        }
        return date;
    }

    /**
     * 将毫秒数转换为Date对象
     *
     * @param milliseconds the milliseconds since January 1, 1970, 00:00:00 GMT.
     * @return Date
     */
    public static Date parseLongDateTime(long milliseconds) {
        return new Date(milliseconds);
    }

    /**
     * 获得日期与今年相差的年份数，如年龄
     *
     * @param date
     * @return int
     * @author Dong
     * @since 2017年3月7日 上午11:42:38
     */
    public static int getYearDifference(Date date) {
        return getYearDifference(date, new Date());
    }

    /**
     * 获得两个日期之间相差的年数
     *
     * @param date1
     * @param date2
     * @return int
     * @author Leon.Dong
     * @since 2017年3月6日 下午5:03:53
     */
    public static int getYearDifference(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);

        return year2 - year1;
    }

    /**
     * 计算两个日期之间的月份差
     *
     * @param date1
     * @param date2
     * @return
     * @author Leon.Dong
     * @since 2017年3月6日 下午5:03:45
     */
    public static int getMonthDifference(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int c = (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 12 + cal1.get(Calendar.MONTH)
                - cal2.get(Calendar.MONTH);
        return c;
    }

    /**
     * 计算两个日期之间的天数差
     *
     * @param start
     * @param end
     * @return
     */
    public static int getDayDifference(Date start, Date end) {
        try {
            return (int) ((parseDate(formatDate(end, "yyyy-MM-dd"), "yyyy-MM-dd").getTime() - parseDate(formatDate(start, "yyyy-MM-dd"), "yyyy-MM-dd").getTime()) / 86400000);
        } catch (ParseException e) {
            return 0;
        }
    }

    /**
     * 计算指定秒后的时间
     *
     * @param date
     * @param amount
     * @return
     * @author Leon.Dong
     * @since 2017年3月6日 下午5:04:11
     */
    public static Date DateAddSecond(Date date, Integer amount) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal1.add(Calendar.SECOND, amount);
        return cal1.getTime();
    }

    /**
     * 给日期增加或减少天数
     *
     * @param date Date
     * @param day  天数
     * @return Date
     * @author 何珏 2014-11-20
     */
    public static Date addDays(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    /**
     * 给日期+00:00:00.000
     *
     * @param date Date
     * @return Date
     */
    public static Date getBeginDateByDate(Date date) {
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MILLISECOND, 0);
        return gc.getTime();
    }

    /**
     * 给日期+23:59:59.999
     *
     * @param date Date
     * @return Date
     */
    public static Date getEndDateByDate(Date date) {
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);
        gc.set(Calendar.HOUR_OF_DAY, 23);
        gc.set(Calendar.MINUTE, 59);
        gc.set(Calendar.SECOND, 59);
        gc.set(Calendar.MILLISECOND, 999);
        return gc.getTime();
    }

    /**
     * 获取指定日期的 当周开始时间
     *
     * @param date
     * @return
     * @author Leon
     * @since 2017年6月21日 下午4:56:24
     */
    public static Date getStartDateOfWeek(Date date) {
        if (date == null) {
            date = new Date();
        }

        Calendar gc = Calendar.getInstance();
        gc.setTime(date);

        int dayofweek = gc.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        gc.add(Calendar.DATE, 2 - dayofweek);
        return getBeginDateByDate(gc.getTime());
    }

    /**
     * 获取指定日期的 当周结束时间
     *
     * @param date
     * @return
     * @author Leon
     * @since 2017年6月21日 下午4:56:46
     */
    public static Date getEndDateOfWeek(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar gc = Calendar.getInstance();
        gc.setTime(getStartDateOfWeek(date));
        gc.add(Calendar.DAY_OF_WEEK, 6);
        return getEndDateByDate(gc.getTime());
    }

    /**
     * 获取指定日期的 当月的开始时间
     *
     * @param date
     * @return
     * @author Leon
     * @since 2017年6月21日 下午5:12:01
     */
    public static Date getStartDateOfMonth(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);

        Calendar calendar = Calendar.getInstance();

        calendar.set(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), 1);
        return getBeginDateByDate(calendar.getTime());

    }

    /**
     * 获取指定日期的 当月的结束时间
     *
     * @param date
     * @return
     * @author Leon
     * @since 2017年6月21日 下午5:11:45
     */
    public static Date getEndDateOfMonth(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        Calendar calendar = Calendar.getInstance();
        calendar.set(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), day);
        return getEndDateByDate(calendar.getTime());
    }

    /**
     * @return Date
     * @Title getCurTime
     * @Description: 从中国科学院国家授时中心获取当前时间
     * @author: Wang Mei
     */
    public static Date getCurTime(int isDebug) {
        if (isDebug == 1) {
            return new Date();
        }
        try {
            URL url = new URL("http://www.ntsc.ac.cn");
            URLConnection uConn = url.openConnection();

            long lCurTime = uConn.getDate();
            if (lCurTime > 0) {
                return new Date(lCurTime);
            } else {
                return new Date();
            }
        } catch (Exception e) {
            return new Date();
        }
    }
}
