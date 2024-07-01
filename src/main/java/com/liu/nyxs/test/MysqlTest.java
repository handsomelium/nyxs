package com.liu.nyxs.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liu.nyxs.NyxsApplication;
import com.liu.nyxs.domain.entity.TestDO;
import com.liu.nyxs.service.ITestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author lium
 * @Date 2023/3/30
 * @Description
 */

@SpringBootTest(classes = NyxsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MysqlTest {

    @Autowired
    private ITestService testService;

    @Resource(name = "threadPoolTaskExecutor")
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

    @Test
    public void testSyncInsert() throws ExecutionException, InterruptedException {
        TestDO testDO = new TestDO();
        testDO.setUserName("李四");
        testDO.setAge(18);
        List<Future<?>> list = new ArrayList<>();
        // 模拟很多请求同时进来
        for (int i = 0; i <= 40; i++){
            Future<?> submit = taskExecutor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                syncInsert(testDO);
                return threadName + "--" + "执行结束";

            });
            list.add(submit);

        }
        for (Future<?> future : list){
            System.out.println(future.get());
        }



    }


    private void syncInsert(TestDO testDO){
        LambdaQueryWrapper<TestDO> param = new LambdaQueryWrapper<>();
        param.eq(TestDO::getUserName, testDO.getUserName());
        param.eq(TestDO::getAge, testDO.getAge());
        TestDO one = testService.getOne(param);
        if (one != null){
            testService.removeById(one.getId());

        }
        testService.save(testDO);

    }





}
