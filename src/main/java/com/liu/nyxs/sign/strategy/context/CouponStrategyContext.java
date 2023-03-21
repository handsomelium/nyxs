package com.liu.nyxs.sign.strategy.context;

import com.liu.nyxs.sign.strategy.inter.ICouponCal;

import java.math.BigDecimal;

public class CouponStrategyContext<T> {

    /**
     * 优惠策略
     */
    private ICouponCal<T> couponCal;

    public CouponStrategyContext(ICouponCal<T> couponCal){
        this.couponCal = couponCal;
    }

    public BigDecimal calPrice(BigDecimal price, T couponInfo){
        return this.couponCal.calPrice(price, couponInfo);
    }
}
