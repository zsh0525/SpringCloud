package com.zsh.www.tools;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author zsh
 * @description 日期工具类 返回字符串的，统一转换为localDateTime类型(多态写法，少些代码吧)。不想自己写可使用第三方工具包(common-util)github自行选择
 * @date 2021/10/27
 */
public class DateTools extends Object {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "hh:mm:ss";
    private static final String DATE_TIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;


    /**
     * 获取当前时间，返回string类型
     *
     * @return
     */
    public static String now() {
        return now(DATE_TIME_FORMAT);
    }

    public static String now(String format) {
        return dateToString(LocalDateTime.now(), format);
    }

    public static String nowDate() {
        return nowDate(DATE_FORMAT);
    }

    public static String nowDate(String format) {
        return dateToString(LocalDate.now(), format);
    }

    /**
     * @param localDate LocalDate类型
     * @param format    转换格式
     * @return 日期字符串类型
     */
    public static String dateToString(LocalDate localDate, String format) {
        return dateToString(format(localDate, format), format);
    }

    /**
     * @param localDateTime LocalDateTime类型
     * @param format        转换格式
     * @return 日期字符串类型
     */
    public static String dateToString(LocalDateTime localDateTime, String format) {
        return localDateTimeFormat(localDateTime, format);
    }

    /**
     * @param date   date类型
     * @param format 转换格式
     * @return 日期字符串类型
     */
    public static String dateToString(Date date, String format) {
        return dateToString(format(date), format);
    }

    /**
     * @param localDateTime LocalDateTime类型
     * @param format        转换格式
     * @return 日期字符串类型
     */
    public static String localDateTimeFormat(LocalDateTime localDateTime, String format) {
        return localDateTime.format(pattern(format));
    }

    /**
     * @param localDateTime LocalDateTime 类型的字符串
     * @param format        转换格式
     * @return LocalTime
     */
    public static LocalDateTime localDateTimeFormat(String localDateTime, String format) {
        return LocalDateTime.parse(localDateTime, pattern(format));
    }

    /**
     * @param localDate localDate类型的字符串
     * @return LocalDate
     */
    public static LocalDate dateFormat(String localDate) {
        return LocalDate.parse(localDate, pattern(DATE_FORMAT));
    }

    /**
     * @param date   date类型的字符串
     * @param format 转换格式
     * @return Date类型
     */
    public static Date dateFormat(String date, String format) {
        return format(localDateTimeFormat(date, format));
    }

    /**
     * @param localDate LocalDate类型
     * @return Date类型
     */
    public static Date format(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @param localDateTime LocalDateTime类型
     * @return Date类型
     */
    public static Date format(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @param date 日期类型
     * @return LocalDateTime
     */
    public static LocalDateTime format(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * @param localDate LocalDate类型
     * @param format    转换格式
     * @return LocalDateTime
     */
    public static LocalDateTime format(LocalDate localDate, String format) {
        return format(format(localDate));
    }


    public static DateTimeFormatter pattern(String format) {
        return DateTimeFormatter.ofPattern(format);
    }

    public static void main(String[] args) {
//        Date date = new Date();
//        System.out.println(date);
//        LocalDate localDate = LocalDate.now();
//        LocalDateTime localDateTime = LocalDateTime.now();
//        System.out.println(DateTools.dateToString(date, "yyyy-MM-dd"));
//        System.out.println(DateTools.dateToString(localDate, DateTools.DATE_FORMAT));
//        System.out.println(DateTools.dateFormat(DateTools.dateToString(localDateTime, DateTools.DATE_FORMAT)));
//        new Hashtable<>();
//        new TreeSet<>();
        String[] a = {new String("a"),  "c","b"};
        List<String> al = new ArrayList<String>();
        al.add("a");
        al.add("c");
        al.add("b");
        Arrays.sort(a);
        List<String> al2 = new LinkedList<>();
        al2.add("a");
        al2.add("c");
        al2.add("b");
        Collections.sort(al);
        Collections.sort(al2);
        Set<String> s = new TreeSet<>();
        s.add("a");
        s.add("c");
        s.add("b");
        Map<String,String> map = new TreeMap<>();
        new HashMap<>();
        for (int i = 0; i < a.length; ++i) {
            System.out.println(a[i]);
            System.out.println(al.get(i));
            System.out.println(al2.get(i));
        }
    }
}
