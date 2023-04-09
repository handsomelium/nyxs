package com.liu.nyxs.test;

import com.liu.nyxs.NyxsApplication;
import com.liu.nyxs.domain.entity.Weather;
import com.liu.nyxs.service.ITDengineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * @Author lium
 * @Date 2023/4/6
 * @Description
 */

@SpringBootTest(classes = NyxsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TDengineTest {

    @Autowired
    private ITDengineService dengineService;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    @Test
    public void createDb(){
        dengineService.createDb();
    }


    @Test
    public void createSuperTable(){
        dengineService.createSuperTable();
    }

    @Test
    public void createTable(){
        System.out.println(dengineService.createTable());
    }


    @Test
    public void batchInsertTdengine() throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < 50; i++){
            Thread.sleep(1);
            Future<Integer> future = threadPoolTaskExecutor.submit(() -> {
                List<Weather> list = new ArrayList<>();
                long ts = System.currentTimeMillis();
                Random random = new Random(ts);
                long thirtySec = 1000 * 30;
                for (int y = 0; y < 100; y++) {
                    Weather weather = new Weather(new Timestamp(ts + (thirtySec * y)), 30 * random.nextFloat(), random.nextInt(100));
                    list.add(weather);
                }
                return dengineService.insertTdengine(list);

            });
            futures.add(future);
        }

        int totalCount = 0;
        for (Future<Integer> future : futures){
            Integer integer = future.get();
            totalCount = totalCount + integer * 50;

        }
        long end = System.currentTimeMillis();

        System.out.println("执行完毕，共插入：" + totalCount + "条" + "共耗时：" + (end - start) * 0.001 + "s");
    }


    @Test
    public void insertTdengine() {

        long start = System.currentTimeMillis();
        List<Weather> list = new ArrayList<>();
        long ts = System.currentTimeMillis();
        Random random = new Random(ts);
        long thirtySec = 1000 * 30;
        for (int y = 0; y < 100; y++) {
            Weather weather = new Weather(new Timestamp(ts + (thirtySec * y)), 30 * random.nextFloat(), random.nextInt(100));
            list.add(weather);
        }
        int i = dengineService.insertTdengine(list);

        long end = System.currentTimeMillis();

        System.out.println("执行完毕， 共耗时：" + (end - start) * 0.001 + "s");
    }

    /*@Test
    public void insertTdengine(){
        List<Weather> list = new ArrayList<>();
        for (int i=0; i<100; i++){
            Weather weather = new Weather();
            weather.setTs(new Timestamp(System.currentTimeMillis() + i));
            weather.setHumidity((float) i);
            weather.setTemperature((float) (i+1));
            list.add(weather);
        }
        dengineService.insertTdengine(list);
    }*/



    @Test
    public void avg(){
        List<Weather> list = dengineService.avg();
        System.out.println(list);
    }


    public static void main(String[] args) {
        long ts = System.currentTimeMillis();
        Random random = new Random(ts);
        System.out.println(random.nextInt(100));

    }


    @Test
    public void count(){
        Integer count = dengineService.getWeatherCount();
        System.out.println(count);
    }

}
