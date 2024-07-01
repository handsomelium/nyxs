package com.liu.nyxs.common.converter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * @author ：haoshen
 * @date ：2023-04-28
 * @description：
 * @version: $
 */
@Slf4j
@Data
public class LocalDateTime2StringConverter implements Converter<LocalDateTime, String> {

    private final static DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final static String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private final String pattern;
    @Override
    public String convert(LocalDateTime source) {
        if (Objects.isNull(source)) {
            return "";
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return source.format(formatter);
        } catch (DateTimeParseException e) {
            log.error("", e);
            return source.format(DEFAULT_FORMATTER);
        }
    }

    public LocalDateTime2StringConverter(String pattern) {
        this.pattern = StringUtils.isBlank(pattern) ? DEFAULT_PATTERN : pattern;
    }

    public LocalDateTime2StringConverter() {
        this.pattern = DEFAULT_PATTERN;
    }
}
