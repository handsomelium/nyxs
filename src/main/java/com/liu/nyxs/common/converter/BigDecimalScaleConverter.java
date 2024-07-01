package com.liu.nyxs.common.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @author ：haoshen
 * @date ：2023-05-25
 * @description：精度转换接口
 * @version: $
 */
public interface BigDecimalScaleConverter extends Converter<BigDecimal, BigDecimal> {

    int DEFAULT_SCALE = 2;
    RoundingMode DEFAULT_MODE = RoundingMode.HALF_UP;

    BigDecimal convert(BigDecimal source, Integer scale, RoundingMode mode);

    default BigDecimal defaultConvert(BigDecimal src, Integer scale, RoundingMode mode) {
        if (Objects.isNull(src)) {
            return null;
        }
        if (Objects.isNull(scale) || scale < 0) {
            scale = DEFAULT_SCALE;
        }
        if (Objects.isNull(mode)) {
            mode = DEFAULT_MODE;
        }
        return src.setScale(scale, mode);
    }
}
