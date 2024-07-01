package com.liu.nyxs.common.converter;


import cn.hutool.core.lang.Assert;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ：haoshen
 * @date ：2023-11-20
 * @description : 字符串转Date转换器
 */
public class String2DateConverter implements Converter<String, Date> {

    public static final String[] PATTERNS = {"yyyy-MM-dd", "yyyy/MM/dd", "yyyy-mm-dd", "yyyy-MM-dd HH:mm:ss",
            "yyyy-mm-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyy/mm/dd HH:mm:ss"};

    @Override
    public Date convert(String source) {
        return convert(source, null);
    }

    public Date convert(String source, int patternIndex) {
        if (patternIndex >= PATTERNS.length) {
            throw new IllegalArgumentException("patternIndex is over the pattern's length");
        }
        return convert(source, PATTERNS[patternIndex]);
    }

    public Date convert(String source, String pattern) {
        Assert.notBlank(source, "source should not be blank");
        if (StringUtils.isBlank(pattern)) {
            return getDate(source);
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            return formatter.parse(source);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static Date getDate(String source) {
        Date date = null;
        for (String pattern : PATTERNS) {
            if (pattern.length() != source.length()) {
                continue;
            }
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            try {
                date = formatter.parse(source);
                break;
            } catch (ParseException e) {
                continue;
            }
        }
        Assert.notNull(date, "No pattern is suitable for the source string");
        return date;
    }

}
