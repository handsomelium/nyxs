package com.liu.nyxs.test;

import com.liu.nyxs.NyxsApplication;
import com.liu.nyxs.domain.entity.TestDO;
import com.liu.nyxs.service.ITestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author lium
 * @Date 2023/3/30
 * @Description
 */

@SpringBootTest(classes = NyxsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestMybatisPlus {

    @Autowired
    private ITestService testService;


    @Test
    public void findAll(){
        List<TestDO> list = testService.list();
        System.out.println(list);
    }

    @Test
    public void insert(){
        TestDO testDO = new TestDO();
        testDO.setUserName("李四");
        testDO.setAge(28);
        testDO.setWeight(BigDecimal.valueOf(70));
        testService.save(testDO);
    }



}
