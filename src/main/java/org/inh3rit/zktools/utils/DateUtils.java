package org.inh3rit.zktools.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @Description:
 * @Author: PC-Tony
 * @Date: 11:01 2020/2/9
 */
public class DateUtils {

    private static final String DATE_PATTERN_0 = "yyyy-MM-dd HH:mm:ss";

    public static String format(long milliseconds) {
        return format(milliseconds, DATE_PATTERN_0);
    }

    public static String format(long milliseconds, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        // 默认时区为东8区
        return LocalDateTime.ofEpochSecond(milliseconds, 0, ZoneOffset.of("+8")).format(formatter);
    }
}
