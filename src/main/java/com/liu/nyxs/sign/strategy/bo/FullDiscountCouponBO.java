package com.liu.nyxs.sign.strategy.bo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 满减类
 */
@Data
@Builder
public class FullDiscountCouponBO {

    private BigDecimal thresholdPrice;

    private BigDecimal discountPrice;

}
