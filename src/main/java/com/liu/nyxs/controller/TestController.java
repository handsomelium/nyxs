package com.liu.nyxs.controller;

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

    @GetMapping
    public String test(){
        return "hello world";
    }

}
