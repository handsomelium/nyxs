package com.liu.nyxs.sign.strategy.inter.impl;

import com.liu.nyxs.sign.strategy.bo.FullDiscountCouponBO;
import com.liu.nyxs.sign.strategy.inter.ICouponCal;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 满减
 */
@Service(value = "fullDiscountCouponCal")
public class FullDiscountCouponCal implements ICouponCal<FullDiscountCouponBO> {

    @Override
    public BigDecimal calPrice(BigDecimal price, FullDiscountCouponBO couponInfo) {
        return price.compareTo(couponInfo.getThresholdPrice()) >= 0
                // 如果总金额 > 满减门槛 则 减去优惠面额
                ? price.subtract(couponInfo.getDiscountPrice())
                : price;
    }
}
