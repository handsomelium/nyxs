package com.liu.nyxs.sign.strategy.test;


import com.liu.nyxs.NyxsApplication;
import com.liu.nyxs.sign.strategy.bo.FullDiscountCouponBO;
import com.liu.nyxs.sign.strategy.context.CouponStrategyContext;
import com.liu.nyxs.sign.strategy.inter.ICouponCal;
import com.liu.nyxs.sign.strategy.inter.impl.DiscountCouponCal;
import com.liu.nyxs.sign.strategy.inter.impl.FullDiscountCouponCal;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;


@SpringBootTest(classes = NyxsApplication.class)
public class AppText {

    @Resource(name = "discountCouponCal")
    ICouponCal<BigDecimal> couponCal;

    @Test
    public void testDisCount(){
        // 折扣券0.9折
        CouponStrategyContext<BigDecimal> couponStrategyContext = new CouponStrategyContext<>(new DiscountCouponCal());
        BigDecimal calPrice = couponStrategyContext.calPrice(BigDecimal.valueOf(100), BigDecimal.valueOf(0.9));
        System.out.println(calPrice);

    }

    @Test
    public void testFullDisCount(){
        // 满减券 满100减10
        CouponStrategyContext<FullDiscountCouponBO> couponStrategyContext = new CouponStrategyContext<>(new FullDiscountCouponCal());
        BigDecimal calPrice = couponStrategyContext.calPrice(BigDecimal.valueOf(100),
                FullDiscountCouponBO.builder()
                        .thresholdPrice(BigDecimal.valueOf(100))
                        .discountPrice(BigDecimal.valueOf(10))
                        .build());
        System.out.println(calPrice);

    }


    @Test
    public void testInject(){
        BigDecimal calPrice = couponCal.calPrice(BigDecimal.valueOf(100), BigDecimal.valueOf(0.9));
        System.out.println(calPrice);

    }
}
