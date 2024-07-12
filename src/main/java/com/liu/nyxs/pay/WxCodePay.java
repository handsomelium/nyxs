package com.liu.nyxs.pay;


import com.github.binarywang.wxpay.bean.request.WxPayMicropayRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;

import javax.annotation.Resource;

/**
 * @Author lium
 * @Date 2024/7/12 17:27
 * @Describe
 */
public class WxCodePay {

    @Resource
    private WxPayService wxPayService;


    public void test() throws WxPayException {
        WxPayMicropayRequest wxPayMicropayRequest = WxPayMicropayRequest
                .newBuilder()
                .body("body")
                .outTradeNo("aaaaa")
                .totalFee(123)
                .spbillCreateIp("127.0.0.1")
                .authCode("aaa")
                .build();
        wxPayService.micropay(wxPayMicropayRequest);


    }
}
