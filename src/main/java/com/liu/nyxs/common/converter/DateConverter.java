package com.liu.nyxs.common.converter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ：haoshen
 * @date ：2023-03-09
 * @description 日期转换器
 */
@Slf4j
public class DateConverter implements Converter<Date, String> {

    private final SimpleDateFormat formatter;

    @Override
    public String convert(Date date) {
        try {
            return formatter.format(date);
        } catch (Exception e) {
            try {
                //尝试再使用标准格式进行转换
                SimpleDateFormat stanPattern = new SimpleDateFormat(Pattern.YYYY_MM_DD_HH_MM_SS.pattern);
                return stanPattern.format(date);
            } catch (Exception ex) {
                log.debug("日期类型转换错误，" + e.getMessage());
            }
        }
        return "";
    }

    public DateConverter() {
        this(Pattern.YYYY_MM_DD_HH_MM_SS.pattern);
    }
    public DateConverter(String pattern) {
        if (StringUtils.isNotBlank(pattern)) {
            formatter = new SimpleDateFormat(pattern);
        } else {
            formatter = new SimpleDateFormat(Pattern.YYYY_MM_DD_HH_MM_SS.pattern);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Pattern {

        YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
        YYYY_MM_DD("yyyy-MM-dd");
        private final String pattern;

    }
}
