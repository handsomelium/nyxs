package com.liu.nyxs.common.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author ：haoshen
 * @date ：2023-05-25
 * @description：BIgDecimal精度转换器
 * @version: $
 */
public class DefaultBigDecimalScaleConverter implements BigDecimalScaleConverter {
    @Override
    public BigDecimal convert(BigDecimal source) {
        return convert(source, DEFAULT_SCALE, DEFAULT_MODE);
    }

    @Override
    public BigDecimal convert(BigDecimal source, Integer scale, RoundingMode mode) {
        return defaultConvert(source, scale, mode);
    }
}
