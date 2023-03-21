package com.liu.nyxs.sign.strategy.inter;


import java.math.BigDecimal;

public interface ICouponCal<T> {

    BigDecimal calPrice(BigDecimal price, T couponInfo);

}
