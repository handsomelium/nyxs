package com.liu.nyxs.common.converter;


import com.liu.nyxs.utils.StatisticConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author ：haoshen
 * @date ：2023-06-21
 * @description String类型转换器
 */
@Slf4j
public class StringConverter implements ObjectConverter<String> {

    @Override
    public Object convert(String source, Type targetType) {
        if (String.class.equals(targetType)) {
            return source;
        }
        if (StringUtils.isBlank(source) || targetType == null) {
            return null;
        }
        Object res = null;
        try {
            if (Integer.class.equals(targetType) || int.class.equals(targetType)) {
                res = Integer.valueOf(source);
            } else if (Long.class.equals(targetType) || long.class.equals(targetType)) {
                res = Long.valueOf(source);
            } else if (BigDecimal.class.equals(targetType)) {
                res = new BigDecimal(source);
            } else if (Double.class.equals(targetType) || double.class.equals(targetType)) {
                res = Double.valueOf(source);
            } else if (Float.class.equals(targetType) || float.class.equals(targetType)) {
                res = Float.valueOf(source);
            } else if (Date.class.equals(targetType)) {
                String2DateConverter string2DateConverter = new String2DateConverter();
                res = string2DateConverter.convert(source);
            } else if (LocalDateTime.class.equals(targetType)) {
                res = LocalDateTime.parse(source, StatisticConstants.DATE_TIME_FORMATTER);
            }
        } catch (Exception e) {
            log.debug("", e);
        }
        return res;
    }

    @Override
    public Object convert(String source) {
        return source;
    }
}
