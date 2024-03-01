package com.oep.backend.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeFormat {
    final static Pattern pattern = Pattern.compile("\\b\\w{3}\\s(\\w{3})\\s(\\d{2})\\s(\\d{4})\\s(\\d{2}):(\\d{2}):(\\d{2})\\b");

    public static Date dateTimeFormat(String dateTimeString){
        // 匹配正则表达式模式
        Matcher matcher = pattern.matcher(dateTimeString);
        if (matcher.find()) {
            // 提取年、月、日、时、分和秒
            String month = matcher.group(1);
            String day = matcher.group(2);
            String year = matcher.group(3);
            String hour = matcher.group(4);
            String minute = matcher.group(5);
            String second = matcher.group(6);

            // 构建 yyyy-MM-dd HH:mm:ss 格式的字符串
            String formattedDateTime = year + "-" + monthToNumber(month) + "-" + day + " " + hour + ":" + minute + ":" + second;

            System.out.println("转换后的日期时间字符串: " + formattedDateTime);

            // 使用 LocalDateTime 解析格式化的日期时间字符串
            LocalDateTime localDateTime = LocalDateTime.parse(formattedDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Shanghai"));

            // 将 ZonedDateTime 转换为 Date 对象并返回
            return Date.from(zonedDateTime.toInstant());
        } else {
            System.out.println("日期时间字符串格式不匹配。");
            return null;
        }
    }

    public static String monthToNumber(String month) {
        return switch (month) {
            case "Jan" -> "01";
            case "Feb" -> "02";
            case "Mar" -> "03";
            case "Apr" -> "04";
            case "May" -> "05";
            case "Jun" -> "06";
            case "Jul" -> "07";
            case "Aug" -> "08";
            case "Sep" -> "09";
            case "Oct" -> "10";
            case "Nov" -> "11";
            case "Dec" -> "12";
            default -> null;
        };
    }
}