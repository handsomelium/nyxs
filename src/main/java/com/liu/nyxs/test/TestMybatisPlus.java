package com.liu.nyxs.test;

import com.liu.nyxs.NyxsApplication;
import com.liu.nyxs.domain.entity.TestDO;
import com.liu.nyxs.service.ITestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author lium
 * @Date 2023/3/30
 * @Description
 */

@SpringBootTest(classes = NyxsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestMybatisPlus {

    @Autowired
    private ITestService testService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;



    @Test
    public void findAll(){
        List<TestDO> list = testService.list();
        System.out.println(list);
    }

    @Test
    public void insert() throws InterruptedException, ExecutionException {

        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i <= 50; i++){
            int finalI = i;
            Future<?> submit = taskExecutor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                TestDO testDO = new TestDO();
                testDO.setUserName("李" + finalI);
                testDO.setAge(finalI);
                testDO.setWeight(BigDecimal.valueOf(70));
                boolean save = testService.save(testDO);
                return threadName + "--" + save;

            });
            futures.add(submit);
        }
        //如果没有这个循环，子线程和主线程就是独立的
        for (Future<?> future : futures){
            System.out.println(future.get());
        }

        System.out.println("执行结束");

    }



}
