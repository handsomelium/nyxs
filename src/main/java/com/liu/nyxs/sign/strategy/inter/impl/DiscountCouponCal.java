package com.liu.nyxs.sign.strategy.inter.impl;

import com.liu.nyxs.sign.strategy.inter.ICouponCal;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 折扣券
 */
@Service(value = "discountCouponCal")
public class DiscountCouponCal implements ICouponCal<BigDecimal> {


    @Override
    public BigDecimal calPrice(BigDecimal price, BigDecimal discount) {
        return price.multiply(discount);
    }
}
