package com.liu.nyxs.utils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * @author ：haoshen
 * @date ：2023-05-10
 * @description：统计工具类相关常量
 * @version: $
 */
public class StatisticConstants {

    //标准时间格式器（包含时分秒）
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    //标志时间格式器（不包含时分秒）
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //Date标准时间格式器（包含时分秒）
    public static final SimpleDateFormat STA_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
