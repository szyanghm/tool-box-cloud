package com.tool.box.utils;

import com.sun.jmx.snmp.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author v_haimiyang
 * @Date 2023/6/28 14:29
 * @Version 1.0
 */
public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy年MM月dd日",
            "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
            "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMdd"};

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
     * Date转时间戳
     *
     * @param date
     * @return
     */
    public static long getTime(Date date) {
        return date.getTime();
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
        return getCurrentTime(DEFAULT_DATE_PATTERN);
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
