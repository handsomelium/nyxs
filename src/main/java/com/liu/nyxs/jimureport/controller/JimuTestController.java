package com.liu.nyxs.jimureport.controller;

import com.liu.nyxs.domain.JimuResponse;
import com.liu.nyxs.domain.entity.TestDO;
import com.liu.nyxs.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author lium
 * @Date 2023/7/18
 * @Description
 */
@RestController
@RequestMapping("/jimuTest")
public class JimuTestController {


    @Autowired
    private ITestService testService;


    // http://127.0.0.1:19999/nyxsservice/jimuTest/getTestData
    @GetMapping("/getTestData")
    public JimuResponse<?> getTestData(){
        List<TestDO> testDOList = testService.list();
        return getJimuResponse(testDOList);

    }

    // http://127.0.0.1:19999/nyxsservice/jimuTest/getTestData2
    @GetMapping("/getTestData2")
    public JimuResponse<?> getTestData2(){
        List<TestDO> testDOList = testService.list();
        return getJimuResponse(testDOList);

    }

    private <T> JimuResponse<T> getJimuResponse(List<T> list) {
        JimuResponse<T> response = new JimuResponse<>();
        response.setData(list);
        response.setCount(list.size());
        response.setTotal(1L);
        return response;

    }
}
