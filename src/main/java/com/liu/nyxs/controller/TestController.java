package com.liu.nyxs.controller;

import com.liu.nyxs.service.IMeterService;
import com.liu.nyxs.service.ITDengineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lium
 * @Date 2023/3/16
 * @Description 测试
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private IMeterService meterService;

    @Autowired
    private ITDengineService dengineService;

    @GetMapping
    public Integer test(){
        return dengineService.getWeatherCount();
    }



}
