package com.tool.box.utils;

import com.sun.jmx.snmp.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author v_haimiyang
 * @Date 2023/6/28 14:29
 * @Version 1.0
 */
public class DateUtils extends PropertyEditorSupport {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    private static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy年MM月dd日",
            "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
            "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMdd"};

    public static ThreadLocal<SimpleDateFormat> yyyymmddhhmmss = ThreadLocal.withInitial(()
            -> new SimpleDateFormat(yyyyMMddHHmmss));

    public static ThreadLocal<SimpleDateFormat> threadLocal_default_date_pattern = ThreadLocal.withInitial(()
            -> new SimpleDateFormat(DEFAULT_DATE_PATTERN));

    /**
     * 日期转换为字符串
     *
     * @param date    日期
     * @param dateSdf 日期格式
     * @return 字符串
     */
    public static String format(Date date, SimpleDateFormat dateSdf) {
        synchronized (dateSdf) {
            if (null == date) {
                return null;
            }
            return dateSdf.format(date);
        }
    }

    public static Date parseDate(String string, String[] parsePatterns) {
        if (string == null) {
            return null;
        }
        try {
            return DateUtils.parseDate(string, DateUtils.parsePatterns);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 日期转换为字符串
     *
     * @param dateSdf 日期格式
     * @return 字符串
     */
    public static String format(SimpleDateFormat dateSdf) {
        synchronized (dateSdf) {
            Date date = getDate();
            if (null == date) {
                return null;
            }
            return dateSdf.format(date);
        }
    }

    public static void main(String[] args) {
        System.out.println(format(yyyymmddhhmmss.get()));
    }

    public static String format(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * Date转时间戳
     *
     * @param date
     * @return
     */
    public static long getTime(Date date) {
        return date.getTime();
    }

    /**
     * 日期转换为字符串
     *
     * @param format 日期格式
     * @return 字符串
     */
    public static String getDate(String format) {
        Date date = new Date();
        if (null == date) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 当前日期
     *
     * @return 系统当前时间
     */
    public static Date getDate() {
        return new Date();
    }

    /**
     * 格式化 LocalDateTime
     *
     * @param localDateTime
     * @param formDate
     * @return
     */
    public static String formatterLocalDateTime(LocalDateTime localDateTime, String formDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formDate);
        return dateTimeFormatter.format(localDateTime);
    }

    /**
     * 获取当天的字符串
     *
     * @return
     */
    public static String getTodayStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        return sdf.format(new Date());
    }

    public static String getTodayStr2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
        return sdf.format(new Date());
    }


    public static String getCurrentTime() {
        return getCurrentTime(DEFAULT_TIME_PATTERN);
    }


    /**
     * 获取当前时间的字符串
     *
     * @return
     */
    public static String getCurrentDateTime() {
        return format(new Date(), DATE_TIME_PATTERN);
    }

    /**
     * 获取当前时间的字符串
     *
     * @param format 字符串格式，如：yy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return sdf.format(timestamp);
    }

    /**
     * 获取当前的月份
     *
     * @return
     */
    public static String getCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(new Date());
    }

}
